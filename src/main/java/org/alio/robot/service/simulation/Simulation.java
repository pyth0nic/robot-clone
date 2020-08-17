package org.alio.robot.service.simulation;

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
        List<Point> points = WorldBuilder.createMap(request.getDimension());
        if (points.isEmpty()) {
            return ScenarioResultError.builder()
                    .messages(List.of("World points were empty!"))
                    .build();
        }

        World world = WorldBuilder.addAgents(points, request);
        if (world.getRobots().isEmpty()) {
            return ScenarioResultError.builder()
                    .messages(List.of("Robots were empty!"))
                    .build();
        }

        return runSteps(request, world);
    }

    private static ScenarioResultSuccess runSteps(ScenarioRequest request, World world) {
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
                    var newPoint = WorldUtil.makeMove(robot, move, request.getDimension());
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
}
