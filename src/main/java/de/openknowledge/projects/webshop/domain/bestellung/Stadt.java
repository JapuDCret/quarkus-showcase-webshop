package de.openknowledge.projects.webshop.domain.bestellung;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

public class Stadt implements Serializable  {
    private static final long serialVersionUID = -8592943921371696984L;

    public final int plz;
    
    public final String stadtName;

    public Stadt(int plz, @NotNull String stadtName) {
        this.plz = plz;
        this.stadtName = stadtName;
    }

    public int getPlz() {
        return plz;
    }

    public String getStadtName() {
        return stadtName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stadt that = (Stadt) o;
        return plz == that.plz;
    }

    @Override
    public int hashCode() {
        return Objects.hash(plz);
    }

    @Override
    public String toString() {
        return "Postleitzahl{" +
                "plz=" + plz +
                ", stadtName='" + stadtName + '\'' +
                '}';
    }
}
