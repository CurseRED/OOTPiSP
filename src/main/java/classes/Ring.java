package main.java.classes;

import java.io.Serializable;

public class Ring extends Jewel implements Serializable {

    private float radius;

    public Ring(String name, Material material, Gem gem, int price, String owner, float radius) {
        super(name, material, gem, price, owner);
        this.radius = radius;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }
}
