package de.openknowledge.projects.webshop.domain.bestellung;

import de.openknowledge.projects.webshop.infrastructure.bestellung.BestellRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;

@ApplicationScoped
public class BestellDomainService {

    private static final Logger LOG = LoggerFactory.getLogger(BestellDomainService.class);

    private BestellRepository bestellRepository;

    @Inject
    private Event<Bestellung> bestellungCreated;

    @Inject
    public BestellDomainService(BestellRepository bestellRepository) {
        this.bestellRepository = bestellRepository;
    }

    public void create(Bestellung bestellung) {
        LOG.debug("Create {}");

        bestellRepository.create(bestellung);

        LOG.debug("Bestellung was created, firing event");

        bestellungCreated.fire(bestellung);
    }
}
