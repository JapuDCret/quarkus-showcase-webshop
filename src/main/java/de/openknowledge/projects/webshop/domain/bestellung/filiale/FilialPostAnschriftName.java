package de.openknowledge.projects.webshop.domain.bestellung.filiale;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * ValueObject "FilialPostAnschriftName"
 */
public class FilialPostAnschriftName implements Serializable {
    private static final long serialVersionUID = 8829486452290535888L;

    private final String name;

    public FilialPostAnschriftName(@NotNull String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilialPostAnschriftName that = (FilialPostAnschriftName) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "FilialPostAnschriftName{" +
                "name='" + name + '\'' +
                '}';
    }
}
