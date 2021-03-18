package de.openknowledge.projects.webshop.infrastructure.bestellung;

import de.openknowledge.projects.webshop.domain.bestellung.BestellungId;
import de.openknowledge.projects.webshop.domain.bestellung.Bestellung;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@ApplicationScoped
public class BestellungRepository {
    private final Set<Bestellung> bestellungen = new HashSet<>();

    @PostConstruct
    public void init() { }

    public Set<Bestellung> read() {
        return Collections.unmodifiableSet(this.bestellungen);
    }

    public Optional<Bestellung> findById(String bestellId) {
        return this.findById(new BestellungId(bestellId));
    }

    public Optional<Bestellung> findById(BestellungId bestellungId) {
        return this.bestellungen
                .stream()
                .filter((bestellung) -> bestellung.getBestellId().equals(bestellungId))
                .findAny();
    }

    public boolean create(Bestellung bestellung) {
        return this.bestellungen.add(bestellung);
    }
}
