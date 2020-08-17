package org.alio.robot.service;

import lombok.extern.log4j.Log4j2;
import org.alio.robot.model.DTO.ScenarioRequest;
import org.alio.robot.model.DTO.ScenarioResult;
import org.alio.robot.service.simulation.Simulation;

import java.util.concurrent.Callable;

import static java.util.stream.Collectors.toMap;

@Log4j2
public class SimulationTask implements Callable<ScenarioResult> {

    ScenarioRequest request;
    String jobId;

    public SimulationTask(ScenarioRequest request, String jobId) {
        this.request = request;
        this.jobId = jobId;
    }

    // need an n by n board, any move should be within the board
    @Override
    public ScenarioResult call() {
        var result = Simulation.runSimulation(this.request);
        return result;
    }
}
