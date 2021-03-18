/*
 * Copyright (C) open knowledge GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
package de.openknowledge.projects.webshop.application.zahlung;

import de.openknowledge.projects.webshop.domain.zahlung.Zahlung;
import de.openknowledge.projects.webshop.infrastructure.zahlungsart.ZahlungsRepository;
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
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A resource that provides access to the {@link Zahlung}e.
 */
@Path("zahlung")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class ZahlungsResource {

  private static final Logger LOG = LoggerFactory.getLogger(ZahlungsResource.class);

  @Inject
  public ZahlungsRepository repository;

  @Inject
  public ZahlungApplicationService service;

  // TODO: remove
  @GET
  @APIResponse(responseCode = "200", description = "Zahlungsliste", content = @Content(schema = @Schema(implementation = ZahlungsInfoDTO.class)))
  public Response getZahlungen() {
    Set<Zahlung> zahlungen = repository.read();

    Set<ZahlungsInfoDTO> zahlungsInfos = zahlungen.stream()
            .map((zahlung) -> ZahlungsInfoDTO.of(zahlung))
            .collect(Collectors.toSet());

    LOG.info("zahlungsInfos = {}", zahlungsInfos);

    return Response.status(Response.Status.OK)
            .entity(zahlungsInfos)
            .build();
  }

  @GET
  @Path("{bestellId}")
  @APIResponse(responseCode = "200", description = "Info über die Zahlung", content = @Content(schema = @Schema(implementation = ZahlungsInfoDTO.class)))
  @APIResponse(responseCode = "404", description = "Zahlung nicht gefunden")
  public Response getZahlung(
          @PathParam("bestellId") final String bestellId
  ) {
    LOG.debug("Retrieving Zahlung for BestellId \"{}\"", bestellId);

    ZahlungsInfoDTO zahlungsInfo = service.getZahlungInfo(bestellId);

    LOG.debug("Returning {}", zahlungsInfo);

    return Response.status(Response.Status.OK)
            .entity(zahlungsInfo)
            .build();
  }

  @POST
  @Path("{bestellId}/authorize")
  @APIResponse(responseCode = "201", description = "Zahlung aktualisiert", content = @Content(schema = @Schema(implementation = ZahlungsInfoDTO.class)))
  @APIResponse(responseCode = "404", description = "Zahlung nicht gefunden")
  public Response authorizeZahlung(
          @PathParam("bestellId") final String bestellId
  ) {
    LOG.debug("Authorizing Zahlung with BestellId \"{}\"", bestellId);

    ZahlungsInfoDTO zahlungsInfo = service.authorize(bestellId);

    LOG.debug("Returning {}", zahlungsInfo);

    return Response.status(Response.Status.ACCEPTED)
            .entity(zahlungsInfo)
            .build();
  }
}
