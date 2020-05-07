package main.java.classes;

import jdk.jfr.Description;

@Description("Несколько украшений")
public class SeveralJewels {

    @Description("Сережки")
    private Earrings earrings;
    @Description("Ожерелье")
    private Necklace necklace;
    @Description("Кольцо")
    private Ring ring;

    @Description("Много")
    private Boolean isMany;
    @Description("Мало")
    private boolean isBit;
}
