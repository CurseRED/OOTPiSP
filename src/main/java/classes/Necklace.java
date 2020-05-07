package main.java.classes;

import jdk.jfr.Description;

import java.io.Serializable;

@Description("Ожерелье")
public class Necklace extends Jewel implements Serializable {

    @Description("Длина")
    private float length;

    public Necklace(String name, Material material, Gem gem, int price, String owner, float length) {
        super(name, material, gem, price, owner);
        this.length = length;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public Necklace() {

    }
}
