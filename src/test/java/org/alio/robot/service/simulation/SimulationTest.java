package org.alio.robot.service.simulation;

import org.alio.robot.model.DTO.ScenarioResult;
import org.alio.robot.model.DTO.ScenarioResultSuccess;
import org.alio.robot.model.simulation.MOVE;
import org.alio.robot.model.simulation.Point;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimulationTest {

    @Test
    void runSimulation() {
        var sim = (ScenarioResultSuccess) Simulation.runSimulation(
                RequestUtil.getScenarioRequest(
                        4,
                        new Point(2,1, null),
                        List.of(new Point(0,1, null),
                                new Point(1,2, null),
                                new Point(3,1, null)),
                        List.of(MOVE.D, MOVE.L,MOVE.U,MOVE.U,MOVE.R,MOVE.R)));
        var expectedResult = ScenarioResultSuccess.builder()
                .score(3)
                .endPositions(List.of(
                        new Point(3,0, null),
                        new Point(2,1, null),
                        new Point(1,0, null),
                        new Point(0,0, null))).build();
        assertEquals(expectedResult.getScore(), sim.getScore());
        for (int i = 0; i < expectedResult.getEndPositions().size(); i++) {
            var expected = expectedResult.getEndPositions().get(i);
            var result = sim.getEndPositions().get(i);
            assertTrue(Point.eq(expected,result));
        }
    }
}