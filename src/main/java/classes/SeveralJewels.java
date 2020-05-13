package main.java.classes;

import jdk.jfr.Description;

import java.io.Serializable;
import java.util.Objects;

@Description("Набор украшений")
public class SeveralJewels implements Serializable {

    @Description("Сережки")
    private Earrings earrings;
    @Description("Ожерелье")
    private Necklace necklace;
    @Description("Кольцо")
    private Ring ring;
    @Description("Много")
    private Boolean isMany;

    public Earrings getEarrings() {
        return earrings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeveralJewels that = (SeveralJewels) o;
        return Objects.equals(earrings, that.earrings) &&
                Objects.equals(necklace, that.necklace) &&
                Objects.equals(ring, that.ring) &&
                Objects.equals(isMany, that.isMany);
    }

    @Override
    public int hashCode() {
        return Objects.hash(earrings, necklace, ring, isMany);
    }

    public void setEarrings(Earrings earrings) {
        this.earrings = earrings;
    }

    public Necklace getNecklace() {
        return necklace;
    }

    public void setNecklace(Necklace necklace) {
        this.necklace = necklace;
    }

    public Ring getRing() {
        return ring;
    }

    public void setRing(Ring ring) {
        this.ring = ring;
    }

    public Boolean getMany() {
        return isMany;
    }

    public void setMany(Boolean many) {
        isMany = many;
    }
}
