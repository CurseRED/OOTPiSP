package main.java.serializers;

import jdk.jfr.Description;
import main.java.util.Serializer;

import java.io.File;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Description("Текстовый сериализатор")
public class TextSerializer implements Serializer, Serializable {

    @Override
    @Description("txt")
    public void serialize(File file, List<Object> objectList) {

    }

    @Override
    public List<Object> deserialize(File file) {
        return null;
    }
}
