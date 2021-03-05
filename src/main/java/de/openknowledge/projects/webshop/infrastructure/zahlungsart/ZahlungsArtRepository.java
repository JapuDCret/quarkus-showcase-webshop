package de.openknowledge.projects.webshop.infrastructure.zahlungsart;

import de.openknowledge.projects.webshop.domain.zahlung.ZahlungsArt;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ApplicationScoped
public class ZahlungsArtRepository {
    private final List<ZahlungsArt> zahlungsArten = new ArrayList<>();

    @PostConstruct
    public void init() {
        ZahlungsArt zahlungsArtPaypal = new ZahlungsArt("PayPal");

        zahlungsArten.add(zahlungsArtPaypal);
    }

    public List<ZahlungsArt> read() {
        return Collections.unmodifiableList(this.zahlungsArten);
    }
}
