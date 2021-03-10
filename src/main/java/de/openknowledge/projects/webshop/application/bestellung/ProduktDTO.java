package de.openknowledge.projects.webshop.application.bestellung;

import de.openknowledge.projects.webshop.domain.bestellung.Produkt;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

public class ProduktDTO implements Serializable {
    private static final long serialVersionUID = -7404733921387433476L;

    private final String name;

    private final double preis;

    public ProduktDTO(@NotNull String name, double preis) {
        this.name = name;
        this.preis = preis;
    }

    public String getName() {
        return name;
    }

    public double getPreis() {
        return preis;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProduktDTO that = (ProduktDTO) o;
        return Double.compare(that.preis, preis) == 0 && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, preis);
    }

    @Override
    public String toString() {
        return "ProduktDTO{" +
                "name='" + name + '\'' +
                ", preis=" + preis +
                '}';
    }

    public static ProduktDTO of(Produkt produkt) {
        String name = produkt.getName();
        double preis = produkt.getPreis().doubleValue();

        return new ProduktDTO(name, preis);
    }
}
