package de.openknowledge.projects.webshop.infrastructure.bestellung;

import de.openknowledge.projects.webshop.domain.bestellung.BestellId;
import de.openknowledge.projects.webshop.domain.bestellung.Bestellung;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class BestellRepository {
    private final List<Bestellung> bestellungen = new ArrayList<>();

    @PostConstruct
    public void init() { }

    public List<Bestellung> read() {
        return Collections.unmodifiableList(this.bestellungen);
    }

    public Optional<Bestellung> findById(@NotNull BestellId bestellId) {
        return this.bestellungen
                .stream()
                .filter((bestellung) -> {
                    return bestellung.getBestellId().equals(bestellId);
                })
                .findAny();
    }

    public boolean create(Bestellung bestellung) {
        return this.bestellungen.add(bestellung);
    }
}
