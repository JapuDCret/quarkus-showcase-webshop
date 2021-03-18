package de.openknowledge.projects.webshop.domain.zahlung;

import de.openknowledge.projects.webshop.domain.bestellung.Bestellung;
import de.openknowledge.projects.webshop.infrastructure.zahlungsart.ZahlungsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class ZahlungDomainService {

    private static final Logger LOG = LoggerFactory.getLogger(ZahlungDomainService.class);

    @Inject
    private ZahlungsRepository zahlungsRepository;

    public void observeBestellungCreated(@Observes Bestellung bestellung) {
        LOG.debug("Creating Zahlung from {}", bestellung);

        Zahlung zahlung = Zahlung.Builder()
                .setBestellung(bestellung)
                .build();

        LOG.debug("Persisting {}", zahlung);

        zahlungsRepository.create(zahlung);
    }
}
