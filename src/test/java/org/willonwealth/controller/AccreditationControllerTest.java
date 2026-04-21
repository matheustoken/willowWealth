package org.willonwealth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.willonwealth.controller.handler.CustomExceptionHandler;
import org.willonwealth.dto.request.AccreditationRequestDTO;
import org.willonwealth.dto.request.DocumentDTO;
import org.willonwealth.dto.request.FinalizeRequestDTO;
import org.willonwealth.dto.response.AccreditationIdResponse;
import org.willonwealth.exception.BadRequestException;
import org.willonwealth.exception.ConflictException;
import org.willonwealth.model.AccreditationStatus;
import org.willonwealth.model.AccreditationType;
import org.willonwealth.service.AccreditationService;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AccreditationControllerTest {

    private MockMvc mockMvc;
    private final ObjectMapper mapper = new ObjectMapper();

    @Mock
    private AccreditationService service;

    @InjectMocks
    private AccreditationController controller;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new CustomExceptionHandler())
                .build();
    }

    @Test
    void should_ReturnCreated_When_PostAccreditationIsSuccessful() throws Exception {
        var id = UUID.randomUUID();
        var request = new AccreditationRequestDTO("user123", AccreditationType.BY_INCOME,
                new DocumentDTO("doc.pdf", "application/pdf", "YmFzZTY0"));

        when(service.createAccreditation(any())).thenReturn(new AccreditationIdResponse(id));

        mockMvc.perform(post("/user/accreditation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accreditation_id").value(id.toString()));
    }

    @Test
    void should_Return409Conflict_When_UserAlreadyHasPendingAccreditation() throws Exception {
        String userId = "matheus_accorsi_01";
        String errorMessage = "User already has PENDING accreditation";
        List<String> details = List.of("User ID: " + userId);

        var request = new AccreditationRequestDTO(
                userId,
                AccreditationType.BY_NET_WORTH,
                new DocumentDTO("irpf_2023.pdf", "application/pdf", "S0NPTiB0ZXN0ZSBkZSBiYXNlNjQ=")
        );

        when(service.createAccreditation(any(AccreditationRequestDTO.class)))
                .thenThrow(new ConflictException(errorMessage, details));

        mockMvc.perform(post("/user/accreditation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error.message").value(errorMessage))
                .andExpect(jsonPath("$.error.details[0]").value("User ID: " + userId));
    }

    @Test
    void should_ReturnOk_When_FinalizeAccreditationIsSuccessful() throws Exception {
        UUID accreditationId = UUID.fromString("8a103708-aac2-4aab-9339-357d650ca1f6");

        var requestBody = new FinalizeRequestDTO(AccreditationStatus.CONFIRMED);

        doNothing().when(service).finalizeAccreditation(
                eq(accreditationId),
                eq(AccreditationStatus.CONFIRMED)
        );

        mockMvc.perform(put("/user/accreditation/{id}", accreditationId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accreditation_id").value(accreditationId.toString()));
    }

    @Test
    void should_ReturnBadRequest_When_TryingToConfirmAFailedAccreditation() throws Exception {

        UUID id = UUID.fromString("8a103708-aac2-4aab-9339-357d650ca1f6");
        var requestBody = new FinalizeRequestDTO(AccreditationStatus.CONFIRMED);

        String errorMessage = "A FAILED accreditation status should not be updateable.";
        List<String> details = List.of("Current status: FAILED", "Target status: CONFIRMED");

        doThrow(new BadRequestException(errorMessage, details))
                .when(service)
                .finalizeAccreditation(eq(id), eq(AccreditationStatus.CONFIRMED));

        mockMvc.perform(put("/user/accreditation/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(requestBody)))
                .andExpect(status().isBadRequest()) // 400
                .andExpect(jsonPath("$.error.message").value(errorMessage))
                .andExpect(jsonPath("$.error.details[0]").value("Current status: FAILED"))
                .andExpect(jsonPath("$.error.details[1]").value("Target status: CONFIRMED"));
    }


    @Test
    void should_ReturnBadRequest_When_PathVariableIsMalformed() throws Exception {
        mockMvc.perform(put("/user/accreditation/id-invalido")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(new FinalizeRequestDTO(AccreditationStatus.CONFIRMED))))
                .andExpect(status().isBadRequest());
    }
}