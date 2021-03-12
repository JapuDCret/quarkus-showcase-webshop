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
package de.openknowledge.projects.webshop;

import de.openknowledge.projects.webshop.application.bestellung.BestellungDTO;
import de.openknowledge.projects.webshop.application.bestellung.LieferAdresseDTO;
import de.openknowledge.projects.webshop.application.bestellung.ProduktAuswahlDTO;
import de.openknowledge.projects.webshop.application.bestellung.ProduktDTO;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Step Definitions for the cucumber test {@link BestellungCucumberIT}.
 */
public class BestellungCucumberSteps extends AbstractTestcontainersIT {

  private RequestSpecification requestSpecification;

  private io.restassured.response.Response response;

  @Before
  public void beforeScenario() {
    requestSpecification = new RequestSpecBuilder()
        .setPort(APPLICATION.getFirstMappedPort())
        .build();
  }

  @When("ein Kunde die verfügbaren Produkte abrufen möchte")
  public void when_a_customer_requests_the_products() {
    response = RestAssured.given(requestSpecification)
            .accept(MediaType.APPLICATION_JSON)
            .when()
            .get("/produkt/");
  }

  private static final List<ProduktDTO> EXPECTED_PRODUKTLISTE = new ArrayList<>() {{
    add(new ProduktDTO("Salat", 8.5));
    add(new ProduktDTO("Bowl", 11.0));
    add(new ProduktDTO("Ciabatta", 1.5));
    add(new ProduktDTO("Getränk", 2.0));
    add(new ProduktDTO("Dip", 1.0));
    add(new ProduktDTO("Dressing", 0.5));
  }};

  @Then("erhält er eine Liste mit 6 Produkten")
  public void then_the_produkt_list_is_of_length_6_() {
    response.then()
            .statusCode(Response.Status.OK.getStatusCode())
            .contentType(MediaType.APPLICATION_JSON)
            .body("$", Matchers.hasSize(6));
  }

  private LieferAdresseDTO lieferAdresseDTO;

  @Given("Eine Bestellung mit den Lieferdaten {string}, {string}, {string}, {string}, {string}")
  public void given_a_customer_supplies_a_lieferadresse(
          final String vorname,
          final String nachname,
          final String strasse,
          final String plz,
          final String ort
  ) {
    LieferAdresseDTO lieferAdresse = new LieferAdresseDTO(vorname, nachname, strasse, plz, ort);

    this.lieferAdresseDTO = lieferAdresse;
  }

  @When("der Kunde bestellt die Produkte {int}-Mal {string} und {int}-Mal {string}")
  public void when_a_customer_submits_a_bestellung(
          final int p1Anzahl,
          final String p1Name,
          final int p2Anzahl,
          final String p2Name
  ) {
    List<ProduktAuswahlDTO> produktListe = new ArrayList<>() {{
      add(new ProduktAuswahlDTO(p1Name, p1Anzahl));
      add(new ProduktAuswahlDTO(p2Name, p2Anzahl));
    }};
    BestellungDTO bestellung = new BestellungDTO(produktListe, this.lieferAdresseDTO);

    response = RestAssured.given(requestSpecification)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(bestellung)
            .when()
            .put("/bestellung");
  }

  @Then("erhält er eine Zahlungsaufforderung mit einem Betrag von {float}")
  public void then_the_betrag_is_equal_to(final float betrag) {
    this.response.then()
            .statusCode(Response.Status.CREATED.getStatusCode())
            .body("betrag", Matchers.equalTo(betrag));
  }
}
