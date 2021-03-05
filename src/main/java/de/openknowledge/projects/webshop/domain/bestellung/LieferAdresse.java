package de.openknowledge.projects.webshop.domain.bestellung;

import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * Aggregate "Lieferadresse"
 */
public class LieferAdresse implements Serializable {
    private static final long serialVersionUID = -8221213050698157946L;

    @NotNull
    private final KundenName kundenName;

    @NotNull
    private final Strasse strasse;

    @NotNull
    private final Stadt stadt;

    private final LieferKommentar kommentar;

    public KundenName getKundenName() {
        return kundenName;
    }

    public Strasse getStrasse() {
        return strasse;
    }

    public Stadt getStadt() {
        return stadt;
    }

    public LieferKommentar getKommentar() {
        return kommentar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LieferAdresse that = (LieferAdresse) o;
        return Objects.equals(kundenName, that.kundenName) && Objects.equals(strasse, that.strasse) && Objects.equals(stadt, that.stadt) && Objects.equals(kommentar, that.kommentar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(kundenName, strasse, stadt, kommentar);
    }

    @Override
    public String toString() {
        return "Lieferadresse{" +
                "kundenName=" + kundenName +
                ", strasse=" + strasse +
                ", stadt=" + stadt +
                ", kommentar=" + kommentar +
                '}';
    }

    public static Builder Builder() {
        return new Builder();
    }

    public LieferAdresse(Builder b) {
        this.kundenName = b.kundenName;
        this.strasse = b.strasse;
        this.stadt = b.stadt;
        this.kommentar = b.kommentar;
    }

    public static class Builder {
        private KundenName kundenName;
        private Strasse strasse;
        private Stadt stadt;
        private LieferKommentar kommentar;

        private Builder() { }

        public Builder setKundenName(@NotNull String vorname, @NotNull String nachname) {
            this.kundenName = new KundenName(vorname, nachname);

            return this;
        }

        public Builder setStrasse(@NotNull String strasse) {
            this.strasse = new Strasse(strasse);

            return this;
        }

        public Builder setStadt(int plz, @NotNull String stadtName) {
            this.stadt = new Stadt(plz, stadtName);

            return this;
        }

        public Builder setKommentar(@NotNull String kommentar) {
            this.kommentar = new LieferKommentar(kommentar);

            return this;
        }

        /**
         * baut eine Instanz von Lieferadresse, wenn die Felder {@link #kundenName}, {@link #strasse} und {@link #stadt} befüllt sind
         *
         * @return Lieferadresse
         * @throws ValidationException wenn nicht alle notwendigen Felder gefüllt wurden
         */
        public LieferAdresse build() {
            if(this.kundenName == null) {
                throw new ValidationException("Lieferadresse.Builder: KundenName darf nicht 'null' sein!");
            }
            if(this.strasse == null) {
                throw new ValidationException("Lieferadresse.Builder: Straße darf nicht 'null' sein!");
            }
            if(this.stadt == null) {
                throw new ValidationException("Lieferadresse.Builder: Stadt darf nicht 'null' sein!");
            }
            // this.kommentar darf null sein

            return new LieferAdresse(this);
        }
    }
}
