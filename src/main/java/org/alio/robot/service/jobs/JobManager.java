package org.alio.robot.service.jobs;

import org.alio.robot.model.DTO.JobResult;
import org.alio.robot.model.DTO.ScenarioRequest;
import org.alio.robot.model.DTO.ScenarioResult;

import java.util.concurrent.ExecutionException;

public interface JobManager {
    JobResult startJob(ScenarioRequest request);
    Boolean isComplete(String jobId);
    ScenarioResult getResult(String jobId);
}
