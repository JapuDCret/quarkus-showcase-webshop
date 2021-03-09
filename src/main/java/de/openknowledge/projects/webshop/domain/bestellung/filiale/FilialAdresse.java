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
    private final Postleitzahl plz;

    @NotNull
    private final StadtName stadtName;

    public FilialPostAnschriftName getPostAnschriftName() {
        return postAnschriftName;
    }

    public Strasse getStrasse() {
        return strasse;
    }

    public Postleitzahl getPlz() {
        return plz;
    }

    public StadtName getStadtName() {
        return stadtName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilialAdresse that = (FilialAdresse) o;
        return Objects.equals(postAnschriftName, that.postAnschriftName) && Objects.equals(strasse, that.strasse) && Objects.equals(plz, that.plz) && Objects.equals(stadtName, that.stadtName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postAnschriftName, strasse, plz, stadtName);
    }

    @Override
    public String toString() {
        return "FilialAdresse{" +
                "postAnschriftName=" + postAnschriftName +
                ", strasse=" + strasse +
                ", plz=" + plz +
                ", stadtName=" + stadtName +
                '}';
    }

    public static Builder Builder() {
        return new Builder();
    }

    public FilialAdresse(Builder b) {
        this.postAnschriftName = b.postAnschriftName;
        this.strasse = b.strasse;
        this.plz = b.plz;
        this.stadtName = b.stadtName;
    }

    public static class Builder {
        private FilialPostAnschriftName postAnschriftName;
        private Strasse strasse;
        private Postleitzahl plz;
        private StadtName stadtName;

        private Builder() { }

        public Builder setPostAnschriftName(@NotNull String postAnschriftName) {
            this.postAnschriftName = new FilialPostAnschriftName(postAnschriftName);

            return this;
        }

        public Builder setStrasse(@NotNull String strasse) {
            this.strasse = new Strasse(strasse);

            return this;
        }

        public Builder setPlz(@NotNull String plz) {
            this.plz = new Postleitzahl(plz);

            return this;
        }

        public Builder setStadtName(@NotNull String stadtName) {
            this.stadtName = new StadtName(stadtName);

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
            if(this.plz == null) {
                throw new ValidationException("FilialAdresse.Builder: PLZ darf nicht 'null' sein!");
            }
            if(this.stadtName == null) {
                throw new ValidationException("FilialAdresse.Builder: StadtName darf nicht 'null' sein!");
            }

            return new FilialAdresse(this);
        }
    }
}
