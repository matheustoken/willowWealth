package org.willonwealth.service;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.WebApplicationException;
import org.willonwealth.dto.request.CreateAccreditationDto;
import org.willonwealth.mapper.AccreditationMapper;
import org.willonwealth.model.Accreditation;
import org.willonwealth.model.AccreditationStatus;
import org.willonwealth.model.AccreditationType;
import org.willonwealth.model.Document;
import org.willonwealth.repository.AccreditationRepository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;


@ApplicationScoped
public class AccreditationService {

    private final AccreditationRepository repository;

    public AccreditationService(AccreditationRepository repository) {
        this.repository = repository;
    }

    public Uni<String> createAccreditation(CreateAccreditationDto dto) {
        return repository.findPendingByUserId(dto.getUserId()).
        chain(existing -> {
            // Se 'existing' não for nulo, significa que já existe uma pendente
            if (existing != null) {
                throw new WebApplicationException("User already has a pending request", 409);
            }
                    Accreditation acc = new Accreditation();
                    acc.setId(UUID.randomUUID().toString());
                    acc.setUserId(dto.getUserId());
                    acc.setType(dto.getAccreditationType());
                    acc.setStatus(AccreditationStatus.PENDING);
                    acc.setLastUpdated(Instant.now());

                    if (dto.getDocument() != null) {
                        Document doc = new Document();
                        doc.setName(dto.getDocument().getName());
                        doc.setMimeType(dto.getDocument().getMimeType()); // Ajuste conforme seu DTO
                        doc.setContent(dto.getDocument().getContent());
                        acc.setDocuments(doc); // Nome do campo na sua entidade é 'documents'
                    }

                    return repository.persist(acc)
                            .map(saved -> saved.id);
                });
    }

}
