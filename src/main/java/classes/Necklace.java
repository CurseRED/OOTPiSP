package main.java.classes;

import jdk.jfr.Description;

import java.io.Serializable;
import java.util.Objects;

@Description("Ожерелье")
public class Necklace extends Jewel implements Serializable {

    @Description("Длина")
    private Float length;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Necklace necklace = (Necklace) o;
        return Objects.equals(length, necklace.length);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), length);
    }

    public Necklace(String name, Material material, Gem gem, Integer price, String owner, Float length) {
        super(name, material, gem, price, owner);
        this.length = length;
    }

    public Float getLength() {
        return length;
    }

    public void setLength(Float length) {
        this.length = length;
    }

    public Necklace() {

    }
}
