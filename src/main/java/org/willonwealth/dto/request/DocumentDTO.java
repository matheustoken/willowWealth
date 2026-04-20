package org.willonwealth.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record DocumentDTO(
        @NotBlank(message = "Document name is required")
        String name,

        @JsonProperty("mime_type")
        @NotBlank(message = "Mime type is required")
        String mime_type,

        @NotBlank(message = "Content is required")
        String content
) {}
