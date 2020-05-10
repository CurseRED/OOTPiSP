package main.java.util;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public interface Serializer {

    void serialize(File file, List<Object> objectList);
    List<Object> deserialize(File file);
}
