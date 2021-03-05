package de.openknowledge.projects.webshop.domain.bestellung.lieferung;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * ValueObject "LieferEnde"
 */
public class LieferEnde implements Comparable<LieferEnde>, Serializable {
    private static final long serialVersionUID = 931561594042232449L;

    private final ZonedDateTime zeitpunkt;

    public LieferEnde(@NotNull ZonedDateTime zeitpunkt) {
        this.zeitpunkt = zeitpunkt;
    }

    public ZonedDateTime getZeitpunkt() {
        return zeitpunkt;
    }

    @Override
    public int compareTo(LieferEnde o) {
        return this.zeitpunkt.compareTo(o.zeitpunkt);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LieferEnde that = (LieferEnde) o;
        return Objects.equals(zeitpunkt, that.zeitpunkt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zeitpunkt);
    }

    @Override
    public String toString() {
        return "LieferEnde{" +
                "zeitpunkt=" + zeitpunkt +
                '}';
    }
}
