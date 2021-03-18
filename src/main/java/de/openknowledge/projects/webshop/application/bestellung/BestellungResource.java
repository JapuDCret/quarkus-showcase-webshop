package de.openknowledge.projects.webshop.application.bestellung;

import de.openknowledge.projects.webshop.domain.bestellung.Bestellung;
import de.openknowledge.projects.webshop.infrastructure.bestellung.BestellungRepository;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Set;

/**
 * A resource that provides access to the {@link Bestellung}en.
 */
@Path("bestellung")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class BestellungResource {

    private static final Logger LOG = LoggerFactory.getLogger(BestellungResource.class);

    @Inject
    private BestellungApplicationService service;

    @Inject
    private BestellungRepository repository;

    // TODO: remove
    @GET
    public Response getBestellungen() {
        Set<Bestellung> bestellungen = repository.read();

        LOG.info("{}", bestellungen);

        return Response.status(Response.Status.OK)
                .entity(bestellungen)
                .build();
    }

    @PUT
    @Operation(operationId = "placeBestellung", description = "Bestellung wird abgeschickt")
    @APIResponse(responseCode = "202", description = "Bestellung angenommen", content = @Content(schema = @Schema(implementation = BestellungInfoDTO.class)))
    @APIResponse(responseCode = "400", description = "Bestellung abgelehnt")
    public Response placeBestellung(
            @RequestBody(name = "bestellung", required = true, content = @Content(schema = @Schema(implementation = BestellungDTO.class)))
            @NotNull @Valid final BestellungDTO bestellungDTO
    ) {
        LOG.debug("Placing Bestellung {}", bestellungDTO);

        BestellungInfoDTO bestellungInfoDTO = service.placeBestellung(bestellungDTO);

        LOG.debug("Bestellung successfully placed, returning {}", bestellungInfoDTO);

        return Response.status(Response.Status.CREATED)
                .entity(bestellungInfoDTO)
                .build();
    }
}