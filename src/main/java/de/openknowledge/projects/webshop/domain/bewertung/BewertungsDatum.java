package de.openknowledge.projects.webshop.domain.bewertung;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * ValueObject "BewertungsDatum"
 */
public class BewertungsDatum implements Serializable {
    private static final long serialVersionUID = 2059564965389803732L;

    private final ZonedDateTime zeitpunkt;

    public BewertungsDatum(@NotNull ZonedDateTime zeitpunkt) {
        this.zeitpunkt = zeitpunkt;
    }

    public ZonedDateTime getZeitpunkt() {
        return zeitpunkt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BewertungsDatum that = (BewertungsDatum) o;
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
