package de.openknowledge.projects.webshop.domain.bestellung.produkt;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * ValueObject "ProduktAuswahl"
 */
public class ProduktAuswahl implements Comparable<ProduktAuswahl>, Serializable {
    private static final long serialVersionUID = 3065787133104541368L;

    private final Produkt produkt;

    private final int anzahl;

    public ProduktAuswahl(@NotNull Produkt produkt, @NotNull int anzahl) {
        this.produkt = produkt;
        this.anzahl = anzahl;
    }

    public Produkt getProdukt() {
        return produkt;
    }

    public int getAnzahl() {
        return anzahl;
    }

    @Override
    public int compareTo(ProduktAuswahl o) {
        int produktComparison = this.produkt.compareTo(o.produkt);

        // is produkt equal? then also check the preis
        if(produktComparison == 0) {
            return Integer.compare(this.anzahl, o.anzahl);
        } else {
            return produktComparison;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProduktAuswahl that = (ProduktAuswahl) o;
        return anzahl == that.anzahl && Objects.equals(this.produkt, that.produkt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(produkt, anzahl);
    }

    @Override
    public String toString() {
        return "ProduktAuswahl{" +
                "produkt=" + produkt +
                ", anzahl=" + anzahl +
                '}';
    }
}
