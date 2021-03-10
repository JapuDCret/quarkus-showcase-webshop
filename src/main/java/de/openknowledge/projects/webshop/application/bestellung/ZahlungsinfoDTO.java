package de.openknowledge.projects.webshop.application.bestellung;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

public class ZahlungsinfoDTO implements Serializable {
    private static final long serialVersionUID = -6563220814478095446L;

    @NotNull
    private String bestellId;

    @NotNull
    private String zahlungsId;

    @NotNull
    @Schema(required = true)
    private double betrag;

    protected ZahlungsinfoDTO() {
        super();
    }

    public ZahlungsinfoDTO(@NotNull String bestellId, @NotNull String zahlungsId, @NotNull double betrag) {
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
        ZahlungsinfoDTO that = (ZahlungsinfoDTO) o;
        return Objects.equals(bestellId, that.bestellId) && Objects.equals(zahlungsId, that.zahlungsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bestellId, zahlungsId);
    }

    @Override
    public String toString() {
        return "ZahlungsinfoDTO{" +
                "bestellId='" + bestellId + '\'' +
                ", zahlungsId='" + zahlungsId + '\'' +
                ", betrag=" + betrag +
                '}';
    }
}
