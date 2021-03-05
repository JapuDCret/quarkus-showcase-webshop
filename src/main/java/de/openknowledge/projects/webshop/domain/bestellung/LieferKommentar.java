package de.openknowledge.projects.webshop.domain.bestellung;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * ValueObject "LieferKommentar"
 */
public class LieferKommentar implements Serializable {
    private static final long serialVersionUID = -4721760141423951711L;

    private final String kommentar;

    public LieferKommentar(@NotNull String kommentar) {
        this.kommentar = kommentar;
    }

    public String getKommentar() {
        return kommentar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LieferKommentar that = (LieferKommentar) o;
        return Objects.equals(kommentar, that.kommentar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(kommentar);
    }

    @Override
    public String toString() {
        return "LieferKommentar{" +
                "kommentar='" + kommentar + '\'' +
                '}';
    }
}
