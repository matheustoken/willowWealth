package org.willonwealth.service;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.willonwealth.dto.request.AccreditationRequestDTO;
import org.willonwealth.dto.response.AccreditationIdResponse;
import org.willonwealth.dto.response.AccreditationListResponse;
import org.willonwealth.mapper.AccreditationMapper;
import org.willonwealth.model.Accreditation;
import org.willonwealth.model.AccreditationAuditLog;
import org.willonwealth.model.AccreditationStatus;
import org.willonwealth.repository.AccreditationRepository;
import org.willonwealth.repository.AuditLogRepository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service
public class AccreditationService {

    private final AccreditationRepository repository;
    private final AuditLogRepository auditRepository;
    private final AccreditationMapper mapper;

    public AccreditationService(AccreditationRepository repository, AuditLogRepository auditRepository, AccreditationMapper mapper) {
        this.repository = repository;
        this.auditRepository = auditRepository;
        this.mapper = mapper;
    }

    public AccreditationIdResponse createAccreditation(AccreditationRequestDTO dto) {

        if (repository.existsByUserIdAndStatus(dto.userId(), AccreditationStatus.PENDING)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already has PENDING accreditation");
        }

        Accreditation acc = mapper.toEntity(dto);

        Accreditation saved = repository.save(acc);

        registerAuditLog(saved.getAccreditationId(), saved.getStatus());

        return new AccreditationIdResponse(saved.getAccreditationId());

    }

    public void finalizeAccreditation(UUID accreditationId, AccreditationStatus outcome) {
        Accreditation acc = repository.findByAccreditationId(accreditationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Accreditation not found"));

        if (acc.getStatus() == AccreditationStatus.FAILED) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "A FAILED accreditation status should not be updateable.");
        }

        if (acc.getStatus() == AccreditationStatus.CONFIRMED && outcome != AccreditationStatus.EXPIRED) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "A CONFIRMED accreditation can only be updated to EXPIRED.");
        }

        acc.setStatus(outcome);
        acc.setLastUpdated(Instant.now());
        repository.save(acc);
        registerAuditLog(acc.getAccreditationId(), outcome);
    }

    public AccreditationListResponse getUserAccreditations(String userId) {
        List<Accreditation> accreditations = repository.findByUserId(userId);

        AccreditationListResponse response = new AccreditationListResponse();
        response.setUser_id(userId);

        Map<String, Map<String, String>> statuses = new LinkedHashMap<>();

        for (Accreditation acc : accreditations) {
            Map<String, String> details = new LinkedHashMap<>();
            details.put("accreditation_type", acc.getType().toString());
            details.put("status", acc.getStatus().toString());

            statuses.put(acc.getId().toString(), details);
        }

        response.setAccreditation_statuses(statuses);
        return response;
    }

    private void registerAuditLog(UUID id, AccreditationStatus status) {
        auditRepository.save(new AccreditationAuditLog(String.valueOf(id), status));
    }

    @Scheduled(fixedRate = 60000) // Executa a cada 1 minuto (ajuste conforme necessário)
    @Transactional
    public void expireOldAccreditations() {

        Instant cutoff = Instant.now().minus(30, ChronoUnit.DAYS);

        List<Accreditation> toExpire = repository.findByStatusAndLastUpdatedBefore(
                AccreditationStatus.CONFIRMED, cutoff);

        for (Accreditation acc : toExpire) {
            acc.setStatus(AccreditationStatus.EXPIRED);
            acc.setLastUpdated(Instant.now());

            repository.save(acc);

            registerAuditLog(acc.getAccreditationId(), AccreditationStatus.EXPIRED);

            System.out.println("Scheduler: Accreditation " + acc.getAccreditationId() + " has expired.");
        }
    }


}
