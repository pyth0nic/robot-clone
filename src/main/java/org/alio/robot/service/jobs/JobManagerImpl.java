package org.alio.robot.service.jobs;

import org.alio.robot.model.ScenarioRequest;
import org.alio.robot.model.ScenarioResult;
import org.springframework.stereotype.Service;

@Service
public class JobManagerImpl implements JobManager {
    @Override
    public String startJob(ScenarioRequest request) {
        return null;
    }

    @Override
    public Boolean isComplete(String jobId) {
        return null;
    }

    @Override
    public ScenarioResult getResult(String jobId) {
        return null;
    }
}
