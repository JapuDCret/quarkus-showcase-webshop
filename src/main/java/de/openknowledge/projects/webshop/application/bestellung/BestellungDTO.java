package de.openknowledge.projects.webshop.application.bestellung;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class BestellungDTO implements Serializable {
    private static final long serialVersionUID = 702881262028313455L;

    @NotNull
    private List<ProduktAuswahlDTO> produktListe;

    @NotNull
    private LieferAdresseDTO lieferAdresse;

    protected BestellungDTO() {
        super();
    }

    public List<ProduktAuswahlDTO> getProduktListe() {
        return produktListe;
    }

    public void setProduktListe(List<ProduktAuswahlDTO> produktListe) {
        this.produktListe = produktListe;
    }

    public LieferAdresseDTO getLieferAdresse() {
        return lieferAdresse;
    }

    public void setLieferAdresse(LieferAdresseDTO lieferAdresse) {
        this.lieferAdresse = lieferAdresse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BestellungDTO that = (BestellungDTO) o;
        return Objects.equals(produktListe, that.produktListe) && Objects.equals(lieferAdresse, that.lieferAdresse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(produktListe, lieferAdresse);
    }

    @Override
    public String toString() {
        return "BestellungDTO{" +
                "produktListe=" + produktListe +
                ", lieferAdresse=" + lieferAdresse +
                '}';
    }
}
