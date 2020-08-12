package org.alio.robot.model.DTO;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Data
@SuperBuilder
public class JobResultSuccess extends JobResult {
    private String jobId;
    private Instant time;
    private ScenarioRequest request;
}
