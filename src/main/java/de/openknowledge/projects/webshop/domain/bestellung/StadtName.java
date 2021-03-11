package de.openknowledge.projects.webshop.domain.bestellung;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * ValueObject "StadtName"
 */
public class StadtName implements Serializable  {
    private static final long serialVersionUID = 4457309026656725986L;
    
    private final String name;

    public StadtName(@NotNull String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StadtName that = (StadtName) o;
        return name == that.name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "StadtName{" +
                "name='" + name + '\'' +
                '}';
    }
}
