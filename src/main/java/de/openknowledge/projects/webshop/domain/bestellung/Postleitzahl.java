package de.openknowledge.projects.webshop.domain.bestellung;

import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * ValueObject "Postleitzahl"
 */
public class Postleitzahl implements Serializable  {
    private static final long serialVersionUID = -5034920939044989117L;

    private final String plz;

    public Postleitzahl(@NotNull String plz) {
        if(plz.length() != 5) {
            throw new ValidationException("invalid PLZ! plz = " + plz);
        }
        this.plz = plz;
    }

    public String getPlz() {
        return plz;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Postleitzahl that = (Postleitzahl) o;
        return plz == that.plz;
    }

    @Override
    public int hashCode() {
        return Objects.hash(plz);
    }

    @Override
    public String toString() {
        return "PLZ{" +
                "plz=" + plz +
                '}';
    }
}
