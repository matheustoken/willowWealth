package org.willonwealth.model;

import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

import java.time.Instant;
import java.util.Objects;

@MongoEntity(collection = "accreditations")
public class Accreditation {
    public Accreditation() {
    }

    @BsonId
    @BsonRepresentation(BsonType.STRING)
    public String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public AccreditationStatus getStatus() {
        return status;
    }

    public void setStatus(AccreditationStatus status) {
        this.status = status;
    }

    public AccreditationType getType() {
        return type;
    }

    public void setType(AccreditationType type) {
        this.type = type;
    }

    public Document getDocuments() {
        return documents;
    }

    public void setDocuments(Document documents) {
        this.documents = documents;
    }

    public Instant getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Accreditation that = (Accreditation) o;
        return Objects.equals(id, that.id) && Objects.equals(userId, that.userId) && status == that.status && type == that.type && Objects.equals(documents, that.documents) && Objects.equals(lastUpdated, that.lastUpdated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, status, type, documents, lastUpdated);
    }

    public String userId;

    public AccreditationStatus status;

    public AccreditationType type;

    public Document documents;

    public Accreditation(Instant lastUpdated, Document documents, AccreditationType type, AccreditationStatus status, String userId, String id) {
        this.lastUpdated = lastUpdated;
        this.documents = documents;
        this.type = type;
        this.status = status;
        this.userId = userId;
        this.id = id;
    }

    public Instant lastUpdated;
}
