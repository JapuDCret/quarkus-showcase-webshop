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
package de.openknowledge.projects.webshop.application.zahlungsart;

import de.openknowledge.projects.webshop.domain.zahlung.ZahlungsArt;
import de.openknowledge.projects.webshop.infrastructure.zahlungsart.ZahlungsArtRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * A resource that provides access to the {@link ZahlungsArt}en.
 */
@Path("zahlungsart")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class ZahlungsArtResource {

  private static final Logger LOG = LoggerFactory.getLogger(ZahlungsArtResource.class);

  @Inject
  public ZahlungsArtRepository repository;

  @GET
  public Response getZahlungsarten() {
    List<ZahlungsArt> zahlungsArten = repository.read();

    LOG.info("{}", zahlungsArten);

    return Response.status(Response.Status.OK)
        .entity(zahlungsArten)
        .build();
  }
}
