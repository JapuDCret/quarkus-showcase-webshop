package de.openknowledge.projects.webshop.application.bestellung;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

public class ProduktAuswahlDTO implements Serializable {
    private static final long serialVersionUID = -7888587115495187952L;

    @NotNull
    private String name;

    @NotNull
    private int anzahl;

    protected ProduktAuswahlDTO() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAnzahl() {
        return anzahl;
    }

    public void setAnzahl(int anzahl) {
        this.anzahl = anzahl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProduktAuswahlDTO that = (ProduktAuswahlDTO) o;
        return anzahl == that.anzahl && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, anzahl);
    }

    @Override
    public String toString() {
        return "ProduktAuswahlDTO{" +
                "name='" + name + '\'' +
                ", anzahl=" + anzahl +
                '}';
    }
}
