package org.alio.robot.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.alio.robot.model.simulation.Point;

import java.util.List;

@Data
@SuperBuilder
public class ScenarioResultSuccess extends ScenarioResult {
    private Integer score;
    private List<Point> endPositions;
}
