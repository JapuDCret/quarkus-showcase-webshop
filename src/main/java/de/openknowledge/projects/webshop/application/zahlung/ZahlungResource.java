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
import de.openknowledge.projects.webshop.infrastructure.zahlung.ZahlungRepository;
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
public class ZahlungResource {

  private static final Logger LOG = LoggerFactory.getLogger(ZahlungResource.class);

  @Inject
  public ZahlungRepository repository;

  @Inject
  public ZahlungApplicationService service;

  // TODO: remove
  @GET
  @APIResponse(responseCode = "200", description = "Zahlungsliste", content = @Content(schema = @Schema(implementation = ZahlungInfoDTO.class)))
  public Response getZahlungen() {
    Set<Zahlung> zahlungen = repository.read();

    Set<ZahlungInfoDTO> zahlungInfos = zahlungen.stream()
            .map((zahlung) -> ZahlungInfoDTO.of(zahlung))
            .collect(Collectors.toSet());

    LOG.info("zahlungInfos = {}", zahlungInfos);

    return Response.status(Response.Status.OK)
            .entity(zahlungInfos)
            .build();
  }

  @GET
  @Path("{bestellungId}")
  @APIResponse(responseCode = "200", description = "Info über die Zahlung", content = @Content(schema = @Schema(implementation = ZahlungInfoDTO.class)))
  @APIResponse(responseCode = "404", description = "Zahlung nicht gefunden")
  public Response getZahlung(
          @PathParam("bestellungId") final String bestellungId
  ) {
    LOG.debug("Retrieving Zahlung for BestellungId \"{}\"", bestellungId);

    ZahlungInfoDTO zahlungInfo = service.getZahlungInfo(bestellungId);

    LOG.debug("Returning {}", zahlungInfo);

    return Response.status(Response.Status.OK)
            .entity(zahlungInfo)
            .build();
  }

  @POST
  @Path("{bestellungId}/authorize")
  @APIResponse(responseCode = "201", description = "Zahlung aktualisiert", content = @Content(schema = @Schema(implementation = ZahlungInfoDTO.class)))
  @APIResponse(responseCode = "404", description = "Zahlung nicht gefunden")
  public Response authorizeZahlung(
          @PathParam("bestellungId") final String bestellungId
  ) {
    LOG.debug("Authorizing Zahlung with BestellungId \"{}\"", bestellungId);

    ZahlungInfoDTO zahlungInfo = service.authorize(bestellungId);

    LOG.debug("Returning {}", zahlungInfo);

    return Response.status(Response.Status.ACCEPTED)
            .entity(zahlungInfo)
            .build();
  }
}
