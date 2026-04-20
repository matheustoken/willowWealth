package org.willonwealth.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record AccreditationIdResponse(
        @JsonProperty("accreditation_id")
        UUID accreditationId
) {}

