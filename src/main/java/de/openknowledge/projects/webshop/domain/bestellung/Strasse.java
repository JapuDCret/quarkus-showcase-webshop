package de.openknowledge.projects.webshop.domain.bestellung;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * ValueObject "Strasse"
 */
public class Strasse implements Serializable {
    private static final long serialVersionUID = 3455067855847748596L;

    private final String strasse;

    public Strasse(@NotNull String strasse) {
        this.strasse = strasse;
    }

    public String getStrasse() {
        return strasse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Strasse that = (Strasse) o;
        return strasse.equals(that.strasse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(strasse);
    }

    @Override
    public String toString() {
        return "Strasse{" +
                "strasse='" + strasse + '\'' +
                '}';
    }
}
