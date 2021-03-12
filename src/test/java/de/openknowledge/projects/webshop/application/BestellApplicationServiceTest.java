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
import de.openknowledge.projects.webshop.infrastructure.zahlungsart.ZahlungsRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Test class for the resource {@link ProduktResource}.
 */
@ExtendWith(MockitoExtension.class)
class BestellApplicationServiceTest {

  @InjectMocks
  private BestellApplicationService bestellApplicationService;

  @Mock
  private ProduktRepository produktRepository;

  private BestellRepository bestellRepository;

  private List<ProduktAuswahl> produkte;
  private LieferAdresse lieferAdresse;
  private Bestellung bestellung;

  @BeforeEach
  public void setUp() {
    ZahlungsRepository zahlungsRepository = new ZahlungsRepository();
    this.bestellRepository = new BestellRepository();
    BestellDomainService bestellDomainService = new BestellDomainService(this.bestellRepository, zahlungsRepository);
    this.bestellApplicationService = new BestellApplicationService(this.produktRepository, bestellDomainService);

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
  public void placeBestellungShouldCreateBestellung() {
    for(ProduktAuswahl auswahl : this.produkte) {
      Mockito.doReturn(Optional.of(auswahl.getProdukt())).when(produktRepository).findByName(auswahl.getProdukt().getName());
    }

    // assert initial conditions
    Assertions.assertThat(bestellRepository.read().size())
            .describedAs("Es existieren keine Bestellungen")
            .isEqualTo(0);

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
    ZahlungsAufforderungDTO zahlungsAufforderung = this.bestellApplicationService.placeBestellung(bestellungDTO);

    Mockito.verifyNoMoreInteractions(produktRepository);

    // verify
    Assertions.assertThat(zahlungsAufforderung.getBetrag())
            .describedAs("Beträge sind gleich")
            .isEqualTo(bestellung.getProduktListe().getBetrag().doubleValue());

    Assertions.assertThat(bestellRepository.read().size())
            .describedAs("Es existiert eine Bestellungen")
            .isEqualTo(1);

    String bestellId = zahlungsAufforderung.getBestellId();
    Optional<Bestellung> optBestellung = bestellRepository.findById(bestellId);
    Assertions
            .assertThat(optBestellung.isPresent())
            .describedAs("Bestellung mit id=\"%s\" existiert", bestellId)
            .isTrue();

    Bestellung bestellung = optBestellung.get();

    Assertions.assertThat(bestellung.getProduktListe())
            .describedAs("Produktliste ist gleich")
            .isEqualTo(this.bestellung.getProduktListe());
    Assertions.assertThat(bestellung.getLieferAdresse())
            .describedAs("Lieferadresse ist gleich")
            .isEqualTo(this.bestellung.getLieferAdresse());
  }
}
