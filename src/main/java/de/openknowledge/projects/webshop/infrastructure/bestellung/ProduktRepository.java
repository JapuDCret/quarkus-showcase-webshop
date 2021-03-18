package de.openknowledge.projects.webshop.infrastructure.bestellung;

import de.openknowledge.projects.webshop.domain.bestellung.produkt.Produkt;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ProduktRepository implements PanacheRepository<Produkt> {
    public List<Produkt> read() {
        return this.listAll();
    }

    public Optional<Produkt> findByName(@NotNull String name) {
        return this.find("name", name).firstResultOptional();
    }
}
