package de.openknowledge.projects.webshop.domain.bestellung;

import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Aggregate "ProduktListe"
 */
public class ProduktListe implements Serializable {
    private static final long serialVersionUID = 976238722135651570L;

    @NotNull
    private final List<ProduktAuswahl> produkte;

    public List<ProduktAuswahl> getProdukte() {
        return Collections.unmodifiableList(produkte);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProduktListe that = (ProduktListe) o;
        return Objects.equals(produkte, that.produkte);
    }

    @Override
    public int hashCode() {
        return Objects.hash(produkte);
    }

    @Override
    public String toString() {
        return "ProduktListe{" +
                "produkte=" + produkte +
                '}';
    }

    public static Builder Builder() {
        return new Builder();
    }

    private ProduktListe(Builder b) {
        this.produkte = b.produkte;
    }

    public static class Builder {
        private List<ProduktAuswahl> produkte;

        private Builder() {
            this.produkte = new ArrayList<>();
        }

        public Builder addProdukt(@NotNull ProduktAuswahl produktAuswahl) {
            this.produkte.add(produktAuswahl);

            return this;
        }

        /**
         * baut eine Instanz von ProduktListe, wenn mindestens ein {@link Produkt} hinzugefügt wurde
         *
         * @return ProduktListe
         * @throws ValidationException wenn nicht alle notwendigen Felder gefüllt wurden
         */
        public ProduktListe build() {
            if(produkte.size() == 0) {
                throw new ValidationException("ProduktListe.Builder: Keine Produkte vorhanden!");
            }

            return new ProduktListe(this);
        }
    }
}
