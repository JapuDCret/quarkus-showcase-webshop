package de.openknowledge.projects.webshop.domain;

import de.openknowledge.projects.webshop.domain.bestellung.*;
import de.openknowledge.projects.webshop.domain.bestellung.produkt.Produkt;
import de.openknowledge.projects.webshop.domain.bestellung.produkt.ProduktAuswahl;
import de.openknowledge.projects.webshop.domain.bestellung.produkt.ProduktListe;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class BestellungUnitTest {
    private ProduktListe testProduktListe;

    @BeforeEach
    public void setUp() { }

    @Test
    public void testBestellungBetrag() {
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

        this.testProduktListe = produktListenBuilder.build();
        LieferAdresse lieferAdresse = LieferAdresse.Builder()
                .setKundenName("Marvin", "Kienitz")
                .setStrasse("Platanenallee 1337")
                .setPlz("12345")
                .setStadtName("Berlin")
                .build();

        Bestellung bestellung = Bestellung.Builder()
                .setProduktListe(this.testProduktListe)
                .setLieferadresse(lieferAdresse)
                .build();

        assertThat(bestellung.getProduktListe().getBetrag()).isEqualTo(BigDecimal.valueOf(22.5d));
    }
}
