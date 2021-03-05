package de.openknowledge.projects.webshop.domain.bestellung.filiale;

import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * Aggregate "Filiale"
 */
public class Filiale implements Serializable {
    private static final long serialVersionUID = 4304906754578356396L;

    @NotNull
    private final FilialName name;

    @NotNull
    private final FilialAdresse adresse;

    public FilialName getName() {
        return name;
    }

    public FilialAdresse getAdresse() {
        return adresse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Filiale filiale = (Filiale) o;
        return Objects.equals(name, filiale.name) && Objects.equals(adresse, filiale.adresse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, adresse);
    }

    @Override
    public String toString() {
        return "Filiale{" +
                "name=" + name +
                ", adresse=" + adresse +
                '}';
    }

    public static Builder Builder() {
        return new Builder();
    }

    private Filiale(Builder b) {
        this.name = b.name;
        this.adresse = b.adresse;
    }

    public static class Builder {
        private FilialName name;
        private FilialAdresse adresse;

        private Builder() { }

        public Builder setName(@NotNull FilialName name) {
            this.name = name;

            return this;
        }

        public Builder setFilialAdresse(@NotNull FilialAdresse adresse) {
            this.adresse = adresse;

            return this;
        }

        /**
         * baut eine Instanz von Lieferung, wenn die Felder {@link #name} und {@link #adresse} befüllt sind
         *
         * @return Lieferung
         * @throws ValidationException wenn nicht alle notwendigen Felder gefüllt wurden Felder gefüllt wurden
         */
        public Filiale build() {
            if(this.name == null) {
                throw new ValidationException("Filiale.Builder: Name darf nicht 'null' sein!");
            }
            if(this.adresse == null) {
                throw new ValidationException("Filiale.Builder: Adresse darf nicht 'null' sein!");
            }

            return new Filiale(this);
        }
    }
}
