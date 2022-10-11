package de.techtask.saucelabs.service;

import de.techtask.saucelabs.ScheduledTasks;
import de.techtask.saucelabs.config.AppConfig;
import de.techtask.saucelabs.model.AggregatedAppHealth;
import de.techtask.saucelabs.model.AggregatedAppHealthStatusCode;
import de.techtask.saucelabs.model.AppHealth;
import de.techtask.saucelabs.model.AppHealthStatusCode;
import de.techtask.saucelabs.repository.AggregatedAppHealthRepo;
import de.techtask.saucelabs.repository.AppStateRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class HealthCheckerServiceImpl implements  HealthCheckerService {
    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
    private static final HttpClient client = HttpClient.newHttpClient();

    private final AppStateRepo appStateRepo;
    private final AggregatedAppHealthRepo aggregatedAppHealthRepo;
    private final AppConfig appConfig;

    @Autowired
    public HealthCheckerServiceImpl(AppStateRepo appStateRepo, AggregatedAppHealthRepo aggregatedAppHealthRepo, AppConfig appConfig) {
        this.appStateRepo = appStateRepo;
        this.aggregatedAppHealthRepo = aggregatedAppHealthRepo;
        this.appConfig = appConfig;
    }

    @Override
    public AppHealth persistAppState() {
        AppHealthStatusCode appHealthStatusCode = getAppStatus();
        return appStateRepo.insert(new AppHealth(appHealthStatusCode, LocalDateTime.now(), false));
    }

    @Override
    public AggregatedAppHealth notifyAggregatedAppHealth() {
        return aggregatedAppHealthRepo.insert(getAggregateAppHealth());
    }

    AggregatedAppHealth getAggregateAppHealth() {
        List<AppHealth> unprocessedHealthRecords = appStateRepo.findAllByProcessedOrderByCreatedAsc(false);
        AggregatedAppHealthStatusCode aggregatedAppHealthStatus = getAggregatedHealthStatusCode(unprocessedHealthRecords);

        LocalDateTime from = getFromDate(unprocessedHealthRecords);
        LocalDateTime to = getToDate(unprocessedHealthRecords);

        markRecordsAsProcessed(unprocessedHealthRecords);
        return new AggregatedAppHealth(aggregatedAppHealthStatus, from, to);
    }

    AggregatedAppHealthStatusCode getAggregatedHealthStatusCode(List<AppHealth> unprocessedHealthRecords) {
        if (isApplicationReachable(unprocessedHealthRecords)) {
            return AggregatedAppHealthStatusCode.REACHABLE;
        } else {
            return AggregatedAppHealthStatusCode.NOT_REACHABLE;
        }
    }
    private boolean isApplicationReachable(List<AppHealth> healthRecords) {
        return healthRecords
                .stream().anyMatch(item -> item.getStatus().equals(AppHealthStatusCode.ERROR) || item.getStatus().equals(AppHealthStatusCode.OK));
    }

    private void markRecordsAsProcessed(List<AppHealth> unprocessedHealthRecords) {
        unprocessedHealthRecords.forEach(record -> record.setProcessed(true));
        appStateRepo.saveAll(unprocessedHealthRecords);
    }

    private LocalDateTime getFromDate(List<AppHealth> unprocessedHealthRecords) {
        return unprocessedHealthRecords
                .stream()
                .findFirst()
                .map(AppHealth::getCreated).orElseGet(LocalDateTime::now);
    }

    private LocalDateTime getToDate(List<AppHealth> unprocessedHealthRecords) {
        if (!unprocessedHealthRecords.isEmpty()) {
            return unprocessedHealthRecords.get(unprocessedHealthRecords.size() - 1).getCreated();
        } else {
            return LocalDateTime.now();
        }
    }

    private AppHealthStatusCode getAppStatus() {
        try {
            HttpRequest request = HttpRequest
                    .newBuilder(new URI(appConfig.getUrl()))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return AppHealthStatusCode.OK;
            } else {
                return AppHealthStatusCode.ERROR;
            }
        } catch (Exception ce) { //Ideally only ConnectException occurs here.
//            log.error("Exception occurred", ce);
            return AppHealthStatusCode.NOT_REACHABLE;
        }
    }
}
