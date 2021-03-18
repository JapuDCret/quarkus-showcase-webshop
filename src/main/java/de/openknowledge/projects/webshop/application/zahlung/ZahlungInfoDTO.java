package de.openknowledge.projects.webshop.application.zahlung;

import de.openknowledge.projects.webshop.domain.zahlung.Zahlung;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

public class ZahlungInfoDTO implements Serializable {
    private static final long serialVersionUID = 7269706344080846083L;

    @NotNull
    private String bestellungId;

    @NotNull
    private String zahlungId;

    @NotNull
    private double betrag;

    @NotNull
    private boolean autorisiert;

    protected ZahlungInfoDTO() {
        super();
    }

    public ZahlungInfoDTO(@NotNull String bestellungId, @NotNull String zahlungId, double betrag, boolean autorisiert) {
        this();
        this.bestellungId = bestellungId;
        this.zahlungId = zahlungId;
        this.betrag = betrag;
        this.autorisiert = autorisiert;
    }

    public String getBestellungId() {
        return bestellungId;
    }

    public void setBestellungId(String bestellungId) {
        this.bestellungId = bestellungId;
    }

    public String getZahlungId() {
        return zahlungId;
    }

    public void setZahlungId(String zahlungId) {
        this.zahlungId = zahlungId;
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
        ZahlungInfoDTO that = (ZahlungInfoDTO) o;
        return Double.compare(that.betrag, betrag) == 0 && autorisiert == that.autorisiert && Objects.equals(bestellungId, that.bestellungId) && Objects.equals(zahlungId, that.zahlungId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bestellungId, zahlungId, betrag, autorisiert);
    }

    @Override
    public String toString() {
        return "ZahlungInfoDTO{" +
                "bestellungId='" + bestellungId + '\'' +
                ", zahlungId='" + zahlungId + '\'' +
                ", betrag=" + betrag +
                ", autorisiert=" + autorisiert +
                '}';
    }

    public static ZahlungInfoDTO of(Zahlung zahlung) {
        String bestellungId = zahlung.getBestellung().getBestellId().getId();
        String zahlungId = zahlung.getZahlungId().getId();
        double betrag = zahlung.getBetrag().doubleValue();
        boolean autorisiert = zahlung.getAutorisierung().isPresent();

        return new ZahlungInfoDTO(bestellungId, zahlungId, betrag, autorisiert);
    }
}
