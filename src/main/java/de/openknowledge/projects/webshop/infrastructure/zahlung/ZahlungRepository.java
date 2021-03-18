package de.openknowledge.projects.webshop.infrastructure.zahlung;

import de.openknowledge.projects.webshop.domain.bestellung.BestellungId;
import de.openknowledge.projects.webshop.domain.zahlung.Zahlung;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.validation.constraints.NotNull;
import java.util.*;

@ApplicationScoped
public class ZahlungRepository {
    private final Set<Zahlung> zahlungen = new HashSet<>();

    @PostConstruct
    public void init() { }

    public Set<Zahlung> read() {
        return Collections.unmodifiableSet(this.zahlungen);
    }

    public Optional<Zahlung> findById(@NotNull BestellungId bestellungId) {
        return this.zahlungen
                .stream()
                .filter((zahlung) -> zahlung.getBestellung().getBestellId().equals(bestellungId))
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
