package org.willonwealth.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.willonwealth.model.AccreditationType;

public record AccreditationRequestDTO(
        @JsonProperty("user_id")
        @NotBlank(message = "User ID is required")
        String userId,

        @JsonProperty("accreditation_type")
        @NotNull(message = "Accreditation type is required")
        AccreditationType accreditation_type,

        @Valid
        @NotNull(message = "Document is required")
        DocumentDTO document
) {}


