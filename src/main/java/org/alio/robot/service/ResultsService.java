package org.alio.robot.service;

import lombok.extern.log4j.Log4j2;
import org.alio.robot.model.ScenarioResult;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Log4j2
public class ResultsService {
    Map<String, ScenarioResult> results = new HashMap<String, ScenarioResult>();

    public ScenarioResult getResults(String jobId) {
        if (results.containsKey(jobId)) {
            return results.get(jobId);
        } else {
            return null;
        }
    }
}
