package de.openknowledge.projects.webshop.domain.zahlung;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * ValueObject "ZahlungAbschluss"
 */
public class ZahlungAbschluss implements Serializable {
    private static final long serialVersionUID = 6637119688915135741L;

    private final ZonedDateTime zeitpunkt;

    public ZahlungAbschluss(@NotNull ZonedDateTime zeitpunkt) {
        this.zeitpunkt = zeitpunkt;
    }

    public ZonedDateTime getZeitpunkt() {
        return zeitpunkt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ZahlungAbschluss that = (ZahlungAbschluss) o;
        return Objects.equals(zeitpunkt, that.zeitpunkt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zeitpunkt);
    }

    @Override
    public String toString() {
        return "ZahlungAbschluss{" +
                "zeitpunkt=" + zeitpunkt +
                '}';
    }
}
