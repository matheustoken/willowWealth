package org.willonwealth.mapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.willonwealth.dto.request.AccreditationRequestDTO;
import org.willonwealth.dto.request.DocumentDTO;
import org.willonwealth.model.Accreditation;
import org.willonwealth.model.AccreditationStatus;
import org.willonwealth.model.Document;

import java.time.Instant;
import java.util.UUID;

@Mapper(
        componentModel = "spring",
        imports = { UUID.class, AccreditationStatus.class, Instant.class }
)
public interface AccreditationMapper {

    @Mapping(target = "type", source = "accreditation_type")
    @Mapping(target = "documents", source = "document")
    @Mapping(target = "status", expression = "java(AccreditationStatus.PENDING)")
    @Mapping(target = "lastUpdated", expression = "java(Instant.now())")
    @Mapping(target = "accreditationId",expression = "java(UUID.randomUUID())")
    Accreditation toEntity(AccreditationRequestDTO dto);

    @Mapping(target = "mimeType", source = "mime_type")
    Document toDocument(DocumentDTO dto);

}



