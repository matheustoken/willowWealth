package org.willonwealth.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.willonwealth.model.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccreditationDto {

    @JsonProperty("user_id")
    public String userId;

    @JsonProperty("accreditation_type")
    public String accreditationType;

    private List<Document> documents;
}
