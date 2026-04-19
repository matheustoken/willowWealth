package org.willonwealth.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.willonwealth.model.AccreditationType;
import org.willonwealth.model.Document;

import java.util.List;


public class CreateAccreditationDto {

    public CreateAccreditationDto(String userId, AccreditationType accreditationType, Document document) {
        this.userId = userId;
        this.accreditationType = accreditationType;
        this.document = document;
    }

    public CreateAccreditationDto() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public AccreditationType getAccreditationType() {
        return accreditationType;
    }

    public void setAccreditationType(AccreditationType accreditationType) {
        this.accreditationType = accreditationType;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    @NotBlank
    public String userId;

    @NotNull
    public AccreditationType accreditationType;

    @NotNull
    private Document document;
}
