package de.techtask.saucelabs.service;

import de.techtask.saucelabs.model.AggregatedAppHealth;
import de.techtask.saucelabs.model.AppHealth;

public interface HealthCheckerService {
    AppHealth persistAppState();
    AggregatedAppHealth notifyAggregatedAppHealth();
}
