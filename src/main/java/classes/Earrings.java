package main.java.classes;

import java.io.Serializable;

public class Earrings extends Jewel implements Serializable {

    private float size;

    public Earrings(String name, Material material, Gem gem, int price, String owner, float size) {
        super(name, material, gem, price, owner);
        this.size = size;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }
}
