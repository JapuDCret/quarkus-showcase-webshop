package de.openknowledge.projects.webshop.domain.bestellung;

import de.openknowledge.projects.webshop.infrastructure.bestellung.BestellungRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;

@ApplicationScoped
public class BestellungDomainService {

    private static final Logger LOG = LoggerFactory.getLogger(BestellungDomainService.class);

    private BestellungRepository bestellungRepository;

    private Event<Bestellung> bestellungCreated;

    @Inject
    public BestellungDomainService(BestellungRepository bestellungRepository, Event<Bestellung> bestellungCreated) {
        this.bestellungRepository = bestellungRepository;
        this.bestellungCreated = bestellungCreated;
    }

    public void create(Bestellung bestellung) {
        LOG.debug("Create {}");

        bestellungRepository.create(bestellung);

        LOG.debug("Bestellung was created, firing event");

        bestellungCreated.fire(bestellung);
    }
}
