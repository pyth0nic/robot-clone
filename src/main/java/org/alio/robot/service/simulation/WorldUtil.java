package org.alio.robot.service.simulation;

import lombok.extern.log4j.Log4j2;
import org.alio.robot.model.simulation.MOVE;
import org.alio.robot.model.simulation.Point;
import org.alio.robot.model.simulation.Robot;

@Log4j2
public class WorldUtil {
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
}
