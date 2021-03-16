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
import de.openknowledge.projects.webshop.application.bestellung.ProduktDTO;
import de.openknowledge.projects.webshop.application.bestellung.ProduktResource;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.hamcrest.Description;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Integration test for the resource {@link ProduktResource}.
 */
@QuarkusTest
@QuarkusTestResource(DatabaseTestResource.class)
@TestHTTPEndpoint(ProduktResource.class)
@DBRider
@DataSet(value = "webshop.yml", strategy = SeedStrategy.CLEAN_INSERT, skipCleaningFor = "flyway_schema_history")
class ProductResourceIT {

  private static final Logger LOG = LoggerFactory.getLogger(ProductResourceIT.class);

  @Test
  void getProductsShouldReturn200And6Produkte() {
    RestAssured.given()
            .accept(MediaType.APPLICATION_JSON)
            .when()
            .get()
            .then()
            .statusCode(Response.Status.OK.getStatusCode())
            .contentType(MediaType.APPLICATION_JSON)
            .body("$", Matchers.hasSize(6));
  }

  private static final List<ProduktDTO> EXPECTED_PRODUKTLISTE = new ArrayList<>() {{
    add(new ProduktDTO("Salat", 8.5));;
    add(new ProduktDTO("Bowl", 11.0));
    add(new ProduktDTO("Ciabatta", 1.5));
    add(new ProduktDTO("Getränk", 2.0));
    add(new ProduktDTO("Dip", 1.0));
    add(new ProduktDTO("Dressing", 0.5));
  }};

  @Test
  void getProductsShouldReturn200And6SpecificProdukte() {
    final TypeSafeMatcher produktListMatcher = new TypeSafeMatcher<List<Map>>() {
      private final Logger LOG = LoggerFactory.getLogger(TypeSafeMatcher.class);

      @Override
      public void describeTo(Description description) {
        description.appendText("The following list of products " + EXPECTED_PRODUKTLISTE);
      }

      @Override
      protected boolean matchesSafely(List<Map> list) {
        for(Map produktAsMap : list) {
          Object name = produktAsMap.get("name");
          Object preis = produktAsMap.get("preis");
          if(!(name instanceof String)) {
            LOG.warn("name of produkt <{}> is not a String, was <{}> ({})", produktAsMap, name, name == null ? null : name.getClass());
            return false;
          }
          if(!(preis instanceof Number)) {
            LOG.warn("preis of produkt <{}> is not a Number, was <{}> ({})", produktAsMap, preis, preis == null ? null : preis.getClass());
            return false;
          }
          boolean containsMatch = false;
          for(ProduktDTO produkt : EXPECTED_PRODUKTLISTE) {
            if(produkt.getName().equals(name)) {
              if(preis != null && produkt.getPreis() == ((Number) preis).doubleValue()) {
                containsMatch = true;
              } else {
                LOG.warn("produkt <{}> has a different preis than expected produkt with the same name <{}>", produktAsMap, produkt);
                return false;
              }
            }
          }
          if(!containsMatch) {
            LOG.warn("produkt <{}> could not be found in list of expectations", produktAsMap);
            return false;
          }
        }

        return true;
      }
    };


    RestAssured.given()
            .accept(MediaType.APPLICATION_JSON)
            .when()
            .get()
            .then()
            .statusCode(Response.Status.OK.getStatusCode())
            .contentType(MediaType.APPLICATION_JSON)
            .body("$", produktListMatcher);
  }
}
