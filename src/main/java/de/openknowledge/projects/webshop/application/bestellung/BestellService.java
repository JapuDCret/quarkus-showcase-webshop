package de.openknowledge.projects.webshop.application.bestellung;

import de.openknowledge.projects.webshop.domain.bestellung.*;
import de.openknowledge.projects.webshop.infrastructure.bestellung.BestellRepository;
import de.openknowledge.projects.webshop.infrastructure.bestellung.ProduktRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ValidationException;
import java.math.BigInteger;
import java.util.Optional;
import java.util.Random;

/**
 * A service that handles mapping from {@BestellungDTO} to {@link Bestellung}.
 */
@ApplicationScoped
public class BestellService {

    private static final Logger LOG = LoggerFactory.getLogger(BestellService.class);

    @Inject
    private ProduktRepository produktRepository;

    @Inject
    private BestellRepository bestellRepository;

    final Random rnd = new Random();

    public void placeBestellung(final BestellungDTO dto) {
        Bestellung bestellung = this.convertBestellung(dto);

        bestellRepository.create(bestellung);
    }

    private Bestellung convertBestellung(final BestellungDTO dto) {
        long bestellId = rnd.nextLong();

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
                .setBestellId(new BestellId(BigInteger.valueOf(bestellId)))
                .setLieferadresse(lieferAdresse)
                .setProduktListe(produktListe)
                .build();

        return bestellung;
    }

    private ProduktAuswahl convertProdukt(final ProduktAuswahlDTO produktAuswahlDTO) {
        String produktName = produktAuswahlDTO.getName();
        Optional<Produkt> optProdukt = produktRepository.findByName(produktName);

        if(!optProdukt.isPresent()) {
            final String errMsg = String.format("Produkt with name %s does not exist!", produktName);
            throw new ValidationException(errMsg);
        }

        final Produkt produkt = optProdukt.get();

        ProduktAuswahl produktAuswahl = new ProduktAuswahl(produkt, produktAuswahlDTO.getAnzahl());

        return produktAuswahl;
    }
}
