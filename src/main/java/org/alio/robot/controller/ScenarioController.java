package org.alio.robot.controller;

import org.alio.robot.model.DTO.JobResult;
import org.alio.robot.model.DTO.ScenarioRequest;
import org.alio.robot.model.DTO.ScenarioResult;
import org.alio.robot.service.jobs.JobManager;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("api/scenario")
public class ScenarioController {

    private JobManager jobManager;

    public ScenarioController(JobManager jobManager) {
        this.jobManager = jobManager;
    }

    @PostMapping("/start")
    public JobResult startScenario(@RequestBody ScenarioRequest scenarioRequest) {
        return jobManager.startJob(scenarioRequest);
    }

    @GetMapping("/result/{jobId}")
    public ScenarioResult getResult(@PathVariable String jobId) {
        return jobManager.getResult(jobId);
    }
}
