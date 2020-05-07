package main.java.util;

import java.io.File;
import java.util.LinkedList;

public interface Serializer {

    void serialize(File file);
    LinkedList<Object> deserialize(File file);
}
