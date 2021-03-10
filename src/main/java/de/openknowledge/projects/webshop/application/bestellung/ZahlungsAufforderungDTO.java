package de.openknowledge.projects.webshop.application.bestellung;

import de.openknowledge.projects.webshop.domain.zahlung.Zahlung;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

public class ZahlungsAufforderungDTO implements Serializable {
    private static final long serialVersionUID = 3363144929465633010L;

    @NotNull
    private String bestellId;

    @NotNull
    private String zahlungsId;

    @NotNull
    private double betrag;

    protected ZahlungsAufforderungDTO() {
        super();
    }

    public ZahlungsAufforderungDTO(@NotNull String bestellId, @NotNull String zahlungsId, @NotNull double betrag) {
        this();
        this.bestellId = bestellId;
        this.zahlungsId = zahlungsId;
        this.betrag = betrag;
    }

    public String getBestellId() {
        return bestellId;
    }

    public void setBestellId(String bestellId) {
        this.bestellId = bestellId;
    }

    public String getZahlungsId() {
        return zahlungsId;
    }

    public void setZahlungsId(String zahlungsId) {
        this.zahlungsId = zahlungsId;
    }

    public double getBetrag() {
        return betrag;
    }

    public void setBetrag(double betrag) {
        this.betrag = betrag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ZahlungsAufforderungDTO that = (ZahlungsAufforderungDTO) o;
        return Objects.equals(bestellId, that.bestellId) && Objects.equals(zahlungsId, that.zahlungsId) && Objects.equals(betrag, that.betrag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bestellId, zahlungsId, betrag);
    }

    @Override
    public String toString() {
        return "ZahlungsAufforderungDTO{" +
                "bestellId='" + bestellId + '\'' +
                ", zahlungsId='" + zahlungsId + '\'' +
                ", betrag=" + betrag +
                '}';
    }

    public static ZahlungsAufforderungDTO of(Zahlung zahlung) {
        String bestellId = zahlung.getBestellung().getBestellId().getId();
        String zahlungsId = zahlung.getZahlungsId().getId();
        double betrag = zahlung.getBetrag().doubleValue();

        return new ZahlungsAufforderungDTO(bestellId, zahlungsId, betrag);
    }
}
