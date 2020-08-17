package org.alio.robot.service.simulation;

import org.alio.robot.model.DTO.ScenarioRequest;
import org.alio.robot.model.simulation.MOVE;
import org.alio.robot.model.simulation.Point;
import org.javatuples.Pair;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WorldBuilderTest {

    @Test
    void createMap_BuildsWorld_WithValidDimension() {
       var world = WorldBuilder.createMap(2);
       var expected = List.of(
               new Point(0,0,null),
               new Point(0,1,null),
               new Point(1,0,null),
               new Point(1,1,null));
        Assert.assertArrayEquals(expected.toArray(), world.toArray());
    }

    @Test
    void createMap_ReturnsEmptyList_WithInvalidDimension() {
        var world = WorldBuilder.createMap(-1);
        Assert.assertArrayEquals(List.of().toArray(), world.toArray());
    }

    @Test
    void addAgents_Adds_Agents_With_Valid_Positions() {
        var robotStart = new Point(1,1,null);
        var portals = List.of(
                new Point(1,0,null),
                new Point(0,0,null));
        var moves = List.of(MOVE.D, MOVE.U);
        var request= ScenarioRequest
                .builder()
                .portalPoints(portals)
                .startPoint(robotStart)
                .moves(moves)
                .dimension(2).build();

        var worldPoints = WorldBuilder.createMap(request.getDimension());
        var world = WorldBuilder.addAgents(worldPoints, request);

        assertEquals(1, world.getRobots().size());
        var firstRobot = world.getRobots().get(0);
        assertEquals(robotStart, firstRobot.getPoint());
        assertEquals(moves, firstRobot.getMoves());

        for (var p: portals) {
            var point = world.getPoints().get(Pair.with(p.getX(), p.getY()));
            assertNotNull(point.getZ());
            assertNull(point.getZ().getMoves());
        }
    }
}