package org.alio.robot.service.simulation;

import lombok.extern.log4j.Log4j2;
import org.alio.robot.model.DTO.ScenarioRequest;
import org.alio.robot.model.simulation.Point;
import org.alio.robot.model.simulation.Portal;
import org.alio.robot.model.simulation.Robot;
import org.alio.robot.model.simulation.World;
import org.javatuples.Pair;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

@Log4j2
public class WorldBuilder {
    protected static List<Point> createMap(int n) {
        List<Point> map = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                map.add(Point.builder().x(i).y(j).build());
            }
        }
        return map;
    }

    // todo validate starting move list?
    protected static World addAgents(List<Point> map, ScenarioRequest request) {
        if (map.isEmpty()) {
            log.debug("Got empty list for map");
            return World.builder().build();
        }
        log.info("MAP {}", map);

        List<Robot> robots = new ArrayList<>();
        Queue<Point> points = new LinkedList<>(request.getPortalPoints()
                .stream()
                .sorted((a,b) -> (a.getX() - b.getX()) + (a.getY() - b.getY()))
                .collect(Collectors.toList()));

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
            }
            if (!points.isEmpty() && Point.eq(x, points.peek())) {
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
