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
import de.openknowledge.projects.webshop.domain.bestellung.*;
import de.openknowledge.projects.webshop.infrastructure.bestellung.BestellRepository;
import de.openknowledge.projects.webshop.infrastructure.bestellung.ProduktRepository;
import org.assertj.core.api.Assertions;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
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
class BestellApplicationServiceTest {

  @InjectMocks
  private BestellApplicationService service;

  @Mock
  private ProduktRepository produktRepository;

  @Inject
  private BestellRepository bestellRepository;

  private List<ProduktAuswahl> produkte;
  private LieferAdresse lieferAdresse;
  private Bestellung bestellung;

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
    this.produkte = produktListe.getProdukte();

    LieferAdresse lieferAdresse = LieferAdresse.Builder()
            .setKundenName("Marvin", "Kienitz")
            .setStrasse("Platanenallee 1337")
            .setPlz("12345")
            .setStadtName("Berlin")
            .build();
    this.lieferAdresse = lieferAdresse;

    this.bestellung = Bestellung.Builder()
            .setProduktListe(produktListe)
            .setLieferadresse(lieferAdresse)
            .build();
  }

  @Test
  public void getProdukteShouldReturnProduktliste() {
    // assert initial conditions
    Assertions.assertThat(bestellRepository.read().size())
            .isEqualTo(0)
            .describedAs("Es existieren keine Bestellungen");

    // create DTO
    List<ProduktAuswahlDTO> produktDTOListe = new ArrayList<>() {{
      for(ProduktAuswahl auswahl : BestellApplicationServiceTest.this.produkte) {
        add(new ProduktAuswahlDTO(auswahl.getProdukt().getName(), auswahl.getAnzahl()));
      }
    }};
    LieferAdresseDTO lieferAdresseDTO = new LieferAdresseDTO(
            lieferAdresse.getKundenName().getVorname(),
            lieferAdresse.getKundenName().getNachname(),
            lieferAdresse.getStrasse().getStrasse(),
            lieferAdresse.getPlz().getPlz(),
            lieferAdresse.getStadtName().getName()
    );
    BestellungDTO bestellungDTO = new BestellungDTO(produktDTOListe, lieferAdresseDTO);

    // do call
    ZahlungsAufforderungDTO zahlungsAufforderung = this.service.placeBestellung(bestellungDTO);

    // verify
    Assertions.assertThat(zahlungsAufforderung.getBetrag())
            .isEqualTo(bestellung.getProduktListe().getBetrag().doubleValue())
            .describedAs("Beträge sind gleich");

    Assertions.assertThat(bestellRepository.read().size())
            .isEqualTo(1)
            .describedAs("Es existiert eine Bestellungen");

    String bestellId = zahlungsAufforderung.getBestellId();
    Optional<Bestellung> optBestellung = bestellRepository.findById(bestellId);
    Assertions.assertThat(optBestellung.isPresent())
            .isTrue()
            .describedAs("Bestellung mit id=\"%s\" existiert", bestellId);

    Bestellung bestellung = optBestellung.get();

    Assertions.assertThat(bestellung.getProduktListe())
            .isEqualTo(this.bestellung.getProduktListe())
            .describedAs("Produktliste ist gleich");
    Assertions.assertThat(bestellung.getLieferAdresse())
            .isEqualTo(this.bestellung.getLieferAdresse())
            .describedAs("Lieferadresse ist gleich");
  }
}
