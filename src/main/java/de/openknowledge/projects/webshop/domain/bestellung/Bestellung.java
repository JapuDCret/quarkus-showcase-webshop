package de.openknowledge.projects.webshop.domain.bestellung;

import de.openknowledge.projects.webshop.domain.bestellung.produkt.ProduktListe;

import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;
import java.util.Random;

/**
 * Aggregate "Bestellung"
 */
public class Bestellung implements Serializable {
    private static final long serialVersionUID = 2473222120737283076L;

    @NotNull
    private final BestellungId bestellungId;

    @NotNull
    private final ProduktListe produktListe;

    @NotNull
    private final LieferAdresse lieferAdresse;

//    @NotNull
//    private final RechnungsAdresse rechnungsAdresse;

    public BestellungId getBestellId() {
        return bestellungId;
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
        return Objects.equals(bestellungId, that.bestellungId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bestellungId);
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
        this.bestellungId = b.bestellungId;
        this.produktListe = b.produktListe;
        this.lieferAdresse = b.lieferAdresse;
//        this.rechnungsAdresse = b.rechnungsAdresse;
    }

    public static class Builder {
        private BestellungId bestellungId;
        private ProduktListe produktListe;
        private LieferAdresse lieferAdresse;
        private RechnungsAdresse rechnungsAdresse;

        private Builder() { }

        private BestellungId generateBestellId() {
            // see https://stackoverflow.com/questions/14622622/generating-a-random-hex-string-of-length-50-in-java-me-j2me/14623245
            final int totalCharCounter = 32;
            final String baseString = "be771e771d";

            Random r = new Random();
            StringBuffer sb = new StringBuffer(baseString);
            while(sb.length() < (totalCharCounter)){
                sb.append(Integer.toHexString(r.nextInt()));
            }

            return new BestellungId(sb.toString().substring(0, totalCharCounter));
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
         * baut eine Instanz von Bestellung, wenn die Felder {@link #bestellungId}, {@link #produktListe}, {@link #lieferAdresse} und {@link #rechnungsAdresse} befüllt sind
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
//            if(this.rechnungsAdresse == null) {
//                throw new ValidationException("Bestellung.Builder: Rechnungsadresse darf nicht 'null' sein!");
//            }
            this.bestellungId = generateBestellId();

            return new Bestellung(this);
        }
    }
}
