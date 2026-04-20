package org.willonwealth.dto.request;

import jakarta.validation.constraints.NotNull;
import org.willonwealth.model.AccreditationStatus;

public record FinalizeRequestDTO(
        @NotNull AccreditationStatus outcome
)
{}
