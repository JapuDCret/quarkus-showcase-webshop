package de.openknowledge.projects.webshop.domain;

import de.openknowledge.projects.webshop.domain.bestellung.Produkt;
import de.openknowledge.projects.webshop.domain.bestellung.ProduktAuswahl;
import de.openknowledge.projects.webshop.domain.bestellung.ProduktListe;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class ProduktListeUnitTest {
    @BeforeEach
    public void setUp() {
        // no-op
    }

    @Test
    public void testProduktListe() {
        ProduktListe.Builder produktListenBuilder = ProduktListe.Builder();

        produktListenBuilder.addProdukt(
                new ProduktAuswahl(
                        new Produkt("ProduktA", BigDecimal.valueOf(3d)),
                        2
                )
        );
        produktListenBuilder.addProdukt(
                new ProduktAuswahl(
                        new Produkt("ProduktB", BigDecimal.valueOf(1.2d)),
                        5
                )
        );
        produktListenBuilder.addProdukt(
                new ProduktAuswahl(
                        new Produkt("ProduktC", BigDecimal.valueOf(10.5d)),
                        1
                )
        );

        ProduktListe produktListe = produktListenBuilder.build();

        // 3*2 + 1.2*5 + 10.5 = 6 + 6 + 10,5 = 22,5
        assertThat(produktListe.getBetrag()).isEqualTo(BigDecimal.valueOf(22.5d));
    }
}
