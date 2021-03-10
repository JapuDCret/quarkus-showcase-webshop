package de.openknowledge.projects.webshop.infrastructure.bestellung;

import de.openknowledge.projects.webshop.domain.bestellung.BestellId;
import de.openknowledge.projects.webshop.domain.bestellung.Bestellung;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.validation.constraints.NotNull;
import java.util.*;

@ApplicationScoped
public class BestellRepository {
    private final Set<Bestellung> bestellungen = new HashSet<>();

    @PostConstruct
    public void init() { }

    public Set<Bestellung> read() {
        return Collections.unmodifiableSet(this.bestellungen);
    }

    public Optional<Bestellung> findById(@NotNull BestellId bestellId) {
        return this.bestellungen
                .stream()
                .filter((bestellung) -> bestellung.getBestellId().equals(bestellId))
                .findAny();
    }

    public boolean create(Bestellung bestellung) {
        return this.bestellungen.add(bestellung);
    }
}
