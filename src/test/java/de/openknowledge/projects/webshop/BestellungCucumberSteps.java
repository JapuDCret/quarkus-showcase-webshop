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

import javax.validation.ValidationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

  private Map<Integer, String> produktMap;

  @Given("den zur Auswahl stehenden Produkten:")
  public void and_the_available_products(
          final List<Map<String, String>> table
  ) {
    Map<Integer, String> produktMap = new HashMap<>();

    for(Map<String, String> produkt : table) {
      Integer id = Integer.valueOf(produkt.get("id"));
      String name = produkt.get("name");

      produktMap.put(id, name);
    }

    this.produktMap = produktMap;
  }

  @When("der Kunde bestellt die Produkte {string}")
  public void when_a_customer_submits_a_bestellung(
          final String produktAuswahlString
  ) {
    Map<Integer, Integer> produktAuswahl = this.parseProduktAuswahl(produktAuswahlString);

    List<ProduktAuswahlDTO> produktListe = new ArrayList<>() {{
      for(Integer id : produktAuswahl.keySet()) {
        Integer anzahl = produktAuswahl.get(id);
        String name = BestellungCucumberSteps.this.produktMap.get(id);

        add(new ProduktAuswahlDTO(name, anzahl));
      }
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

  /**
   * parses a string of format "[1:2,2:2,3:2]" to (id=1,count=2), (id=2,count=2), (id=3,count=2)
   *
   * @param produktAuswahlString
   * @return
   */
  private Map<Integer, Integer> parseProduktAuswahl(String produktAuswahlString) {
    Map<Integer, Integer> produktAuswahl = new HashMap<>();

    char firstChar = produktAuswahlString.charAt(0);
    char lastChar = produktAuswahlString.charAt(produktAuswahlString.length()-1);

    if('[' != firstChar || ']' != lastChar) {
      String errMsg = String.format("parseProduktAuswahl(): Invalid format, should start with '[' and end with ']', input was \"%s\"", produktAuswahlString);
      throw new ValidationException(errMsg);
    }

    String[] produkts = produktAuswahlString.substring(1, produktAuswahlString.length() - 1).split("\\,");

    for(String produkt : produkts) {
      String[] auswahl = produkt.split("\\:");
      if(auswahl.length != 2) {
        String errMsg = String.format("parseProduktAuswahl(): Inner object should be of format '<id>:<count>', input was \"%s\"", produktAuswahlString);
        throw new ValidationException(errMsg);
      }
      String id = auswahl[0];
      String count = auswahl[1];

      produktAuswahl.put(Integer.valueOf(id), Integer.valueOf(count));
    }

    return produktAuswahl;
  }
}
