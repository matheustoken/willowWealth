package org.willonwealth.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.willonwealth.dto.request.AccreditationRequestDTO;
import org.willonwealth.dto.request.DocumentDTO;
import org.willonwealth.dto.response.AccreditationIdResponse;
import org.willonwealth.exception.ConflictException;
import org.willonwealth.exception.ResourceNotFoundException;
import org.willonwealth.exception.BadRequestException;
import org.willonwealth.mapper.AccreditationMapper;
import org.willonwealth.model.Accreditation;
import org.willonwealth.model.AccreditationStatus;
import org.willonwealth.model.AccreditationType;
import org.willonwealth.repository.AccreditationRepository;
import org.willonwealth.repository.AuditLogRepository;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccreditationServiceTest {

    @Mock
    private AccreditationRepository repository;
    @Mock
    private AuditLogRepository auditRepository;
    @Mock
    private AccreditationMapper mapper;

    @InjectMocks
    private AccreditationService service;

    private AccreditationRequestDTO validDto;
    private Accreditation accreditation;
    private UUID accreditationId;

    @BeforeEach
    void setUp() {
        accreditationId = UUID.randomUUID();
        DocumentDTO docDto = new DocumentDTO("test.pdf", "application/pdf", "base64content");
        validDto = new AccreditationRequestDTO("user123", AccreditationType.BY_INCOME, docDto);

        accreditation = new Accreditation();
        accreditation.setAccreditationId(accreditationId);
        accreditation.setStatus(AccreditationStatus.PENDING);
        accreditation.setUserId("user123");
    }

    @Test
    void shouldCreateAccreditationWhenNoPendingStatusExists() {
        when(repository.existsByUserIdAndStatus("user123", AccreditationStatus.PENDING)).thenReturn(false);
        when(mapper.toEntity(validDto)).thenReturn(accreditation);
        when(repository.save(any())).thenReturn(accreditation);

        AccreditationIdResponse response = service.createAccreditation(validDto);

        assertNotNull(response);
        assertEquals(accreditationId, response.accreditationId());
        verify(auditRepository, times(1)).save(any());
    }

    @Test
    void shouldThrowConflictExceptionWhenUserAlreadyHasPendingAccreditation() {
        when(repository.existsByUserIdAndStatus("user123", AccreditationStatus.PENDING)).thenReturn(true);

        assertThrows(ConflictException.class, () -> service.createAccreditation(validDto));
        verify(repository, never()).save(any());
    }

    @Test
    void shouldFinalizeAccreditationWhenTransitionIsFromPendingToConfirmed() {
        when(repository.findByAccreditationId(accreditationId)).thenReturn(Optional.of(accreditation));

        service.finalizeAccreditation(accreditationId, AccreditationStatus.CONFIRMED);

        assertEquals(AccreditationStatus.CONFIRMED, accreditation.getStatus());
        verify(repository, times(1)).save(accreditation);
    }

    @Test
    void shouldThrowBadRequestExceptionWhenUpdatingFailedAccreditation() {
        accreditation.setStatus(AccreditationStatus.FAILED);
        when(repository.findByAccreditationId(accreditationId)).thenReturn(Optional.of(accreditation));

        assertThrows(BadRequestException.class, () ->
                service.finalizeAccreditation(accreditationId, AccreditationStatus.CONFIRMED));
    }

    @Test
    void shouldAllowTransitionWhenConfirmedAccreditationIsSetToExpired() {
        accreditation.setStatus(AccreditationStatus.CONFIRMED);
        when(repository.findByAccreditationId(accreditationId)).thenReturn(Optional.of(accreditation));

        service.finalizeAccreditation(accreditationId, AccreditationStatus.EXPIRED);

        assertEquals(AccreditationStatus.EXPIRED, accreditation.getStatus());
        verify(repository).save(any());
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenAccreditationIdDoesNotExist() {
        when(repository.findByAccreditationId(accreditationId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                service.finalizeAccreditation(accreditationId, AccreditationStatus.CONFIRMED));
    }
}
