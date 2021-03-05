package de.openknowledge.projects.webshop.domain.bewertung;

import javax.validation.ValidationException;
import java.io.Serializable;
import java.util.Objects;

/**
 * ValueObject "Wertung"
 */
public class Wertung  implements Serializable {
    private static final long serialVersionUID = -4721760141423951711L;

    private final int wert;

    public Wertung(int wert) {
        if(wert < 1) {
            throw new ValidationException("Wertung: Wert darf nicht kleiner als 1 sein!");
        }
        if(wert > 5) {
            throw new ValidationException("Wertung: Wert darf nicht größer als 5 sein!");
        }
        this.wert = wert;
    }

    public int getWert() {
        return wert;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wertung wertung = (Wertung) o;
        return wert == wertung.wert;
    }

    @Override
    public int hashCode() {
        return Objects.hash(wert);
    }

    @Override
    public String toString() {
        return "Wertung{" +
                "wert=" + wert +
                '}';
    }
}
