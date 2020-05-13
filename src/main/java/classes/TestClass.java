package main.java.classes;

import jdk.jfr.Description;

import java.io.Serializable;
import java.util.Objects;

@Description("Тест")
public class TestClass extends Jewel implements Serializable {

    private int i;
    private Integer i1;
    private String str;
    private char c;
    private Character ch;
    private float f;
    private Float fl;
    private long l;
    private Long lo;
    private double d;
    private Double dou;
    private short sh;
    private Short sho;
    private boolean b;
    private Boolean bo;
    private byte byt;
    private Byte by;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TestClass testClass = (TestClass) o;
        return i == testClass.i &&
                c == testClass.c &&
                Float.compare(testClass.f, f) == 0 &&
                l == testClass.l &&
                Double.compare(testClass.d, d) == 0 &&
                sh == testClass.sh &&
                b == testClass.b &&
                byt == testClass.byt &&
                Objects.equals(i1, testClass.i1) &&
                Objects.equals(str, testClass.str) &&
                Objects.equals(ch, testClass.ch) &&
                Objects.equals(fl, testClass.fl) &&
                Objects.equals(lo, testClass.lo) &&
                Objects.equals(dou, testClass.dou) &&
                Objects.equals(sho, testClass.sho) &&
                Objects.equals(bo, testClass.bo) &&
                Objects.equals(by, testClass.by);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), i, i1, str, c, ch, f, fl, l, lo, d, dou, sh, sho, b, bo, byt, by);
    }
}
