package de.openknowledge.projects.webshop.domain.bestellung;

import java.io.Serializable;
import java.util.Objects;

/**
 * ValueObject "BestellungId"
 */
public class BestellungId implements Comparable<BestellungId>, Serializable {
    private static final long serialVersionUID = -8686851430840544576L;

    private final String id;

    public BestellungId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BestellungId bestellungId = (BestellungId) o;
        return Objects.equals(id, bestellungId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(BestellungId that) {
        return this.id.compareTo(that.id);
    }

    @Override
    public String toString() {
        return "BestellungId{" +
                "id='" + id + '\'' +
                '}';
    }
}
