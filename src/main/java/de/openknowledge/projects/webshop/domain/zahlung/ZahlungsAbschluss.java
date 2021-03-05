package de.openknowledge.projects.webshop.domain.zahlung;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * ValueObject "ZahlungsAbschluss"
 */
public class ZahlungsAbschluss implements Serializable {
    private static final long serialVersionUID = -1342989155932513122L;

    private final ZonedDateTime zeitpunkt;

    public ZahlungsAbschluss(@NotNull ZonedDateTime zeitpunkt) {
        this.zeitpunkt = zeitpunkt;
    }

    public ZonedDateTime getZeitpunkt() {
        return zeitpunkt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ZahlungsAbschluss that = (ZahlungsAbschluss) o;
        return Objects.equals(zeitpunkt, that.zeitpunkt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zeitpunkt);
    }

    @Override
    public String toString() {
        return "LieferZeitpunkt{" +
                "zeitpunkt=" + zeitpunkt +
                '}';
    }
}
