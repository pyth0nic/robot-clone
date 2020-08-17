package org.alio.robot.service.simulation;

import org.alio.robot.model.DTO.ScenarioRequest;
import org.alio.robot.model.simulation.Point;

import java.util.ArrayList;
import java.util.List;

public class RequestValidator {
    public static List<String> validateScenario(ScenarioRequest request) {
        List<String> errors = new ArrayList<>();
        if (request.getDimension() <= 0) {
            errors.add("Dimension parameter is too small");
            return errors;
        }
        if (!Point.isValidPoint(request.getStartPoint(), request.getDimension())) {
            errors.add("A portal point is not within valid bounds");
        }
        for (Point portal: request.getPortalPoints()) {
            if (!Point.isValidPoint(portal, request.getDimension())) {
                errors.add(
                        String.format("A portal point is not within valid bounds (%d,%d)",
                                portal.getX(), portal.getY())
                );
            }
        }
        if (request.getMoves().size() == 0) {
            errors.add("There must be at least one move.");
        }
        return errors;
    }
}
