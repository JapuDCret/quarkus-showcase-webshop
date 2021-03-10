package de.openknowledge.projects.webshop.domain.zahlung;

import java.io.Serializable;
import java.util.Objects;

/**
 * ValueObject "ZahlungsId"
 */
public class ZahlungsId implements Comparable<ZahlungsId>, Serializable {
    private static final long serialVersionUID = -7217028524904547944L;

    private final String id;

    public ZahlungsId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ZahlungsId bestellId = (ZahlungsId) o;
        return Objects.equals(id, bestellId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(ZahlungsId that) {
        return this.id.compareTo(that.id);
    }

    @Override
    public String toString() {
        return "ZahlungsId{" +
                "id='" + id + '\'' +
                '}';
    }
}
