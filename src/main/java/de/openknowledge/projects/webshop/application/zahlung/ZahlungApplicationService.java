package de.openknowledge.projects.webshop.application.zahlung;

import de.openknowledge.projects.webshop.domain.bestellung.BestellId;
import de.openknowledge.projects.webshop.domain.bestellung.Bestellung;
import de.openknowledge.projects.webshop.domain.zahlung.Zahlung;
import de.openknowledge.projects.webshop.infrastructure.zahlungsart.ZahlungsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.NotFoundException;
import java.util.Optional;

/**
 * A service that handles mapping from {@BestellungDTO} to {@link Bestellung}.
 */
@ApplicationScoped
public class ZahlungApplicationService {

    private static final Logger LOG = LoggerFactory.getLogger(ZahlungApplicationService.class);

    public ZahlungsRepository repository;

    @Inject
    public ZahlungApplicationService(ZahlungsRepository repository) {
        this.repository = repository;
    }

    private Zahlung findByBestellId(@NotNull final String bestellId) {
        LOG.debug("Retrieving Zahlung with BestellId \"{}\" from repository", bestellId);

        Optional<Zahlung> optZahlung = repository.findById(new BestellId(bestellId));

        if(!optZahlung.isPresent()) {
            LOG.warn("Could not find Zahlung with BestellId \"{}\" gefunden", bestellId);

            throw new NotFoundException(String.format("Es konnte keine Zahlung zur BestellId \"{}\" gefunden werden.", bestellId));
        }

        return optZahlung.get();
    }

    public ZahlungsInfoDTO getZahlungInfo(@NotNull final String bestellId) {
        Zahlung zahlung = this.findByBestellId(bestellId);

        LOG.debug("Converting {} to DTO", zahlung);

        return ZahlungsInfoDTO.of(zahlung);
    }

    public ZahlungsInfoDTO authorize(@NotNull final String bestellId) {
        Zahlung zahlung = this.findByBestellId(bestellId);

        LOG.debug("Authorizing {}", zahlung);

        zahlung.autorisiere();

        LOG.debug("Writing {} to repository", zahlung);

        repository.update(zahlung);

        LOG.debug("Converting {} to DTO", zahlung);

        return ZahlungsInfoDTO.of(zahlung);
    }
}
