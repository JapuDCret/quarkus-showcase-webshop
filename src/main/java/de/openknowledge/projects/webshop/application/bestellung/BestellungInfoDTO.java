package de.openknowledge.projects.webshop.application.bestellung;

import de.openknowledge.projects.webshop.domain.bestellung.Bestellung;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

public class BestellungInfoDTO implements Serializable {
    private static final long serialVersionUID = -4990855733147896887L;

    @NotNull
    private final String bestellId;

    @NotNull
    private final double betrag;

    public BestellungInfoDTO(@NotNull String bestellId, @NotNull double betrag) {
        this.bestellId = bestellId;
        this.betrag = betrag;
    }

    public String getBestellId() {
        return bestellId;
    }

    public double getBetrag() {
        return betrag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BestellungInfoDTO that = (BestellungInfoDTO) o;
        return Double.compare(that.betrag, betrag) == 0 && Objects.equals(bestellId, that.bestellId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bestellId, betrag);
    }

    @Override
    public String toString() {
        return "BestellungInfoDTO{" +
                "bestellId='" + bestellId + '\'' +
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
