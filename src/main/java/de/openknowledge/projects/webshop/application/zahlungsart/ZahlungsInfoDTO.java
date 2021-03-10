package de.openknowledge.projects.webshop.application.zahlungsart;

import de.openknowledge.projects.webshop.domain.zahlung.Zahlung;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

public class ZahlungsInfoDTO implements Serializable {
    private static final long serialVersionUID = 8278379066041663506L;

    @NotNull
    private String bestellId;

    @NotNull
    private String zahlungsId;

    @NotNull
    private double betrag;

    @NotNull
    private boolean autorisiert;

    protected ZahlungsInfoDTO() {
        super();
    }

    public ZahlungsInfoDTO(@NotNull String bestellId, @NotNull String zahlungsId, double betrag, boolean autorisiert) {
        this();
        this.bestellId = bestellId;
        this.zahlungsId = zahlungsId;
        this.betrag = betrag;
        this.autorisiert = autorisiert;
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

    public boolean isAutorisiert() {
        return autorisiert;
    }

    public void setAutorisiert(boolean autorisiert) {
        this.autorisiert = autorisiert;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ZahlungsInfoDTO that = (ZahlungsInfoDTO) o;
        return Double.compare(that.betrag, betrag) == 0 && autorisiert == that.autorisiert && Objects.equals(bestellId, that.bestellId) && Objects.equals(zahlungsId, that.zahlungsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bestellId, zahlungsId, betrag, autorisiert);
    }

    @Override
    public String toString() {
        return "ZahlungsInfoDTO{" +
                "bestellId='" + bestellId + '\'' +
                ", zahlungsId='" + zahlungsId + '\'' +
                ", betrag=" + betrag +
                ", autorisiert=" + autorisiert +
                '}';
    }

    public static ZahlungsInfoDTO of(Zahlung zahlung) {
        String bestellId = zahlung.getBestellung().getBestellId().getId();
        String zahlungsId = zahlung.getZahlungsId().getId();
        double betrag = zahlung.getBetrag().doubleValue();
        boolean autorisiert = zahlung.getAutorisierung().isPresent();

        return new ZahlungsInfoDTO(bestellId, zahlungsId, betrag, autorisiert);
    }
}
