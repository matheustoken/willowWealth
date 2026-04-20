package org.willonwealth.model;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "accreditations")
public class Accreditation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public UUID getAccreditationId() {
        return accreditationId;
    }

    public void setAccreditationId(UUID accreditationId) {
        this.accreditationId = accreditationId;
    }

    private UUID accreditationId;

    private String userId;

    @Enumerated(EnumType.STRING)
    private AccreditationStatus status;

    @Enumerated(EnumType.STRING)
    private AccreditationType type;

    @Embedded
    private Document documents;

    private Instant lastUpdated;

    public Instant getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Document getDocuments() {
        return documents;
    }

    public void setDocuments(Document documents) {
        this.documents = documents;
    }

    public AccreditationType getType() {
        return type;
    }

    public void setType(AccreditationType type) {
        this.type = type;
    }

    public AccreditationStatus getStatus() {
        return status;
    }

    public void setStatus(AccreditationStatus status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Accreditation(Long id, Instant lastUpdated, Document documents, AccreditationType type, AccreditationStatus status, String userId) {
        this.id = id;
        this.lastUpdated = lastUpdated;
        this.documents = documents;
        this.type = type;
        this.status = status;
        this.userId = userId;
    }

    public Accreditation() {
    }
}
