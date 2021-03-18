package de.openknowledge.projects.webshop.domain.zahlung;

import de.openknowledge.projects.webshop.domain.bestellung.Bestellung;

import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

/**
 * Aggregate "Zahlung"
 */
public class Zahlung implements Serializable {
    private static final long serialVersionUID = -4346739859581266957L;

    @NotNull
    private final ZahlungId zahlungId;

    @NotNull
    private final Bestellung bestellung;

    @NotNull
    private final BigDecimal betrag;

    private Optional<ZahlungAutorisierung> autorisierung;

    public ZahlungId getZahlungId() {
        return zahlungId;
    }

    public Bestellung getBestellung() {
        return bestellung;
    }

    public BigDecimal getBetrag() {
        return betrag;
    }

    public Optional<ZahlungAutorisierung> getAutorisierung() {
        return autorisierung;
    }

    public void autorisiere() {
        this.autorisierung = Optional.of(new ZahlungAutorisierung(ZonedDateTime.now()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Zahlung zahlung = (Zahlung) o;
        return Objects.equals(zahlungId, zahlung.zahlungId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zahlungId);
    }

    @Override
    public String toString() {
        return "Zahlung{" +
                "zahlungId=" + zahlungId +
                ", bestellung=" + bestellung +
                ", betrag=" + betrag +
                ", autorisierung=" + autorisierung +
                '}';
    }

    public static Builder Builder() {
        return new Builder();
    }

    private Zahlung(Builder b) {
        this.zahlungId = b.zahlungId;
        this.bestellung = b.bestellung;
        this.betrag = b.betrag;
        this.autorisierung = b.autorisierung;
    }

    public static class Builder {
        private ZahlungId zahlungId;
        private Bestellung bestellung;
        private BigDecimal betrag;
        private Optional<ZahlungAutorisierung> autorisierung = Optional.empty();

        private Builder() { }

        private ZahlungId generateZahlungId() {
            // see https://stackoverflow.com/questions/14622622/generating-a-random-hex-string-of-length-50-in-java-me-j2me/14623245
            final int totalCharCounter = 32;
            final String baseString = "2a8749651d";

            Random r = new Random();
            StringBuffer sb = new StringBuffer(baseString);
            while(sb.length() < (totalCharCounter)){
                sb.append(Integer.toHexString(r.nextInt()));
            }

            return new ZahlungId(sb.toString().substring(0, totalCharCounter));
        }

        public Builder setBestellung(@NotNull Bestellung bestellung) {
            this.bestellung = bestellung;

            return this;
        }

        public Builder setAutorisierung(@NotNull Optional<ZahlungAutorisierung> autorisierung) {
            this.autorisierung = autorisierung;

            return this;
        }

        /**
         * baut eine Instanz von Zahlung, wenn die Felder {@link #bestellung} befüllt sind
         *
         * @return Zahlung
         * @throws ValidationException wenn nicht alle notwendigen Felder gefüllt wurden
         */
        public Zahlung build() {
            if(this.bestellung == null) {
                throw new ValidationException("Zahlung.Builder: Bestellung darf nicht 'null' sein!");
            }
            // this.autorisierung darf null sein

            this.zahlungId = generateZahlungId();
            this.betrag = bestellung.getProduktListe().getBetrag();

            return new Zahlung(this);
        }
    }
}
