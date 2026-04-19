package org.willonwealth.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@MongoEntity(collection = "accreditations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Accreditation {

    public String id; // store UUID string
    public String userId;
    public AccreditationType accreditationType;
    public AccreditationStatus status;
    public List<Document> documents;
    public Instant lastUpdated;
}
