package org.alio.robot.service.simulation;

import org.alio.robot.model.DTO.ScenarioRequest;
import org.alio.robot.model.simulation.MOVE;
import org.alio.robot.model.simulation.Point;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.alio.robot.service.simulation.RequestUtil.getScenarioRequest;
import static org.junit.jupiter.api.Assertions.*;

class RequestValidatorTest {

    @Test
    void validateScenario_Passes_ForValidRequest() {
        ScenarioRequest request = getScenarioRequest(2);
        var errors = RequestValidator.validateScenario(request);
        assertEquals(0, errors.size());
    }

    @Test
    void validateScenario_Throws_ForInvalidDimension() {
        ScenarioRequest request = getScenarioRequest(-2);
        var errors = RequestValidator.validateScenario(request);
        assertEquals(1, errors.size());
    }

    @Test
    void validateScenario_Throws_ForInvalidStartPoint() {
        ScenarioRequest request1 = getScenarioRequest(2);
        request1.getStartPoint().setX(5);
        var errors1 = RequestValidator.validateScenario(request1);
        assertEquals(1, errors1.size());

        ScenarioRequest request2 = getScenarioRequest(2);
        request2.getStartPoint().setY(5);
        var errors2 = RequestValidator.validateScenario(request2);
        assertEquals(1, errors2.size());

        ScenarioRequest request3 = getScenarioRequest(2);
        request3.getStartPoint().setY(5);
        request3.getStartPoint().setX(-1);
        var errors3 = RequestValidator.validateScenario(request3);
        assertEquals(1, errors3.size());
    }

    @Test
    void validateScenario_Throws_ForInvalidPortalPoints() {
        ScenarioRequest request1 = getScenarioRequest(2);
        for (var p: request1.getPortalPoints()) {
            p.setY(-1);
        }
        var errors1 = RequestValidator.validateScenario(request1);
        assertEquals(2, errors1.size());
    }
}