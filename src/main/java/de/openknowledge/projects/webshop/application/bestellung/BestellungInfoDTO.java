package de.openknowledge.projects.webshop.application.bestellung;

import de.openknowledge.projects.webshop.domain.bestellung.Bestellung;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

public class BestellungInfoDTO implements Serializable {
    private static final long serialVersionUID = 7328265679123102208L;

    @NotNull
    private final String bestellungId;

    @NotNull
    private final double betrag;

    public BestellungInfoDTO(@NotNull String bestellungId, @NotNull double betrag) {
        this.bestellungId = bestellungId;
        this.betrag = betrag;
    }

    public String getBestellungId() {
        return bestellungId;
    }

    public double getBetrag() {
        return betrag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BestellungInfoDTO that = (BestellungInfoDTO) o;
        return Double.compare(that.betrag, betrag) == 0 && Objects.equals(bestellungId, that.bestellungId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bestellungId, betrag);
    }

    @Override
    public String toString() {
        return "BestellungInfoDTO{" +
                "bestellungId='" + bestellungId + '\'' +
                ", betrag=" + betrag +
                '}';
    }

    public static BestellungInfoDTO of(Bestellung bestellung) {
        return new BestellungInfoDTO(
                bestellung.getBestellId().getId(),
                bestellung.getProduktListe().getBetrag().doubleValue()
        );
    }
}
