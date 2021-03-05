package de.openknowledge.projects.webshop.domain.bestellung;

import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Aggregate "Bestellung"
 */
public class Bestellung implements Serializable {
    private static final long serialVersionUID = 9058464846356984703L;

    @NotNull
    private final ProduktListe produktListe;

    @NotNull
    private final LieferAdresse lieferAdresse;

    @NotNull
    private final RechnungsAdresse rechnungsAdresse;

    public ProduktListe getProduktListe() {
        return produktListe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bestellung that = (Bestellung) o;
        return Objects.equals(produktListe, that.produktListe) && Objects.equals(lieferAdresse, that.lieferAdresse) && Objects.equals(rechnungsAdresse, that.rechnungsAdresse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(produktListe, lieferAdresse, rechnungsAdresse);
    }

    @Override
    public String toString() {
        return "Bestellung{" +
                "produktListe=" + produktListe +
                ", lieferAdresse=" + lieferAdresse +
                ", rechnungsAdresse=" + rechnungsAdresse +
                '}';
    }

    public static Builder Builder() {
        return new Builder();
    }

    public Bestellung(Builder b) {
        this.produktListe = b.produktListe;
        this.lieferAdresse = b.lieferAdresse;
        this.rechnungsAdresse = b.rechnungsAdresse;
    }

    public static class Builder {
        private ProduktListe produktListe;
        private LieferAdresse lieferAdresse;
        private RechnungsAdresse rechnungsAdresse;

        private Builder() { }

        public Builder setProduktListe(@NotNull ProduktListe produktListe) {
            this.produktListe = produktListe;

            return this;
        }

        public Builder setLieferadresse(@NotNull LieferAdresse lieferAdresse) {
            this.lieferAdresse = lieferAdresse;

            return this;
        }

        public Builder setRechnungsadresse(@NotNull RechnungsAdresse rechnungsAdresse) {
            this.rechnungsAdresse = rechnungsAdresse;

            return this;
        }

        /**
         * baut eine Instanz von Bestellung, wenn die Felder {@link #produktListe}, {@link #lieferAdresse} und {@link #rechnungsAdresse} befüllt sind
         *
         * @return Bestellung
         * @throws ValidationException wenn nicht alle notwendigen Felder gefüllt wurden
         */
        public Bestellung build() {
            if(this.produktListe == null) {
                throw new ValidationException("Bestellung.Builder: ProduktListe darf nicht 'null' sein!");
            }
            if(this.lieferAdresse == null) {
                throw new ValidationException("Bestellung.Builder: Lieferadresse darf nicht 'null' sein!");
            }
            if(this.rechnungsAdresse == null) {
                throw new ValidationException("Bestellung.Builder: Rechnungsadresse darf nicht 'null' sein!");
            }

            return new Bestellung(this);
        }
    }
}
