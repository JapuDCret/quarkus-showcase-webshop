package de.openknowledge.projects.webshop.domain.bestellung;

import java.io.Serializable;
import java.util.Objects;

/**
 * ValueObject "BestellId"
 */
public class BestellId implements Comparable<BestellId>, Serializable {
    private static final long serialVersionUID = -8704431729381243934L;

    private final String id;

    public BestellId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BestellId bestellId = (BestellId) o;
        return Objects.equals(id, bestellId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(BestellId that) {
        return this.id.compareTo(that.id);
    }

    @Override
    public String toString() {
        return "BestellId{" +
                "id='" + id + '\'' +
                '}';
    }
}
