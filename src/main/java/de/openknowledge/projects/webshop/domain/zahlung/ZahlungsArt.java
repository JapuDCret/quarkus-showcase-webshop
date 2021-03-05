package de.openknowledge.projects.webshop.domain.zahlung;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * ValueObject "ZahlungsArt"
 */
public class ZahlungsArt implements Serializable {
    private static final long serialVersionUID = 1428187404697921726L;

    private final String name;

    public ZahlungsArt(@NotNull String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ZahlungsArt that = (ZahlungsArt) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "ZahlungsArt{" +
                "name='" + name + '\'' +
                '}';
    }
}
