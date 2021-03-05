package de.openknowledge.projects.webshop.domain.bestellung.lieferung;

import de.openknowledge.projects.webshop.domain.bestellung.Bestellung;

import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * Aggregate "Lieferung"
 */
public class Lieferung implements Serializable {
    private static final long serialVersionUID = 9058464846356984703L;

    @NotNull
    private final Bestellung bestellung;

    @NotNull
    private final LieferZeitpunkt lieferZeitpunkt;

    private LieferStart lieferStart;

    private LieferEnde lieferEnde;

    public Bestellung getBestellung() {
        return bestellung;
    }

    public LieferZeitpunkt getLieferZeitpunkt() {
        return lieferZeitpunkt;
    }

    public LieferStart getLieferStart() {
        return lieferStart;
    }

    public void setLieferStart(LieferStart lieferStart) {
        this.lieferStart = lieferStart;
    }

    public LieferEnde getLieferEnde() {
        return lieferEnde;
    }

    public void setLieferEnde(LieferEnde lieferEnde) {
        this.lieferEnde = lieferEnde;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lieferung lieferung = (Lieferung) o;
        return Objects.equals(bestellung, lieferung.bestellung) && Objects.equals(lieferZeitpunkt, lieferung.lieferZeitpunkt) && Objects.equals(lieferStart, lieferung.lieferStart) && Objects.equals(lieferEnde, lieferung.lieferEnde);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bestellung, lieferZeitpunkt, lieferStart, lieferEnde);
    }

    @Override
    public String toString() {
        return "Lieferung{" +
                "bestellung=" + bestellung +
                ", lieferZeitpunkt=" + lieferZeitpunkt +
                ", lieferStart=" + lieferStart +
                ", lieferEnde=" + lieferEnde +
                '}';
    }

    public static Builder Builder() {
        return new Builder();
    }

    private Lieferung(Builder b) {
        this.bestellung = b.bestellung;
        this.lieferZeitpunkt = b.lieferZeitpunkt;
    }

    private static class Builder {
        private Bestellung bestellung;
        private LieferZeitpunkt lieferZeitpunkt;
        private LieferStart lieferStart;
        private LieferEnde lieferEnde;

        private Builder() { }

        private Builder setBestellung(@NotNull Bestellung bestellung) {
            this.bestellung = bestellung;

            return this;
        }

        private Builder setLieferZeitpunkt(@NotNull LieferZeitpunkt lieferZeitpunkt) {
            this.lieferZeitpunkt = lieferZeitpunkt;

            return this;
        }

        private Builder setLieferStart(@NotNull LieferStart lieferStart) {
            this.lieferStart = lieferStart;

            return this;
        }

        private Builder setLieferEnde(@NotNull LieferEnde lieferEnde) {
            this.lieferEnde = lieferEnde;

            return this;
        }

        /**
         * baut eine Instanz von Lieferung, wenn die Felder {@link #bestellung} und {@link #lieferZeitpunkt} befüllt sind
         *
         * @return Lieferung
         * @throws ValidationException wenn nicht alle notwendigen Felder gefüllt wurden Felder gefüllt wurden
         */
        public Lieferung build() {
            if(this.bestellung == null) {
                throw new ValidationException("Lieferung.Builder: Bestellung darf nicht 'null' sein!");
            }
            if(this.lieferZeitpunkt == null) {
                throw new ValidationException("Lieferung.Builder: LieferZeitpunkt darf nicht 'null' sein!");
            }
            // this.lieferStart darf null sein
            // this.lieferEnde darf null sein

            return new Lieferung(this);
        }
    }
}
