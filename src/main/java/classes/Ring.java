package main.java.classes;

import jdk.jfr.Description;

import java.io.Serializable;
import java.util.Objects;

@Description("Кольцо")
public class Ring extends Jewel implements Serializable {

    @Description("Радиус")
    private Float radius;

    public Ring(String name, Material material, Gem gem, Integer price, String owner, Float radius) {
        super(name, material, gem, price, owner);
        this.radius = radius;
    }

    public Float getRadius() {
        return radius;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Ring ring = (Ring) o;
        return Objects.equals(radius, ring.radius);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), radius);
    }

    public void setRadius(Float radius) {
        this.radius = radius;
    }

    public Ring() {

    }
}