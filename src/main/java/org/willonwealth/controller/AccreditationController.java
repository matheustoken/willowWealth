package org.willonwealth.controller;

import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.willonwealth.dto.request.CreateAccreditationDto;
import org.willonwealth.dto.response.AccreditationIdResponse;
import org.willonwealth.service.AccreditationService;

import java.util.Map;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AccreditationController {

    private AccreditationService accreditationService;

    public AccreditationController(AccreditationService accreditationService) {
        this.accreditationService = accreditationService;
    }

    @POST
    @Path("/accreditation")
    public Uni<AccreditationIdResponse> createAccreditation(@Valid CreateAccreditationDto dto) {
        return accreditationService.createAccreditation(dto)
                .map(AccreditationIdResponse::new);
    }
}
