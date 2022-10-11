package de.techtask.saucelabs.model;

import java.time.LocalDateTime;

public class AggregatedAppHealth {
    private AggregatedAppHealthStatusCode aggregatedAppHealthStatusCode;
    private LocalDateTime from;
    private LocalDateTime to;

    public AggregatedAppHealth(AggregatedAppHealthStatusCode aggregatedAppHealthStatusCode, LocalDateTime from, LocalDateTime to) {
        this.aggregatedAppHealthStatusCode = aggregatedAppHealthStatusCode;
        this.from = from;
        this.to = to;
    }

    public AggregatedAppHealthStatusCode getAggregatedAppHealthStatusCode() {
        return aggregatedAppHealthStatusCode;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }
}
