package de.openknowledge.projects.webshop.domain.bestellung;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Objects;

/**
 * ValueObject "BestellId"
 */
public class BestellId implements Comparable<BestellId>, Serializable {
    private static final long serialVersionUID = 2473222120737283076L;

    private final BigInteger identifier;

    public BestellId(BigInteger identifier) {
        this.identifier = identifier;
    }

    public BigInteger getIdentifier() {
        return identifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BestellId bestellId = (BestellId) o;
        return Objects.equals(identifier, bestellId.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier);
    }

    @Override
    public int compareTo(BestellId other) {
        return this.identifier.compareTo(other.identifier);
    }
}
