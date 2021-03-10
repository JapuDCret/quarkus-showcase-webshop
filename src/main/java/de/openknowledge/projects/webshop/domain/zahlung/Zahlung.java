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
    private static final long serialVersionUID = -286819606597490400L;

    @NotNull
    private final ZahlungsId zahlungsId;

    @NotNull
    private final Bestellung bestellung;

//    @NotNull
//    private final ZahlungsArt zahlungsArt;

    @NotNull
    private final BigDecimal betrag;

    private Optional<ZahlungsAutorisierung> autorisierung;

    public ZahlungsId getZahlungsId() {
        return zahlungsId;
    }

    public Bestellung getBestellung() {
        return bestellung;
    }

    public BigDecimal getBetrag() {
        return betrag;
    }

    public Optional<ZahlungsAutorisierung> getAutorisierung() {
        return autorisierung;
    }

    public void autorisiere() {
        this.autorisierung = Optional.of(new ZahlungsAutorisierung(ZonedDateTime.now()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Zahlung zahlung = (Zahlung) o;
        return Objects.equals(zahlungsId, zahlung.zahlungsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zahlungsId);
    }

    @Override
    public String toString() {
        return "Zahlung{" +
                "zahlungsId=" + zahlungsId +
                ", bestellung=" + bestellung +
                ", betrag=" + betrag +
                ", autorisierung=" + autorisierung +
                '}';
    }

    public static Builder Builder() {
        return new Builder();
    }

    private Zahlung(Builder b) {
        this.zahlungsId = b.zahlungsId;
        this.bestellung = b.bestellung;
//        this.zahlungsArt = b.zahlungsArt;
        this.betrag = b.betrag;
        this.autorisierung = b.autorisierung;
    }

    public static class Builder {
        private ZahlungsId zahlungsId;
        private Bestellung bestellung;
//        private ZahlungsArt zahlungsArt;
        private BigDecimal betrag;
        private Optional<ZahlungsAutorisierung> autorisierung = Optional.empty();

        private Builder() { }

        private ZahlungsId generateZahlungsId() {
            // see https://stackoverflow.com/questions/14622622/generating-a-random-hex-string-of-length-50-in-java-me-j2me/14623245
            final int totalCharCounter = 32;
            final String baseString = "2a8749651d";

            Random r = new Random();
            StringBuffer sb = new StringBuffer(baseString);
            while(sb.length() < (totalCharCounter)){
                sb.append(Integer.toHexString(r.nextInt()));
            }

            return new ZahlungsId(sb.toString().substring(0, totalCharCounter));
        }

        public Builder setBestellung(@NotNull Bestellung bestellung) {
            this.bestellung = bestellung;

            return this;
        }

//        public Builder setZahlungsArt(@NotNull ZahlungsArt zahlungsArt) {
//            this.zahlungsArt = zahlungsArt;
//
//            return this;
//        }

        public Builder setAutorisierung(@NotNull Optional<ZahlungsAutorisierung> autorisierung) {
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
//            if(this.zahlungsArt == null) {
//                throw new ValidationException("Zahlung.Builder: ZahlungsArt darf nicht 'null' sein!");
//            }
            // this.autorisierung darf null sein

            this.zahlungsId = generateZahlungsId();
            this.betrag = bestellung.getProduktListe().getBetrag();

            return new Zahlung(this);
        }
    }
}
