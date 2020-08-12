package org.alio.robot.model.DTO;

import lombok.Data;
import org.alio.robot.model.simulation.MOVE;
import org.alio.robot.model.simulation.Point;

import java.util.List;

/*
dimensions of the area (N)
the initial position of the zombie
a list of positions of poor creatures
and a list of moves zombies will make
 */
@Data
public class ScenarioRequest {
    private Integer dimension;
    private Point startPoint;
    private List<Point> portalPoints;
    private List<MOVE> moves;
}
