package de.openknowledge.projects.webshop.domain.bestellung.filiale;

import de.openknowledge.projects.webshop.domain.bestellung.*;

import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * Aggregate "FilialAdresse"
 */
public class FilialAdresse implements Serializable {
    private static final long serialVersionUID = -8467590496336736997L;

    @NotNull
    private final FilialPostAnschriftName postAnschriftName;

    @NotNull
    private final Strasse strasse;

    @NotNull
    private final Stadt stadt;

    public FilialPostAnschriftName getPostAnschriftName() {
        return postAnschriftName;
    }

    public Strasse getStrasse() {
        return strasse;
    }

    public Stadt getStadt() {
        return stadt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilialAdresse that = (FilialAdresse) o;
        return Objects.equals(postAnschriftName, that.postAnschriftName) && Objects.equals(strasse, that.strasse) && Objects.equals(stadt, that.stadt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postAnschriftName, strasse, stadt);
    }

    @Override
    public String toString() {
        return "FilialAdresse{" +
                "postAnschriftName=" + postAnschriftName +
                ", strasse=" + strasse +
                ", stadt=" + stadt +
                '}';
    }

    public static Builder Builder() {
        return new Builder();
    }

    public FilialAdresse(Builder b) {
        this.postAnschriftName = b.postAnschriftName;
        this.strasse = b.strasse;
        this.stadt = b.stadt;
    }

    public static class Builder {
        private FilialPostAnschriftName postAnschriftName;
        private Strasse strasse;
        private Stadt stadt;

        private Builder() { }

        public Builder setPostAnschriftName(@NotNull String postAnschriftName) {
            this.postAnschriftName = new FilialPostAnschriftName(postAnschriftName);

            return this;
        }

        public Builder setStrasse(@NotNull String strasse) {
            this.strasse = new Strasse(strasse);

            return this;
        }

        public Builder setStadt(int plz, @NotNull String stadtName) {
            this.stadt = new Stadt(plz, stadtName);

            return this;
        }

        /**
         * baut eine Instanz von FilialAdresse, wenn die Felder {@link #postAnschriftName}, {@link #strasse} und {@link #stadt} befüllt sind
         *
         * @return FilialAdresse
         * @throws ValidationException wenn nicht alle notwendigen Felder gefüllt wurden
         */
        public FilialAdresse build() {
            if(this.postAnschriftName == null) {
                throw new ValidationException("FilialAdresse.Builder: PostAnschriftName darf nicht 'null' sein!");
            }
            if(this.strasse == null) {
                throw new ValidationException("FilialAdresse.Builder: Straße darf nicht 'null' sein!");
            }
            if(this.stadt == null) {
                throw new ValidationException("FilialAdresse.Builder: Stadt darf nicht 'null' sein!");
            }

            return new FilialAdresse(this);
        }
    }
}
