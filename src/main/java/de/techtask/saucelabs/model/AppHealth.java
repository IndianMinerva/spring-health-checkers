package de.techtask.saucelabs.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

public class AppHealth {
    @Id
    private ObjectId id;

    private AppHealthStatusCode status;
    private LocalDateTime created;
    private boolean processed;

    public AppHealth(AppHealthStatusCode status, LocalDateTime created, boolean processed) {
        this.status = status;
        this.created = created;
        this.processed = processed;
    }

    public AppHealthStatusCode getStatus() {
        return status;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }
}
