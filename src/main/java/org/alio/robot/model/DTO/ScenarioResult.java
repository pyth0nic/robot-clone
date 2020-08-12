package org.alio.robot.model.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.alio.robot.model.simulation.World;

@Data
@SuperBuilder
public class ScenarioResult {
    @JsonIgnore
    private World world;
}

