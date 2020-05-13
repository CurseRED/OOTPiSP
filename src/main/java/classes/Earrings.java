package main.java.classes;

import jdk.jfr.Description;

import java.io.Serializable;
import java.util.Objects;

@Description("Сережки")
public class Earrings extends Jewel implements Serializable {

    @Description("Размер")
    private Float size;

    public Earrings(String name, Material material, Gem gem, Integer price, String owner, Float size) {
        super(name, material, gem, price, owner);
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Earrings earrings = (Earrings) o;
        return Objects.equals(size, earrings.size);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), size);
    }

    public Float getSize() {
        return size;
    }

    public void setSize(Float size) {
        this.size = size;
    }

    public Earrings() {
        super();
    }
}
