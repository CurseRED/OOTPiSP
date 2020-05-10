package main.java.serializers;

import jdk.jfr.Description;
import main.java.util.Serializer;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


@Description("Бинарный сериализатор")
public class BinarySerializer implements Serializer, Serializable {

    @Override
    @Description("bin")
    public void serialize(File file, List<Object> objectList) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            for (var o: objectList)
                oos.writeObject(o);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public List<Object> deserialize(File file) {
        List<Object> list = new ArrayList<>();
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            while (true)
                list.add(ois.readObject());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (ois != null)
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return list;
    }
}
