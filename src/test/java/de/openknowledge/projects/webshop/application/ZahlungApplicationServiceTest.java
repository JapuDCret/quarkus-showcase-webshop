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

import de.openknowledge.projects.webshop.application.bestellung.*;
import de.openknowledge.projects.webshop.application.zahlung.ZahlungApplicationService;
import de.openknowledge.projects.webshop.application.zahlung.ZahlungsInfoDTO;
import de.openknowledge.projects.webshop.domain.bestellung.*;
import de.openknowledge.projects.webshop.domain.zahlung.Zahlung;
import de.openknowledge.projects.webshop.domain.zahlung.ZahlungsAutorisierung;
import de.openknowledge.projects.webshop.infrastructure.bestellung.BestellRepository;
import de.openknowledge.projects.webshop.infrastructure.bestellung.ProduktRepository;
import de.openknowledge.projects.webshop.infrastructure.zahlungsart.ZahlungsRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import javax.ws.rs.NotFoundException;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Test class for the resource {@link ProduktResource}.
 */
@ExtendWith(MockitoExtension.class)
class ZahlungApplicationServiceTest {

  @InjectMocks
  private ZahlungApplicationService zahlungApplicationService;

  @Mock
  private ZahlungsRepository zahlungsRepository;

  private String bestellId;
  private Zahlung baseZahlung;

  @BeforeEach
  public void setUp() {
    ProduktListe.Builder produktListenBuilder = ProduktListe.Builder();

    produktListenBuilder.addProdukt(
            new ProduktAuswahl(new Produkt("ProduktA", BigDecimal.valueOf(3d)), 2)
    );
    produktListenBuilder.addProdukt(
            new ProduktAuswahl(new Produkt("ProduktB", BigDecimal.valueOf(1.2d)), 5)
    );
    produktListenBuilder.addProdukt(
            new ProduktAuswahl(new Produkt("ProduktC", BigDecimal.valueOf(10.5d)), 1)
    );

    ProduktListe produktListe = produktListenBuilder.build();

    LieferAdresse lieferAdresse = LieferAdresse.Builder()
            .setKundenName("Marvin", "Kienitz")
            .setStrasse("Platanenallee 1337")
            .setPlz("12345")
            .setStadtName("Berlin")
            .build();

    Bestellung bestellung = Bestellung.Builder()
            .setProduktListe(produktListe)
            .setLieferadresse(lieferAdresse)
            .build();

    this.bestellId = bestellung.getBestellId().getId();

    Zahlung zahlung = Zahlung.Builder()
            .setBestellung(bestellung)
            .build();

    this.baseZahlung = zahlung;
  }

  @Test
  public void authorizeWithMissingZahlungShouldThrowNotFoundException() {
    Mockito.doReturn(Optional.empty()).when(zahlungsRepository).findById(Mockito.any(BestellId.class));

    // do "authorize" call
    Assertions.catchThrowableOfType(() -> {
      ZahlungsInfoDTO zahlungInfo = zahlungApplicationService.authorize(this.bestellId);

      Assertions.assertThat(zahlungInfo.isAutorisiert()).isTrue();
    }, NotFoundException.class);

    Mockito.verifyNoMoreInteractions(zahlungsRepository);
    Mockito.verify(zahlungsRepository).findById(Mockito.any(BestellId.class));
    Mockito.verifyNoMoreInteractions(zahlungsRepository);
  }

  @Test
  public void authorizeShouldSetAutorisierteAndZeitpunkt() {
    Optional<Zahlung> expectedZahlung = Optional.of(this.baseZahlung);

    Mockito.doReturn(expectedZahlung).when(zahlungsRepository).findById(Mockito.any(BestellId.class));
    Mockito.doNothing().when(zahlungsRepository).update(Mockito.any(Zahlung.class));

    ZonedDateTime startTimeOfTest = ZonedDateTime.now();

    // assume the Zahlung exists
    {
      Optional<Zahlung> zahlung = zahlungsRepository.findById(new BestellId(this.bestellId));

      Assume.assumeTrue(zahlung.isPresent());
    }

    // verify that Zahlung.autorisiert = false
    {
      ZahlungsInfoDTO zahlungInfo = zahlungApplicationService.getZahlungInfo(this.bestellId);

      Assertions.assertThat(zahlungInfo.isAutorisiert()).isFalse();
    }

    // do "authorize" call
    {
      ZahlungsInfoDTO zahlungInfo = zahlungApplicationService.authorize(this.bestellId);

      Assertions.assertThat(zahlungInfo.isAutorisiert()).isTrue();
    }

    // verify that Zahlung.autorisiert = true
    {
      ZahlungsInfoDTO zahlungInfo = zahlungApplicationService.getZahlungInfo(this.bestellId);

      Assertions.assertThat(zahlungInfo.isAutorisiert()).isTrue();
    }

    // verify that the Zahlung in the repository is now also authorized
    Optional<Zahlung> optZahlung = zahlungsRepository.findById(new BestellId(this.bestellId));

    Assertions.assertThat(optZahlung.isPresent()).isTrue();
    Optional<ZahlungsAutorisierung> optAutorisierung = optZahlung.get().getAutorisierung();

    Assertions.assertThat(optAutorisierung.isPresent()).isTrue();
    ZahlungsAutorisierung autorisierung = optAutorisierung.get();

    // check if ZahlungsAutorisierung.zeitpunkt is after the start time of the test
    Assertions.assertThat(autorisierung.getZeitpunkt()).isNotNull();
    Assertions.assertThat(autorisierung.getZeitpunkt().isAfter(startTimeOfTest)).isTrue();

    // verify that the Mock was called
    Mockito.verify(zahlungsRepository, Mockito.times(5)).findById(Mockito.any(BestellId.class));
    Mockito.verify(zahlungsRepository).update(Mockito.any(Zahlung.class));
    Mockito.verifyNoMoreInteractions(zahlungsRepository);
  }
}
