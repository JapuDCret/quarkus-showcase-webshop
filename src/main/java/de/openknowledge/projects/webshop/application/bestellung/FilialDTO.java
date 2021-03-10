package de.openknowledge.projects.webshop.application.bestellung;

import de.openknowledge.projects.webshop.domain.bestellung.filiale.FilialAdresse;
import de.openknowledge.projects.webshop.domain.bestellung.filiale.Filiale;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

public class FilialDTO implements Serializable {
    private static final long serialVersionUID = 3603877728489086712L;

    private final String name;

    private final String strasse;

    private final String plz;

    private final String stadt;

    public FilialDTO(@NotNull String name, @NotNull String strasse, @NotNull String plz, @NotNull String stadt) {
        this.name = name;
        this.strasse = strasse;
        this.plz = plz;
        this.stadt = stadt;
    }

    public String getName() {
        return name;
    }

    public String getStrasse() {
        return strasse;
    }

    public String getPlz() {
        return plz;
    }

    public String getStadt() {
        return stadt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilialDTO filialDTO = (FilialDTO) o;
        return Objects.equals(name, filialDTO.name) && Objects.equals(strasse, filialDTO.strasse) && Objects.equals(plz, filialDTO.plz) && Objects.equals(stadt, filialDTO.stadt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, strasse, plz, stadt);
    }

    @Override
    public String toString() {
        return "FilialDTO{" +
                "name='" + name + '\'' +
                ", strasse='" + strasse + '\'' +
                ", plz='" + plz + '\'' +
                ", stadt='" + stadt + '\'' +
                '}';
    }

    public static FilialDTO of(Filiale filiale) {
        String name = filiale.getName().getName();

        FilialAdresse adresse = filiale.getAdresse();

        String strasse = adresse.getStrasse().getStrasse();
        String stadt = adresse.getStadtName().getName();
        String plz = adresse.getPlz().getPlz();

        return new FilialDTO(name, strasse, plz, stadt);
    }
}
