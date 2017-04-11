package net.strocamp.bergjes.domain;

import java.time.LocalDateTime;

/**
 * Created by hugo on 08/04/2017.
 */
public class Status {
    private StatusType status;
    private LocalDateTime timestamp;

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
