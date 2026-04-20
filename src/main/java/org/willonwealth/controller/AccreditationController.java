package org.willonwealth.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.willonwealth.dto.request.AccreditationRequestDTO;
import org.willonwealth.dto.request.FinalizeRequestDTO;
import org.willonwealth.dto.response.AccreditationIdResponse;
import org.willonwealth.dto.response.AccreditationListResponse;
import org.willonwealth.service.AccreditationService;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class AccreditationController {

    // 1. Injeção por construtor (Boa prática: campo final)
    private final AccreditationService service;

    public AccreditationController(AccreditationService service) {
        this.service = service;
    }

    /**
     * Cria uma nova acreditação.
     * Rota: POST /user/accreditation
     */
    @PostMapping("/accreditation")
    public ResponseEntity<AccreditationIdResponse> create(@Valid @RequestBody AccreditationRequestDTO dto) {

        AccreditationIdResponse id = service.createAccreditation(dto);
        AccreditationIdResponse response = new AccreditationIdResponse(id.accreditationId());

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping("/accreditation/{accreditationId}")
    public ResponseEntity<AccreditationIdResponse> finalize(
            @PathVariable UUID accreditationId,
            @Valid @RequestBody FinalizeRequestDTO dto) {

        service.finalizeAccreditation(accreditationId, dto.outcome());
        return ResponseEntity.ok(new AccreditationIdResponse(accreditationId));
    }

    /**
     * Lista as acreditações de um usuário.
     * Rota: GET /user/{userId}/accreditation
     */
    @GetMapping("/{userId}/accreditation")
    public ResponseEntity<AccreditationListResponse> getByUser(@PathVariable String userId) {

        AccreditationListResponse response = service.getUserAccreditations(userId);
        return ResponseEntity.ok(response);
    }
}