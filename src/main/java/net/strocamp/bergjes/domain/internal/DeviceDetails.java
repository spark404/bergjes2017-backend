package net.strocamp.bergjes.domain.internal;

import java.time.LocalDateTime;

/**
 * Created by hugo on 11/04/2017.
 */
public class DeviceDetails {
    private String installationId;
    private String deviceIdentifier;
    private LocalDateTime lastSeen;

    public String getInstallationId() {
        return installationId;
    }

    public void setInstallationId(String installationId) {
        this.installationId = installationId;
    }

    public String getDeviceIdentifier() {
        return deviceIdentifier;
    }

    public void setDeviceIdentifier(String deviceIdentifier) {
        this.deviceIdentifier = deviceIdentifier;
    }

    public LocalDateTime getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(LocalDateTime lastSeen) {
        this.lastSeen = lastSeen;
    }
}
