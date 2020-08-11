package org.alio.robot.service.jobs;

import org.alio.robot.model.ScenarioRequest;
import org.alio.robot.model.ScenarioResult;

public interface JobManager {
    String startJob(ScenarioRequest request);
    Boolean isComplete(String jobId);
    ScenarioResult getResult(String jobId);
}
