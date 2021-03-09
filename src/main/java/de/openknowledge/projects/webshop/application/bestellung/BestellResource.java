package de.openknowledge.projects.webshop.application.bestellung;

import de.openknowledge.projects.webshop.domain.bestellung.Bestellung;
import de.openknowledge.projects.webshop.infrastructure.bestellung.BestellRepository;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * A resource that provides access to the {@link Bestellung}en.
 */
@Path("bestellung")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class BestellResource {

    private static final Logger LOG = LoggerFactory.getLogger(BestellResource.class);

    @Inject
    private BestellService service;

    @Inject
    private BestellRepository repository;

    @GET
    public Response getBestellungen() {
        List<Bestellung> bestellungen = repository.read();

        LOG.info("{}", bestellungen);

        return Response.status(Response.Status.OK)
                .entity(bestellungen)
                .build();
    }

    @PUT
    @Operation(operationId = "placeBestellung", description = "Bestellung wird abgeschickt")
    @APIResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = BestellungDTO.class)))
    public Response placeBestellung(final BestellungDTO dto) {
        LOG.info("Placing Bestellung {}", dto);

        service.placeBestellung(dto);

        LOG.info("Bestellung created");

        return Response.status(Response.Status.CREATED)
                .build();
    }
}
