package de.openknowledge.projects.webshop.domain.bestellung.lieferung;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * ValueObject "LieferZeitpunkt"
 */
public class LieferZeitpunkt implements Comparable<LieferZeitpunkt>, Serializable {
    private static final long serialVersionUID = -2794001014624901987L;

    private final ZonedDateTime zeitpunkt;

    public LieferZeitpunkt(@NotNull ZonedDateTime zeitpunkt) {
        this.zeitpunkt = zeitpunkt;
    }

    public ZonedDateTime getZeitpunkt() {
        return zeitpunkt;
    }

    @Override
    public int compareTo(LieferZeitpunkt o) {
        return this.zeitpunkt.compareTo(o.zeitpunkt);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LieferZeitpunkt that = (LieferZeitpunkt) o;
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
