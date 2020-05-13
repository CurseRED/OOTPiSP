package main.java.classes;

import jdk.jfr.Description;

import java.io.Serializable;
import java.util.Objects;

@Description("Украшение")
public class Jewel implements Serializable {

    @Description("Название")
    protected String name;
    @Description("Тип материала")
    protected Material material;
    @Description("Тип камня")
    protected Gem gem;
    @Description("Цена")
    protected Integer price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Jewel jewel = (Jewel) o;
        return Objects.equals(name, jewel.name) &&
                material == jewel.material &&
                gem == jewel.gem &&
                Objects.equals(price, jewel.price) &&
                Objects.equals(owner, jewel.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, material, gem, price, owner);
    }

    @Description("Имя покупателя")
    protected String owner;

    public Jewel(String name, Material material, Gem gem, Integer price, String owner) {
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
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

    public Jewel() {

    }
}
