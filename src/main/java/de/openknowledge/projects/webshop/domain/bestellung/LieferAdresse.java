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
    private final Postleitzahl plz;

    @NotNull
    private final StadtName stadtName;

    private final LieferKommentar kommentar;

    public KundenName getKundenName() {
        return kundenName;
    }

    public Strasse getStrasse() {
        return strasse;
    }

    public Postleitzahl getPlz() {
        return plz;
    }

    public StadtName getStadtName() {
        return stadtName;
    }

    public LieferKommentar getKommentar() {
        return kommentar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LieferAdresse that = (LieferAdresse) o;
        return Objects.equals(kundenName, that.kundenName) && Objects.equals(strasse, that.strasse) && Objects.equals(plz, that.plz) && Objects.equals(stadtName, that.stadtName) && Objects.equals(kommentar, that.kommentar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(kundenName, strasse, plz, stadtName, kommentar);
    }

    @Override
    public String toString() {
        return "Lieferadresse{" +
                "kundenName=" + kundenName +
                ", strasse=" + strasse +
                ", plz=" + plz +
                ", stadtName=" + stadtName +
                ", kommentar=" + kommentar +
                '}';
    }

    public static Builder Builder() {
        return new Builder();
    }

    public LieferAdresse(Builder b) {
        this.kundenName = b.kundenName;
        this.strasse = b.strasse;
        this.plz = b.plz;
        this.stadtName = b.stadtName;
        this.kommentar = b.kommentar;
    }

    public static class Builder {
        private KundenName kundenName;
        private Strasse strasse;
        private Postleitzahl plz;
        private StadtName stadtName;
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

        public Builder setPlz(@NotNull String plz) {
            this.plz = new Postleitzahl(plz);

            return this;
        }

        public Builder setStadtName( @NotNull String stadtName) {
            this.stadtName = new StadtName(stadtName);

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
            if(this.plz == null) {
                throw new ValidationException("Lieferadresse.Builder: PLZ darf nicht 'null' sein!");
            }
            if(this.stadtName == null) {
                throw new ValidationException("Lieferadresse.Builder: StadtName darf nicht 'null' sein!");
            }
            // this.kommentar darf null sein

            return new LieferAdresse(this);
        }
    }
}
