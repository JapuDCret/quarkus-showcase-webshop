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

import de.openknowledge.projects.webshop.application.bestellung.FilialDTO;
import de.openknowledge.projects.webshop.application.bestellung.FilialResource;
import de.openknowledge.projects.webshop.domain.bestellung.filiale.FilialAdresse;
import de.openknowledge.projects.webshop.domain.bestellung.filiale.FilialName;
import de.openknowledge.projects.webshop.domain.bestellung.filiale.Filiale;
import de.openknowledge.projects.webshop.infrastructure.bestellung.FilialRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for the resource {@link FilialResource}.
 */
@ExtendWith(MockitoExtension.class)
class FilialResourceTest {

  @InjectMocks
  private FilialResource resource;

  @Mock
  private FilialRepository repository;

  @BeforeEach
  void setUp() {
    Mockito.lenient().doCallRealMethod().when(repository).read();
  }

  private static final List<Filiale> PROVIDED_FILIALEN = new ArrayList<>() {{
    add(Filiale.Builder()
            .setName(new FilialName("Restaurant 'Beispiel'"))
            .setFilialAdresse(
              FilialAdresse.Builder()
                .setPostAnschriftName("Beispiel GmbH")
                .setStrasse("Lindenallee 42")
                .setPlz("12345")
                .setStadtName("Berlin")
                .build()
            )
            .build()
    );
  }};
  private static final List<FilialDTO> EXPECTED_FILIALEN = new ArrayList<>() {{
    add(new FilialDTO("Restaurant 'Beispiel'", "Lindenallee 42", "12345", "Berlin"));
  }};

  @Test
  void getFilialenShouldReturnFilialliste() {
    Mockito.doReturn(PROVIDED_FILIALEN).when(repository).read();

    Response response = resource.getFilialen();
    assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
    assertThat(response.getEntity()).isEqualTo(EXPECTED_FILIALEN);

    Mockito.verify(repository).read();
    Mockito.verifyNoMoreInteractions(repository);
  }
}
