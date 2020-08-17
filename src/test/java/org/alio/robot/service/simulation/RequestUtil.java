package org.alio.robot.service.simulation;

import org.alio.robot.model.DTO.ScenarioRequest;
import org.alio.robot.model.simulation.MOVE;
import org.alio.robot.model.simulation.Point;

import java.util.List;

public class RequestUtil {
    protected static ScenarioRequest getScenarioRequest() {
        var robotStart = new Point(1, 1, null);
        var portals = List.of(
                new Point(1, 0, null),
                new Point(0, 0, null));
        var moves = List.of(MOVE.D, MOVE.U);
        return ScenarioRequest
                .builder()
                .portalPoints(portals)
                .startPoint(robotStart)
                .moves(moves)
                .dimension(2).build();
    }

    protected static ScenarioRequest getScenarioRequest(int i) {
        var robotStart = new Point(1, 1, null);
        var portals = List.of(
                new Point(1, 0, null),
                new Point(0, 0, null));
        var moves = List.of(MOVE.D, MOVE.U);
        return ScenarioRequest
                .builder()
                .portalPoints(portals)
                .startPoint(robotStart)
                .moves(moves)
                .dimension(i).build();
    }

    protected static ScenarioRequest getScenarioRequest(int dimension,
                                                        Point robotStart,
                                                        List<Point> portals,
                                                        List<MOVE> moves) {
        return ScenarioRequest
                .builder()
                .portalPoints(portals)
                .startPoint(robotStart)
                .moves(moves)
                .dimension(dimension).build();
    }
}
