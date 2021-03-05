package de.openknowledge.projects.webshop.domain.zahlung;

import de.openknowledge.projects.webshop.domain.bestellung.Bestellung;

import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Aggregate "Zahlung"
 */
public class Zahlung implements Serializable {
    private static final long serialVersionUID = -286819606597490400L;

    @NotNull
    private final Bestellung bestellung;

    @NotNull
    private final ZahlungsArt zahlungsArt;

    @NotNull
    private final BigDecimal betrag;

    private ZahlungsAutorisierung autorisierung;

    private ZahlungsAbschluss abschluss;

    public ZahlungsArt getZahlungsArt() {
        return zahlungsArt;
    }

    public BigDecimal getBetrag() {
        return betrag;
    }

    public ZahlungsAutorisierung getAutorisierung() {
        return autorisierung;
    }

    public void setAutorisierung(ZahlungsAutorisierung autorisierung) {
        this.autorisierung = autorisierung;
    }

    public ZahlungsAbschluss getAbschluss() {
        return abschluss;
    }

    public void setAbschluss(ZahlungsAbschluss abschluss) {
        this.abschluss = abschluss;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Zahlung zahlung = (Zahlung) o;
        return Objects.equals(zahlungsArt, zahlung.zahlungsArt) && Objects.equals(betrag, zahlung.betrag) && Objects.equals(autorisierung, zahlung.autorisierung) && Objects.equals(abschluss, zahlung.abschluss);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zahlungsArt, betrag, autorisierung, abschluss);
    }

    @Override
    public String toString() {
        return "Zahlung{" +
                "zahlungsArt=" + zahlungsArt +
                ", betrag=" + betrag +
                ", autorisierung=" + autorisierung +
                ", abschluss=" + abschluss +
                '}';
    }

    public static Builder Builder() {
        return new Builder();
    }

    private Zahlung(Builder b) {
        this.bestellung = b.bestellung;
        this.zahlungsArt = b.zahlungsArt;
        this.betrag = b.betrag;
        this.autorisierung = b.autorisierung;
        this.abschluss = b.abschluss;
    }

    public static class Builder {
        private Bestellung bestellung;
        private ZahlungsArt zahlungsArt;
        private BigDecimal betrag;
        private ZahlungsAutorisierung autorisierung;
        private ZahlungsAbschluss abschluss;

        private Builder() { }

        public Builder setBestellung(@NotNull Bestellung bestellung) {
            this.bestellung = bestellung;

            return this;
        }

        public Builder setZahlungsArt(@NotNull ZahlungsArt zahlungsArt) {
            this.zahlungsArt = zahlungsArt;

            return this;
        }

        public Builder setBetrag(@NotNull BigDecimal betrag) {
            this.betrag = betrag;

            return this;
        }

        public Builder setAutorisierung(@NotNull ZahlungsAutorisierung autorisierung) {
            this.autorisierung = autorisierung;

            return this;
        }

        public Builder setAbschluss(@NotNull ZahlungsAbschluss abschluss) {
            this.abschluss = abschluss;

            return this;
        }

        /**
         * baut eine Instanz von Zahlung, wenn die Felder {@link #bestellung}, {@link #zahlungsArt} und {@link #betrag} befüllt sind
         *
         * @return Zahlung
         * @throws ValidationException wenn nicht alle notwendigen Felder gefüllt wurden
         */
        public Zahlung build() {
            if(this.bestellung == null) {
                throw new ValidationException("Zahlung.Builder: Bestellung darf nicht 'null' sein!");
            }
            if(this.zahlungsArt == null) {
                throw new ValidationException("Zahlung.Builder: ZahlungsArt darf nicht 'null' sein!");
            }
            if(this.betrag == null) {
                throw new ValidationException("Zahlung.Builder: Betrag darf nicht 'null' sein!");
            }
            // this.autorisierung darf null sein
            // this.abschluss darf null sein

            return new Zahlung(this);
        }
    }
}
