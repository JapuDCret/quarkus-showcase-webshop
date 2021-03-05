package de.openknowledge.projects.webshop.domain.bestellung;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * ValueObject "FirmenZusatz"
 */
public class FirmenZusatz implements Serializable {
    private static final long serialVersionUID = -6637179629884146374L;

    private final String firmenZusatz;

    public FirmenZusatz(@NotNull String firmenZusatz) {
        this.firmenZusatz = firmenZusatz;
    }

    public String getFirmenZusatz() {
        return firmenZusatz;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FirmenZusatz that = (FirmenZusatz) o;
        return Objects.equals(firmenZusatz, that.firmenZusatz);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firmenZusatz);
    }

    @Override
    public String toString() {
        return "FirmenZusatz{" +
                "firmenZusatz='" + firmenZusatz + '\'' +
                '}';
    }
}
