package de.openknowledge.projects.webshop.domain.bestellung;

import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * Aggregate "Bestellung"
 */
public class Bestellung implements Serializable {
    private static final long serialVersionUID = 2473222120737283076L;

    @NotNull
    private final BestellId bestellId;

    @NotNull
    private final ProduktListe produktListe;

    @NotNull
    private final LieferAdresse lieferAdresse;

//    @NotNull
//    private final RechnungsAdresse rechnungsAdresse;

    public BestellId getBestellId() {
        return bestellId;
    }

    public ProduktListe getProduktListe() {
        return produktListe;
    }

    public LieferAdresse getLieferAdresse() {
        return lieferAdresse;
    }

//    public RechnungsAdresse getRechnungsAdresse() {
//        return rechnungsAdresse;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bestellung that = (Bestellung) o;
        return Objects.equals(bestellId, that.bestellId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bestellId);
    }

    @Override
    public String toString() {
        return "Bestellung{" +
                "produktListe=" + produktListe +
                ", lieferAdresse=" + lieferAdresse +
//                ", rechnungsAdresse=" + rechnungsAdresse +
                '}';
    }

    public static Builder Builder() {
        return new Builder();
    }

    public Bestellung(Builder b) {
        this.bestellId = b.bestellId;
        this.produktListe = b.produktListe;
        this.lieferAdresse = b.lieferAdresse;
//        this.rechnungsAdresse = b.rechnungsAdresse;
    }

    public static class Builder {
        private BestellId bestellId;
        private ProduktListe produktListe;
        private LieferAdresse lieferAdresse;
        private RechnungsAdresse rechnungsAdresse;

        private Builder() { }

        public Builder setBestellId(@NotNull BestellId bestellId) {
            this.bestellId = bestellId;

            return this;
        }

        public Builder setProduktListe(@NotNull ProduktListe produktListe) {
            this.produktListe = produktListe;

            return this;
        }

        public Builder setLieferadresse(@NotNull LieferAdresse lieferAdresse) {
            this.lieferAdresse = lieferAdresse;

            return this;
        }

//        public Builder setRechnungsadresse(@NotNull RechnungsAdresse rechnungsAdresse) {
//            this.rechnungsAdresse = rechnungsAdresse;
//
//            return this;
//        }

        /**
         * baut eine Instanz von Bestellung, wenn die Felder {@link #bestellId}, {@link #produktListe}, {@link #lieferAdresse} und {@link #rechnungsAdresse} befüllt sind
         *
         * @return Bestellung
         * @throws ValidationException wenn nicht alle notwendigen Felder gefüllt wurden
         */
        public Bestellung build() {
            if(this.bestellId == null) {
                throw new ValidationException("Bestellung.Builder: BestellId darf nicht 'null' sein!");
            }
            if(this.produktListe == null) {
                throw new ValidationException("Bestellung.Builder: ProduktListe darf nicht 'null' sein!");
            }
            if(this.lieferAdresse == null) {
                throw new ValidationException("Bestellung.Builder: Lieferadresse darf nicht 'null' sein!");
            }
//            if(this.rechnungsAdresse == null) {
//                throw new ValidationException("Bestellung.Builder: Rechnungsadresse darf nicht 'null' sein!");
//            }

            return new Bestellung(this);
        }
    }
}
