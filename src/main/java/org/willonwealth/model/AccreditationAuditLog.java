package org.willonwealth.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "accreditation_audit_log")
public class AccreditationAuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID accreditationId;

    @Enumerated(EnumType.STRING)
    private AccreditationStatus statusChangedTo;

    private Instant changedAt;

    public AccreditationAuditLog(UUID accreditationId, AccreditationStatus status) {
        this.accreditationId = accreditationId;
        this.statusChangedTo = status;
        this.changedAt = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getAccreditationId() {
        return accreditationId;
    }

    public void setAccreditationId(UUID accreditationId) {
        this.accreditationId = accreditationId;
    }

    public AccreditationStatus getStatusChangedTo() {
        return statusChangedTo;
    }

    public void setStatusChangedTo(AccreditationStatus statusChangedTo) {
        this.statusChangedTo = statusChangedTo;
    }

    public Instant getChangedAt() {
        return changedAt;
    }

    public void setChangedAt(Instant changedAt) {
        this.changedAt = changedAt;
    }
}
