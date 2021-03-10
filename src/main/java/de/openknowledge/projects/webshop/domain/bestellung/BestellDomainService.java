package de.openknowledge.projects.webshop.domain.bestellung;

import de.openknowledge.projects.webshop.infrastructure.bestellung.BestellRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class BestellDomainService {
    @Inject
    private BestellRepository repository;

    public boolean create(Bestellung bestellung) {
        return repository.create(bestellung);
    }
}
