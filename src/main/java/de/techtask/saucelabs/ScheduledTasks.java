package de.techtask.saucelabs;

import de.techtask.saucelabs.model.AggregatedAppHealth;
import de.techtask.saucelabs.model.AppHealth;
import de.techtask.saucelabs.repository.AppStateRepo;
import de.techtask.saucelabs.service.HealthCheckerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    @Autowired
    private HealthCheckerService healthCheckerService;

    @Autowired
    private AppStateRepo repo;

    //	@Scheduled(cron = "*/10 * * * * *")  //every 10 seconds
    @Scheduled(cron = "${app.healthChecker.cronExpression}")  //every 10 seconds
    public void checkHealth() {
        AppHealth appHealth = healthCheckerService.persistAppState();
        log.info("Health-check record inserted STATUS: " + appHealth.getStatus());
    }


    //	@Scheduled(cron = "0 0/2 * * * *")  //every 2 minutes
    @Scheduled(cron = "${app.healthAggregator.cronExpression}")  //every 2 minutes
    public void aggregateHealth() {
        AggregatedAppHealth aggregatedAppHealth = healthCheckerService.notifyAggregatedAppHealth();
        log.info("Health-check aggregation record inserted STATUS: " + aggregatedAppHealth.getAggregatedAppHealthStatusCode());
    }
}
