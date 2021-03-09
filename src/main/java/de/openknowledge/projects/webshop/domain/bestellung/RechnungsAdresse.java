package de.openknowledge.projects.webshop.domain.bestellung;

import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * ValueObject "Rechnungsadresse"
 */
public class RechnungsAdresse implements Serializable {
    private static final long serialVersionUID = 4760682272270631611L;

    @NotNull
    private final KundenName kundenName;

    private FirmenZusatz firmenZusatz;

    @NotNull
    private final Strasse strasse;

    @NotNull
    private final Postleitzahl plz;

    @NotNull
    private final StadtName stadtName;

    public KundenName getKundenName() {
        return kundenName;
    }

    public FirmenZusatz getFirmenZusatz() {
        return firmenZusatz;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RechnungsAdresse that = (RechnungsAdresse) o;
        return Objects.equals(kundenName, that.kundenName) && Objects.equals(firmenZusatz, that.firmenZusatz) && Objects.equals(strasse, that.strasse) && Objects.equals(plz, that.plz) && Objects.equals(stadtName, that.stadtName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(kundenName, firmenZusatz, strasse, plz, stadtName);
    }

    @Override
    public String toString() {
        return "RechnungsAdresse{" +
                "kundenName=" + kundenName +
                ", firmenZusatz=" + firmenZusatz +
                ", strasse=" + strasse +
                ", plz=" + plz +
                ", stadtName=" + stadtName +
                '}';
    }

    public static Builder Builder() {
        return new Builder();
    }

    private RechnungsAdresse(Builder b) {
        this.kundenName = b.kundenName;
        this.firmenZusatz = b.firmenZusatz;
        this.strasse = b.strasse;
        this.plz = b.plz;
        this.stadtName = b.stadtName;
    }

    public static class Builder {
        private KundenName kundenName;
        private FirmenZusatz firmenZusatz;
        private Strasse strasse;
        private Postleitzahl plz;
        private StadtName stadtName;

        private Builder() { }

        public Builder setKundenName(@NotNull String vorname, @NotNull String nachname) {
            this.kundenName = new KundenName(vorname, nachname);

            return this;
        }

        public Builder setFirmenZusatz(@NotNull String firmenZusatz) {
            this.firmenZusatz = new FirmenZusatz(firmenZusatz);

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

        public Builder setStadtName(@NotNull String stadtName) {
            this.stadtName = new StadtName(stadtName);

            return this;
        }

        /**
         * baut eine Instanz von Rechnungsadresse, wenn die Felder {@link #kundenName}, {@link #strasse} und {@link #stadt} befüllt sind
         *
         * @return Rechnungsadresse
         * @throws ValidationException wenn nicht alle notwendigen Felder gefüllt wurden
         */
        public RechnungsAdresse build() {
            if(this.kundenName == null) {
                throw new ValidationException("Rechnungsadresse.Builder: KundenName darf nicht 'null' sein!");
            }
            // this.firmenZusatz darf null sein
            if(this.strasse == null) {
                throw new ValidationException("Rechnungsadresse.Builder: Straße darf nicht 'null' sein!");
            }
            if(this.plz == null) {
                throw new ValidationException("Rechnungsadresse.Builder: PLZ darf nicht 'null' sein!");
            }
            if(this.stadtName == null) {
                throw new ValidationException("Rechnungsadresse.Builder: StadtName darf nicht 'null' sein!");
            }

            return new RechnungsAdresse(this);
        }
    }
}
