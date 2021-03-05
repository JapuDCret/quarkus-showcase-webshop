package de.openknowledge.projects.webshop.infrastructure.bestellung;

import de.openknowledge.projects.webshop.domain.bestellung.filiale.FilialAdresse;
import de.openknowledge.projects.webshop.domain.bestellung.filiale.FilialName;
import de.openknowledge.projects.webshop.domain.bestellung.filiale.Filiale;
import de.openknowledge.projects.webshop.domain.zahlung.ZahlungsArt;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ApplicationScoped
public class FilialRepository {
    private final List<Filiale> filialen = new ArrayList<>();

    @PostConstruct
    public void init() {
        FilialAdresse filialAdresseA = FilialAdresse.Builder()
                .setPostAnschriftName("Restaurant GmbH")
                .setStrasse("Poststraße 1")
                .setStadt(26122, "Oldenburg")
                .build();
        FilialName filialNameA = new FilialName("Filiale A");
        Filiale filialeA = Filiale.Builder()
                .setName(filialNameA)
                .setFilialAdresse(filialAdresseA)
                .build();

        filialen.add(filialeA);

        FilialAdresse filialAdresseB = FilialAdresse.Builder()
                .setPostAnschriftName("Restaurant GmbH")
                .setStrasse("II. Hagen 7")
                .setStadt(45127, "Essen")
                .build();
        FilialName filialNameB = new FilialName("Filiale B");
        Filiale filialeB = Filiale.Builder()
                .setName(filialNameB)
                .setFilialAdresse(filialAdresseB)
                .build();

        filialen.add(filialeB);
    }

    public List<Filiale> read() {
        return Collections.unmodifiableList(this.filialen);
    }
}
