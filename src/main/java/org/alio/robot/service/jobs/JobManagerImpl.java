package org.alio.robot.service.jobs;

import lombok.extern.log4j.Log4j2;
import org.alio.robot.model.DTO.*;
import org.alio.robot.model.simulation.Point;
import org.alio.robot.service.SimulationTask;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Service
@Log4j2
public class JobManagerImpl implements JobManager {

    private ExecutorService executorService;

    Map<String, Future<ScenarioResult>> results = new HashMap<String, Future<ScenarioResult>>();

    public JobManagerImpl(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @Override
    public JobResult startJob(ScenarioRequest request) {
        List<String> errors = validateScenario(request);
        if (errors.size() > 0) {
            return JobResultError
                    .builder()
                    .errors(errors)
                    .build();
        }

        String jobId = UUID.randomUUID().toString();
        Future<ScenarioResult> task = executorService.submit(new SimulationTask(request, jobId));
        results.put(jobId, task);
        return JobResultSuccess
                .builder()
                .jobId(jobId)
                .time(Instant.now())
                .request(request)
                .build();
    }

    private List<String> validateScenario(ScenarioRequest request) {
        List<String> errors = new ArrayList<>();
        if (request.getDimension() <= 0) {
            errors.add("Dimension parameter is too small");
        }
        if (!Point.isValidPoint(request.getStartPoint(), request.getDimension())) {
            errors.add("A portal point is not within valid bounds");
        }
        for (Point portal: request.getPortalPoints()) {
            if (!Point.isValidPoint(portal, request.getDimension())) {
                errors.add(
                        String.format("A portal point is not within valid bounds (%d,%d)",
                                       portal.getX(), portal.getY())
                );
            }
        }
        if (request.getMoves().size() == 0) {
            errors.add("There must be at least one move.");
        }
        return errors;
    }

    @Override
    public Boolean isComplete(String jobId) {

        if (results.containsKey(jobId)) {
            Future<ScenarioResult> task = results.get(jobId);
            return task.isDone();
        } else {
            log.info("Couldn't find key {}", jobId);
            return false;
        }
    }

    @Override
    public ScenarioResult getResult(String jobId) {
        if (isComplete(jobId)) {
            Future<ScenarioResult> task = results.get(jobId);
            List<String> errors = new ArrayList<>();
            try {
                return task.get();
            } catch (InterruptedException e) {
                log.error("Job was interupted", e);
                errors.add(e.getMessage());
                return ScenarioResultError.builder().messages(errors).build();
            } catch (ExecutionException e) {
                log.error("Job had error", e);
                errors.add(e.getMessage());
                return ScenarioResultError.builder().messages(errors).build();
            }
        } else {
            log.info("Job not ready yet {}", jobId);
            return null;
        }
    }
}
