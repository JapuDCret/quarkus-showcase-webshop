package de.openknowledge.projects.webshop.infrastructure.zahlungsart;

import de.openknowledge.projects.webshop.domain.zahlung.Zahlung;
import de.openknowledge.projects.webshop.domain.zahlung.ZahlungsId;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.validation.constraints.NotNull;
import java.util.*;

@ApplicationScoped
public class ZahlungsRepository {
    private final Set<Zahlung> zahlungen = new HashSet<>();

    @PostConstruct
    public void init() { }

    public Set<Zahlung> read() {
        return Collections.unmodifiableSet(this.zahlungen);
    }

    public Optional<Zahlung> findById(@NotNull ZahlungsId zahlungsId) {
        return this.zahlungen
                .stream()
                .filter((zahlung) -> zahlung.getZahlungsId().equals(zahlungsId))
                .findAny();
    }

    public boolean create(Zahlung bestellung) {
        return this.zahlungen.add(bestellung);
    }

    public void update(Zahlung zahlung) {
        if(this.zahlungen.contains(zahlung)) {
            this.zahlungen.add(zahlung);
        }
    }
}
