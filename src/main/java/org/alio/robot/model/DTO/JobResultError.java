package org.alio.robot.model.DTO;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
public class JobResultError extends JobResult {
    List<String> errors;
}
