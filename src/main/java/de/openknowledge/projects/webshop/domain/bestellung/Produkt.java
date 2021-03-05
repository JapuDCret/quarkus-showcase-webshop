package de.openknowledge.projects.webshop.domain.bestellung;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * ValueObject "Produkt"
 */
public class Produkt implements Comparable<Produkt>, Serializable {
    private static final long serialVersionUID = -3267302111849257811L;

    private final String name;

    private final BigDecimal preis;

    public Produkt(@NotNull String name, @NotNull BigDecimal preis) {
        this.name = name;
        this.preis = preis;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPreis() {
        return preis;
    }

    @Override
    public int compareTo(Produkt o) {
        int nameComparison = this.name.compareTo(o.name);

        // is name equal? then also check the preis
        if(nameComparison == 0) {
            return this.preis.compareTo(o.preis);
        } else {
            return nameComparison;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produkt produkt = (Produkt) o;
        return Objects.equals(this.name, produkt.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Produkt{" +
                "name='" + name + '\'' +
                ", preis=" + preis +
                '}';
    }
}
