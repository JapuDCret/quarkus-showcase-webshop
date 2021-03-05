package de.openknowledge.projects.webshop.infrastructure.bestellung;

import de.openknowledge.projects.webshop.domain.bestellung.Produkt;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ApplicationScoped
public class ProduktRepository {
    private final List<Produkt> produkte = new ArrayList<>();

    @PostConstruct
    public void init() {
        Produkt produktSalat = new Produkt("Salat", BigDecimal.valueOf(8.5));
        produkte.add(produktSalat);

        Produkt produktBowl = new Produkt("Bowl", BigDecimal.valueOf(11.0));
        produkte.add(produktBowl);

        Produkt produktCiabatta = new Produkt("Ciabatta", BigDecimal.valueOf(1.5));
        produkte.add(produktCiabatta);

        Produkt produktGetraenk = new Produkt("Getränk", BigDecimal.valueOf(2.0));
        produkte.add(produktGetraenk);

        Produkt produktDip = new Produkt("Dip", BigDecimal.valueOf(1.0));
        produkte.add(produktDip);

        Produkt produktDressing = new Produkt("Dressing", BigDecimal.valueOf(0.5));
        produkte.add(produktDressing);
    }

    public List<Produkt> read() {
        return Collections.unmodifiableList(this.produkte);
    }
}
