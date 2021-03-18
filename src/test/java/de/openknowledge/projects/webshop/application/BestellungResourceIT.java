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
package de.openknowledge.projects.webshop.application;

import com.github.database.rider.cdi.api.DBRider;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.SeedStrategy;
import de.openknowledge.projects.webshop.DatabaseTestResource;
import de.openknowledge.projects.webshop.application.bestellung.*;
import de.openknowledge.projects.webshop.application.zahlung.ZahlungInfoDTO;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Integration test for the resource {@link ProduktResource}.
 */
@QuarkusTest
@QuarkusTestResource(DatabaseTestResource.class)
@TestHTTPEndpoint(BestellungResource.class)
@DBRider
@DataSet(value = "webshop.yml", strategy = SeedStrategy.CLEAN_INSERT, skipCleaningFor = "flyway_schema_history")
class BestellungResourceIT {

  private static final Logger LOG = LoggerFactory.getLogger(BestellungResourceIT.class);

  @Test
  void getBestellungenShouldReturn200And0Bestellungen() {
    RestAssured.given()
            .accept(MediaType.APPLICATION_JSON)
            .when()
            .get()
            .then()
            .statusCode(Response.Status.OK.getStatusCode())
            .contentType(MediaType.APPLICATION_JSON)
            .body("$", Matchers.hasSize(0));
  }

  @Test
  void putBestellungShouldReturn202AndZahlungInfo() {
    // create DTO
    List<ProduktAuswahlDTO> produktDTOListe = new ArrayList<>() {{
      add(new ProduktAuswahlDTO("Salat", 2));
      add(new ProduktAuswahlDTO("Dressing", 2));
      add(new ProduktAuswahlDTO("Ciabatta", 2));
      add(new ProduktAuswahlDTO("Getränk", 1));
    }};
    LieferAdresseDTO lieferAdresseDTO = new LieferAdresseDTO(
            "Max",
            "Mustermann",
            "Musterallee 42",
            "12345",
            "Berlin"
    );
    BestellungDTO bestellungDTO = new BestellungDTO(produktDTOListe, lieferAdresseDTO);

    ZahlungInfoDTO zahlungInfo = RestAssured.given()
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .when()
            .body(bestellungDTO)
            .put()
            .then()
            .statusCode(Response.Status.CREATED.getStatusCode())
            .contentType(MediaType.APPLICATION_JSON)
            .extract()
            .response()
            .as(ZahlungInfoDTO.class);

    // 8.5*2 + 0.5*2 + 1.5*2 + 2.0*1 = 23.0
    Assertions.assertThat(zahlungInfo.getBetrag()).isEqualTo(23.0d);
  }
}
