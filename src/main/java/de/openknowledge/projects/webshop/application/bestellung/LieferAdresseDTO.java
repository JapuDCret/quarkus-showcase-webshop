package de.openknowledge.projects.webshop.application.bestellung;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

public class LieferAdresseDTO implements Serializable {
    private static final long serialVersionUID = 7479007705954840655L;

    @NotNull
    @Schema(required = true, example = "Max")
    private String vorName;

    @NotNull
    @Schema(required = true, example = "Mustermann")
    private String nachName;

    @NotNull
    @Schema(required = true, example = "II. Hagen 7")
    private String strasse;

    @NotNull
    @Schema(required = true, example = "45127")
    private String plz;

    @NotNull
    @Schema(required = true, example = "Essen")
    private String ort;

    protected LieferAdresseDTO() {
        super();
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getVorName() {
        return vorName;
    }

    public void setVorName(String vorName) {
        this.vorName = vorName;
    }

    public String getNachName() {
        return nachName;
    }

    public void setNachName(String nachName) {
        this.nachName = nachName;
    }

    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LieferAdresseDTO that = (LieferAdresseDTO) o;
        return Objects.equals(vorName, that.vorName) && Objects.equals(nachName, that.nachName) && Objects.equals(strasse, that.strasse) && Objects.equals(plz, that.plz) && Objects.equals(ort, that.ort);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vorName, nachName, strasse, plz, ort);
    }

    @Override
    public String toString() {
        return "LieferAdresseDTO{" +
                "vorName='" + vorName + '\'' +
                ", nachName='" + nachName + '\'' +
                ", strasse='" + strasse + '\'' +
                ", plz='" + plz + '\'' +
                ", ort='" + ort + '\'' +
                '}';
    }
}
