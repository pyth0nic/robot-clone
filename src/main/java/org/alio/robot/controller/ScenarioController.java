package org.alio.robot.controller;

import org.alio.robot.model.JobResult;
import org.alio.robot.model.ScenarioRequest;
import org.alio.robot.model.ScenarioResult;
import org.alio.robot.service.ResultsService;
import org.springframework.web.bind.annotation.*;

@RestController(value = "api/scenario")
public class ScenarioController {

    private ResultsService resultsService;

    public ScenarioController(ResultsService resultsService) {
        this.resultsService = resultsService;
    }

    @PostMapping("/start")
    public JobResult startScenario(@RequestBody ScenarioRequest scenarioRequest) {
        return null;
    }

    @GetMapping("/result/{jobId}")
    public ScenarioResult startScenario(@PathVariable String jobId) {
        return null;
    }
}
