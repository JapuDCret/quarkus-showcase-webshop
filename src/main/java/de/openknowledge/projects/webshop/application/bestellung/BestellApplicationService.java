package de.openknowledge.projects.webshop.application.bestellung;

import de.openknowledge.projects.webshop.domain.bestellung.*;
import de.openknowledge.projects.webshop.domain.zahlung.Zahlung;
import de.openknowledge.projects.webshop.infrastructure.bestellung.ProduktRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * A service that handles mapping from {@BestellungDTO} to {@link Bestellung}.
 */
@ApplicationScoped
public class BestellApplicationService {

    private static final Logger LOG = LoggerFactory.getLogger(BestellApplicationService.class);

    private ProduktRepository produktRepository;

    private BestellDomainService bestellService;

    @Inject
    public BestellApplicationService(ProduktRepository produktRepository, BestellDomainService bestellService) {
        this.produktRepository = produktRepository;
        this.bestellService = bestellService;
    }

    public @NotNull ZahlungsAufforderungDTO placeBestellung(@NotNull final BestellungDTO dto) {
        Bestellung bestellung = this.convertBestellung(dto);

        LOG.info("Persistiere {}", bestellung);

        Zahlung zahlung = bestellService.create(bestellung);

        LOG.info("Bestellung persistiert, {} erzeugt", zahlung);

        ZahlungsAufforderungDTO zahlungsAufforderung = new ZahlungsAufforderungDTO(
                bestellung.getBestellId().getId(),
                zahlung.getZahlungsId().getId(),
                bestellung.getProduktListe().getBetrag().doubleValue()
        );

        LOG.info("Gebe {} zurück", zahlungsAufforderung);

        return zahlungsAufforderung;
    }

    private @NotNull Bestellung convertBestellung(@NotNull final BestellungDTO dto) {
        LieferAdresseDTO lieferAdresseDTO = dto.getLieferAdresse();

        LieferAdresse lieferAdresse = LieferAdresse.Builder()
                .setKundenName(lieferAdresseDTO.getVorName(), lieferAdresseDTO.getNachName())
                .setStrasse(lieferAdresseDTO.getStrasse())
                .setPlz(lieferAdresseDTO.getPlz())
                .setStadtName(lieferAdresseDTO.getOrt())
                .build();

        ProduktListe.Builder produktListeBuilder = ProduktListe.Builder();
        dto.getProduktListe().forEach((produktDTO) -> {
            produktListeBuilder.addProdukt(convertProdukt(produktDTO));
        });
        ProduktListe produktListe = produktListeBuilder.build();

        Bestellung bestellung = Bestellung.Builder()
                .setLieferadresse(lieferAdresse)
                .setProduktListe(produktListe)
                .build();

        return bestellung;
    }

    private @NotNull ProduktAuswahl convertProdukt(@NotNull final ProduktAuswahlDTO produktAuswahlDTO) {
        String produktName = produktAuswahlDTO.getName();
        Optional<Produkt> optProdukt = produktRepository.findByName(produktName);

        if(!optProdukt.isPresent()) {
            final String errMsg = String.format("Das Produkt mit dem Namen \"%s\" existiert nicht!", produktName);
            throw new ValidationException(errMsg);
        }

        final Produkt produkt = optProdukt.get();

        ProduktAuswahl produktAuswahl = new ProduktAuswahl(produkt, produktAuswahlDTO.getAnzahl());

        return produktAuswahl;
    }
}
