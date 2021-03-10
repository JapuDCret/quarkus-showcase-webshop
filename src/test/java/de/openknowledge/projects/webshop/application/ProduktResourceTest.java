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

import de.openknowledge.projects.webshop.application.bestellung.ProduktDTO;
import de.openknowledge.projects.webshop.application.bestellung.ProduktResource;
import de.openknowledge.projects.webshop.domain.bestellung.Produkt;
import de.openknowledge.projects.webshop.infrastructure.bestellung.ProduktRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for the resource {@link ProduktResource}.
 */
@ExtendWith(MockitoExtension.class)
class ProduktResourceTest {

  @InjectMocks
  private ProduktResource resource;

  @Mock
  private ProduktRepository repository;

  @BeforeEach
  void setUp() {
    Mockito.lenient().doCallRealMethod().when(repository).read();
  }

  private static final List<Produkt> PROVIDED_PRODUKTLISTE = new ArrayList<>() {{
    add(new Produkt("Cocktail", BigDecimal.valueOf(47.11d)));
  }};
  private static final List<ProduktDTO> EXPECTED_PRODUKTLISTE = new ArrayList<>() {{
    add(new ProduktDTO("Cocktail", 47.11d));
  }};

  @Test
  void getProdukteShouldReturnProduktliste() {
    Mockito.doReturn(PROVIDED_PRODUKTLISTE).when(repository).read();

    Response response = resource.getProdukte();
    assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
    assertThat(response.getEntity()).isEqualTo(EXPECTED_PRODUKTLISTE);

    Mockito.verify(repository).read();
    Mockito.verifyNoMoreInteractions(repository);
  }
}
