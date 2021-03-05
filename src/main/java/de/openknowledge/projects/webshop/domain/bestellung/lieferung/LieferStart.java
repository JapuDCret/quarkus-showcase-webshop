package de.openknowledge.projects.webshop.domain.bestellung.lieferung;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * ValueObject "LieferStart"
 */
public class LieferStart implements Comparable<LieferStart>, Serializable {
    private static final long serialVersionUID = 3045241953176123428L;

    private final ZonedDateTime zeitpunkt;

    public LieferStart(@NotNull ZonedDateTime zeitpunkt) {
        this.zeitpunkt = zeitpunkt;
    }

    public ZonedDateTime getZeitpunkt() {
        return zeitpunkt;
    }

    @Override
    public int compareTo(LieferStart o) {
        return this.zeitpunkt.compareTo(o.zeitpunkt);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LieferStart that = (LieferStart) o;
        return Objects.equals(zeitpunkt, that.zeitpunkt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zeitpunkt);
    }

    @Override
    public String toString() {
        return "LieferStart{" +
                "zeitpunkt=" + zeitpunkt +
                '}';
    }
}
