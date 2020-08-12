package org.alio.robot.service;

import lombok.extern.log4j.Log4j2;
import org.alio.robot.model.DTO.ScenarioRequest;
import org.alio.robot.model.DTO.ScenarioResult;
import org.alio.robot.model.DTO.ScenarioResultError;
import org.alio.robot.model.DTO.ScenarioResultSuccess;
import org.alio.robot.model.simulation.*;
import org.javatuples.Pair;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

@Log4j2
public class Simulation {
    public static ScenarioResult runSimulation(ScenarioRequest request) {
        List<Point> points = createMap(request.getDimension());
        if (points.isEmpty()) {
            return ScenarioResultError.builder()
                    .messages(List.of("World points were empty!"))
                    .build();
        }

        World world = addAgents(points, request);
        if (world.getRobots().isEmpty()) {
            return ScenarioResultError.builder()
                    .messages(List.of("Robots were empty!"))
                    .build();
        }

        Integer score = 0;
        Boolean robotMoved = true;
        Integer stepCount = 0;
        while (robotMoved && stepCount < 1000) {
            Integer robotMoveCount = 0;
            List<Robot> newRobots = new ArrayList<>();

            log.info("robots: {}", world.getRobots());
            for (Robot robot: world.getRobots()) {
                if (!robot.moveQueue.isEmpty()) {
                    var move = robot.moveQueue.poll();
                    var newPoint = makeMove(robot, move, request.getDimension());
                    log.info("ROBOT move: {}, old point: {}, new point: {}", move.toString(), robot.getPoint(), newPoint);
                    robot.setPoint(newPoint);
                    var selectedPoint = world
                            .getPoints()
                            .get(Pair.with(newPoint.getX(), newPoint.getY()));

                    robotMoveCount++;
                    if (selectedPoint.getZ() != null) {
                        // add new robot
                        Robot newRobot = Robot.builder()
                                .point(newPoint.clone())
                                .moves(robot.getMoves())
                                .moveQueue(new LinkedList<>(robot.getMoves()))
                                .build();
                        newRobots.add(newRobot);
                        // remove portal
                        selectedPoint.setZ(null);
                        world.points.put(Pair.with(newPoint.getX(), newPoint.getY()), selectedPoint);
                        log.info("Portal hit and new robot added");
                        score++;
                    }
                }
            }
            robotMoved = robotMoveCount > 0;
            world.getRobots().addAll(newRobots);
            log.info("Completed step {}", stepCount);
            stepCount++;
        }
        log.info("Robot Final State {}", world.getRobots());
        log.info("Job Completed at step {}", stepCount);
        return ScenarioResultSuccess
                .builder()
                .endPositions(world.getRobots().stream().map(Agent::getPoint).collect(Collectors.toList()))
                .score(score)
                .build();
    }

    protected static Point makeMove(Robot robot, MOVE move, int dimension) {
        Point currentPoint = robot.getPoint().clone();
        if (move == MOVE.D) {
            if (currentPoint.getY() < dimension - 1) {
                currentPoint.setY(currentPoint.getY() + 1);
            } else {
                currentPoint.setY(0);
            }
        }
        if (move == MOVE.U) {
            if (currentPoint.getY() > 0) {
                currentPoint.setY(currentPoint.getY() - 1);
            } else {
                currentPoint.setY(dimension - 1);
            }
        }
        if (move == MOVE.R) {
            if (currentPoint.getX() < dimension - 1) {
                currentPoint.setX(currentPoint.getX() + 1);
            } else {
                currentPoint.setX(0);
            }
        }
        if (move == MOVE.L) {
            if (currentPoint.getX() > 0) {
                currentPoint.setX(currentPoint.getX() - 1);
            } else {
                currentPoint.setX(dimension - 1);
            }
        }
        return currentPoint;
    }

    private static List<Point> createMap(int n) {
        List<Point> map = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                map.add(Point.builder().x(i).y(j).build());
            }
        }
        return map;
    }

    // todo validate starting move list?
    private static World addAgents(List<Point> map, ScenarioRequest request) {
        if (map.isEmpty()) {
            log.debug("Got empty list for map");
            return World.builder().build();
        }
        log.info("MAP {}", map);

        List<Robot> robots = new ArrayList<>();
        Queue<Point> points = new LinkedList<>(request.getPortalPoints());
        Map<Pair<Integer,Integer>, Point> mapPoints = map.stream().map(x-> {
            if (Point.eq(x, request.getStartPoint())) {
                Robot robot = Robot.builder()
                        .point(request.getStartPoint())
                        .moves(request.getMoves())
                        .moveQueue(new LinkedList<>(request.getMoves()))
                        .build();
                //x.setZ(robot);
                robots.add(robot);
                log.info("Added starting robot {}, {}", x, request.getStartPoint());
            } else if (!points.isEmpty() && Point.eq(x, points.peek())) {
                Point point = points.poll();
                x.setZ(Portal.builder()
                        .point(point)
                        .build());
                log.info("Added Portal at {} {}", x, x.getZ() instanceof Portal);
            }
            return x;
        }).collect(toMap(x-> Pair.with(x.getX(), x.getY()), x-> x));

        return World.builder()
                .points(mapPoints)
                .robots(robots)
                .build();
    }
}
