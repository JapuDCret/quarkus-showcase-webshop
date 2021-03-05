package de.openknowledge.projects.webshop.domain.bewertung;

import de.openknowledge.projects.webshop.domain.bestellung.filiale.Filiale;

import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * Aggregate "Bewertung"
 */
public class Bewertung implements Serializable {
    private static final long serialVersionUID = -939128864107269922L;

    @NotNull
    private final Filiale filiale;

    @NotNull
    private final BewertungsDatum datum;

    @NotNull
    private final Wertung wertung;

    private final BewertungsKommentar kommentar;

    public BewertungsDatum getDatum() {
        return datum;
    }

    public Wertung getWertung() {
        return wertung;
    }

    public BewertungsKommentar getKommentar() {
        return kommentar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bewertung bewertung = (Bewertung) o;
        return Objects.equals(datum, bewertung.datum) && Objects.equals(wertung, bewertung.wertung) && Objects.equals(kommentar, bewertung.kommentar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(datum, wertung, kommentar);
    }

    public static Builder Builder() {
        return new Builder();
    }

    private Bewertung(Builder b) {
        this.filiale = b.filiale;
        this.datum = b.datum;
        this.wertung = b.wertung;
        this.kommentar = b.kommentar;
    }

    public static class Builder {
        private Filiale filiale;
        private BewertungsDatum datum;
        private Wertung wertung;
        private BewertungsKommentar kommentar;

        private Builder() { }

        public Builder setFiliale(@NotNull Filiale filiale) {
            this.filiale = filiale;

            return this;
        }

        public Builder setDatum(@NotNull BewertungsDatum datum) {
            this.datum = datum;

            return this;
        }

        public Builder setWertung(@NotNull Wertung wertung) {
            this.wertung = wertung;

            return this;
        }

        public Builder setKommentar(@NotNull BewertungsKommentar kommentar) {
            this.kommentar = kommentar;

            return this;
        }

        /**
         * baut eine Instanz von Bewertung, wenn die Felder {@link #datum}, {@link #wertung} und {@link #kommentar} befüllt sind
         *
         * @return Bewertung
         * @throws ValidationException wenn nicht alle notwendigen Felder gefüllt wurden
         */
        public Bewertung build() {
            if(this.filiale == null) {
                throw new ValidationException("Bewertung.Builder: Filiale darf nicht 'null' sein!");
            }
            if(this.datum == null) {
                throw new ValidationException("Bewertung.Builder: BewertungsDatum darf nicht 'null' sein!");
            }
            if(this.wertung == null) {
                throw new ValidationException("Bewertung.Builder: Wertung darf nicht 'null' sein!");
            }
            // this.kommentar darf null sein

            return new Bewertung(this);
        }
    }
}
