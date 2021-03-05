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
    private final Stadt stadt;

    public KundenName getKundenName() {
        return kundenName;
    }

    public Strasse getStrasse() {
        return strasse;
    }

    public Stadt getStadt() {
        return stadt;
    }

    public FirmenZusatz getFirmenZusatz() {
        return firmenZusatz;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RechnungsAdresse that = (RechnungsAdresse) o;
        return Objects.equals(kundenName, that.kundenName) && Objects.equals(firmenZusatz, that.firmenZusatz) && Objects.equals(strasse, that.strasse) && Objects.equals(stadt, that.stadt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(kundenName, firmenZusatz, strasse, stadt);
    }

    @Override
    public String toString() {
        return "Rechnungsadresse{" +
                "kundenName=" + kundenName +
                ", firmenZusatz=" + firmenZusatz +
                ", strasse=" + strasse +
                ", stadt=" + stadt +
                '}';
    }

    public static Builder Builder() {
        return new Builder();
    }

    private RechnungsAdresse(Builder b) {
        this.kundenName = b.kundenName;
        this.firmenZusatz = b.firmenZusatz;
        this.strasse = b.strasse;
        this.stadt = b.stadt;
    }

    public static class Builder {
        private KundenName kundenName;
        private FirmenZusatz firmenZusatz;
        private Strasse strasse;
        private Stadt stadt;

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

        public Builder setStadt(int plz, @NotNull String stadtName) {
            this.stadt = new Stadt(plz, stadtName);

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
            // this.lieferadresse.firmenZusatz darf null sein
            if(this.strasse == null) {
                throw new ValidationException("Rechnungsadresse.Builder: Straße darf nicht 'null' sein!");
            }
            if(this.stadt == null) {
                throw new ValidationException("Rechnungsadresse.Builder: Stadt darf nicht 'null' sein!");
            }

            return new RechnungsAdresse(this);
        }
    }
}
