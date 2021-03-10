package de.openknowledge.projects.webshop.application.bestellung;

import de.openknowledge.projects.webshop.domain.bestellung.*;
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
public class BestellService {

    private static final Logger LOG = LoggerFactory.getLogger(BestellService.class);

    @Inject
    private ProduktRepository produktRepository;

    @Inject
    private BestellDomainService bestellService;

    public @NotNull ZahlungsinfoDTO placeBestellung(@NotNull final BestellungDTO dto) {
        Bestellung bestellung = this.convertBestellung(dto);

        bestellService.create(bestellung);

        ZahlungsinfoDTO zahlungsinfoDTO = new ZahlungsinfoDTO(
                bestellung.getBestellId().getId(),
                "", // TODO
                bestellung.getProduktListe().getBetrag().doubleValue()
        );

        return zahlungsinfoDTO;
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
