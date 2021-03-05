package de.openknowledge.projects.webshop.domain.zahlung;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * ValueObject "ZahlungsAutorisierung"
 */
public class ZahlungsAutorisierung implements Serializable {
    private static final long serialVersionUID = 4768695385078786467L;

    private final ZonedDateTime zeitpunkt;

    public ZahlungsAutorisierung(@NotNull ZonedDateTime zeitpunkt) {
        this.zeitpunkt = zeitpunkt;
    }

    public ZonedDateTime getZeitpunkt() {
        return zeitpunkt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ZahlungsAutorisierung that = (ZahlungsAutorisierung) o;
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
