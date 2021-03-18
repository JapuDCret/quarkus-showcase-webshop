package de.openknowledge.projects.webshop.domain.zahlung;

import java.io.Serializable;
import java.util.Objects;

/**
 * ValueObject "ZahlungId"
 */
public class ZahlungId implements Comparable<ZahlungId>, Serializable {
    private static final long serialVersionUID = -7217028524904547944L;

    private final String id;

    public ZahlungId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ZahlungId bestellId = (ZahlungId) o;
        return Objects.equals(id, bestellId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(ZahlungId that) {
        return this.id.compareTo(that.id);
    }

    @Override
    public String toString() {
        return "ZahlungId{" +
                "id='" + id + '\'' +
                '}';
    }
}
