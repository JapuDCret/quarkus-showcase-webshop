package de.openknowledge.projects.webshop.domain.bewertung;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * ValueObject "BewertungsKommentar"
 */
public class BewertungsKommentar  implements Serializable {
    private static final long serialVersionUID = -6387999674365423337L;

    private final String kommentar;

    public BewertungsKommentar(@NotNull String kommentar) {
        this.kommentar = kommentar;
    }

    public String getKommentar() {
        return kommentar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BewertungsKommentar that = (BewertungsKommentar) o;
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
