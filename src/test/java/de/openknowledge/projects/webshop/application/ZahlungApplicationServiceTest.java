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
import de.openknowledge.projects.webshop.application.zahlung.ZahlungInfoDTO;
import de.openknowledge.projects.webshop.domain.bestellung.*;
import de.openknowledge.projects.webshop.domain.bestellung.produkt.Produkt;
import de.openknowledge.projects.webshop.domain.bestellung.produkt.ProduktAuswahl;
import de.openknowledge.projects.webshop.domain.bestellung.produkt.ProduktListe;
import de.openknowledge.projects.webshop.domain.zahlung.Zahlung;
import de.openknowledge.projects.webshop.domain.zahlung.ZahlungAutorisierung;
import de.openknowledge.projects.webshop.infrastructure.zahlung.ZahlungRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.NotFoundException;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Optional;

/**
 * Test class for the resource {@link ProduktResource}.
 */
@ExtendWith(MockitoExtension.class)
class ZahlungApplicationServiceTest {

  @InjectMocks
  private ZahlungApplicationService zahlungApplicationService;

  @Mock
  private ZahlungRepository zahlungRepository;

  private String bestellungId;
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

    this.bestellungId = bestellung.getBestellId().getId();

    Zahlung zahlung = Zahlung.Builder()
            .setBestellung(bestellung)
            .build();

    this.baseZahlung = zahlung;
  }

  @Test
  public void authorizeWithMissingZahlungShouldThrowNotFoundException() {
    Mockito.doReturn(Optional.empty()).when(zahlungRepository).findById(Mockito.any(BestellungId.class));

    // do "authorize" call
    Assertions.catchThrowableOfType(() -> {
      ZahlungInfoDTO zahlungInfo = zahlungApplicationService.authorize(this.bestellungId);

      Assertions.assertThat(zahlungInfo.isAutorisiert()).isTrue();
    }, NotFoundException.class);

    Mockito.verifyNoMoreInteractions(zahlungRepository);
    Mockito.verify(zahlungRepository).findById(Mockito.any(BestellungId.class));
    Mockito.verifyNoMoreInteractions(zahlungRepository);
  }

  @Test
  public void authorizeShouldSetAutorisierteAndZeitpunkt() {
    Optional<Zahlung> expectedZahlung = Optional.of(this.baseZahlung);

    Mockito.doReturn(expectedZahlung).when(zahlungRepository).findById(Mockito.any(BestellungId.class));
    Mockito.doNothing().when(zahlungRepository).update(Mockito.any(Zahlung.class));

    ZonedDateTime startTimeOfTest = ZonedDateTime.now();

    // assume the Zahlung exists
    {
      Optional<Zahlung> zahlung = zahlungRepository.findById(new BestellungId(this.bestellungId));

      Assume.assumeTrue(zahlung.isPresent());
    }

    // verify that Zahlung.autorisiert = false
    {
      ZahlungInfoDTO zahlungInfo = zahlungApplicationService.getZahlungInfo(this.bestellungId);

      Assertions.assertThat(zahlungInfo.isAutorisiert()).isFalse();
    }

    // do "authorize" call
    {
      ZahlungInfoDTO zahlungInfo = zahlungApplicationService.authorize(this.bestellungId);

      Assertions.assertThat(zahlungInfo.isAutorisiert()).isTrue();
    }

    // verify that Zahlung.autorisiert = true
    {
      ZahlungInfoDTO zahlungInfo = zahlungApplicationService.getZahlungInfo(this.bestellungId);

      Assertions.assertThat(zahlungInfo.isAutorisiert()).isTrue();
    }

    // verify that the Zahlung in the repository is now also authorized
    Optional<Zahlung> optZahlung = zahlungRepository.findById(new BestellungId(this.bestellungId));

    Assertions.assertThat(optZahlung.isPresent()).isTrue();
    Optional<ZahlungAutorisierung> optAutorisierung = optZahlung.get().getAutorisierung();

    Assertions.assertThat(optAutorisierung.isPresent()).isTrue();
    ZahlungAutorisierung autorisierung = optAutorisierung.get();

    // check if ZahlungAutorisierung.zeitpunkt is after the start time of the test
    Assertions.assertThat(autorisierung.getZeitpunkt()).isNotNull();
    Assertions.assertThat(autorisierung.getZeitpunkt().isAfter(startTimeOfTest)).isTrue();

    // verify that the Mock was called
    Mockito.verify(zahlungRepository, Mockito.times(5)).findById(Mockito.any(BestellungId.class));
    Mockito.verify(zahlungRepository).update(Mockito.any(Zahlung.class));
    Mockito.verifyNoMoreInteractions(zahlungRepository);
  }
}
