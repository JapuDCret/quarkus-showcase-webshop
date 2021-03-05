package de.openknowledge.projects.webshop.domain.bestellung.filiale;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

public class FilialName implements Serializable {
    private static final long serialVersionUID = 6468910867333771926L;

    private final String name;

    public FilialName(@NotNull String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilialName that = (FilialName) o;
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
