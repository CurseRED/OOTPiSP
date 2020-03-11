package com.company.Classes;

public class Jewel {

    private String name;
    private Material material;
    private Gem gem;
    private int price;
    private String owner;

    public Jewel(String name, Material material, Gem gem, int price, String owner) {
        this.name = name;
        this.material = material;
        this.gem = gem;
        this.price = price;
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Gem getGem() {
        return gem;
    }

    public void setGem(Gem gem) {
        this.gem = gem;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Jewel{" +
                "name='" + name + '\'' +
                ", material=" + material +
                ", gem=" + gem +
                ", price=" + price +
                ", owner='" + owner + '\'' +
                '}';
    }
}
