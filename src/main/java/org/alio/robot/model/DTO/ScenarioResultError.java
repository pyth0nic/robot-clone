package org.alio.robot.model.DTO;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
public class ScenarioResultError extends ScenarioResult {
    private List<String> messages;
}
