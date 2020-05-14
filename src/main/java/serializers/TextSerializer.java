package main.java.serializers;

import jdk.jfr.Description;
import main.java.util.Serializer;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Description("Текстовый сериализатор")
public class TextSerializer implements Serializer, Serializable {

    private Integer i = 0;

    private String serializeObject(Object object) {
        String result = "^" + object.getClass().getName();
        List<Field> fields = getFields(object.getClass());
        try {
            for (var field : fields) {
                field.setAccessible(true);
                Class<?> fieldType = field.getType();
                if (fieldType.isPrimitive()) {
                    result += "\r\n" + "-" + field.get(object);
                } else if (String.class.equals(fieldType)) {
                    result += "\r\n" + "!" + field.get(object);
                } else if (fieldType.isEnum()) {
                    result += "\r\n" + "?" + field.get(object);
                } else if (Integer.class.equals(fieldType)
                            || Short.class.equals(fieldType)
                            || Boolean.class.equals(fieldType)
                            || Float.class.equals(fieldType)
                            || Byte.class.equals(fieldType)
                            || Character.class.equals(fieldType)
                            || Long.class.equals(fieldType)
                            || Double.class.equals(fieldType)) {
                    result += "\r\n" + "+" + field.get(object);
                } else if (field.get(object) != null){
                    result += "\r\n" + serializeObject(field.get(object));
                } else {
                    result += "\r\n" + "null";
                }
            }
        } catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }



    @Override
    @Description(".txt")
    public void serialize(File file, List<Object> objectList) {
        try (FileWriter writer = new FileWriter(file)) {
            for (var o : objectList) {
                writer.write(serializeObject(o) + "\r\n");
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Object parseField(String value, Class<?> field) {
        if (byte.class.equals(field)) {
            return Byte.parseByte(value);
        } else if (short.class.equals(field)) {
            return Short.parseShort(value);
        } else if (int.class.equals(field)) {
            return Integer.parseInt(value);
        } else if (long.class.equals(field)) {
            return Long.parseLong(value);
        } else if (float.class.equals(field)) {
            return Float.parseFloat(value);
        } else if (double.class.equals(field)) {
            return Double.parseDouble(value);
        } else if (char.class.equals(field)) {
            return value.charAt(0);
        } else if (boolean.class.equals(field)) {
            return Boolean.valueOf(value);
        } else if (Byte.class.equals(field)) {
            return Byte.valueOf(value);
        } else if (Short.class.equals(field)) {
            return Short.valueOf(value);
        } else if (Integer.class.equals(field)) {
            return Integer.valueOf(value);
        } else if (Long.class.equals(field)) {
            return Long.valueOf(value);
        } else if (Float.class.equals(field)) {
            return Float.valueOf(value);
        } else if (Double.class.equals(field)) {
            return Double.valueOf(value);
        } else if (Character.class.equals(field)) {
            return value.charAt(0);
        } else if (Boolean.class.equals(field)) {
            return Boolean.valueOf(value);
        } else {
            return null;
        }
    }

    private Object deserializeObject(List<String> list) {
        Object o = null;
        try {
            Class<?> clazz = Class.forName(list.get(i).substring(1));
            o = clazz.getDeclaredConstructors()[0].newInstance();
            List<Field> fields = getFields(clazz);
            for (var field : fields) {
                i++;
                String line = list.get(i);
                field.setAccessible(true);
                if (field.getType().isPrimitive()) {
                    field.set(o, parseField(line.substring(1), field.getType()));
                } else if (line.charAt(0) == '!') {
                    field.set(o, line.substring(1));
                } else if (field.getType().isEnum()) {
                    field.set(o, Enum.valueOf((Class<Enum>) field.getType(), line.substring(1)));
                } else if (line.charAt(0) == '+') {
                    field.set(o, parseField(line.substring(1), field.getType()));
                } else if (line.charAt(0) == 'n') {
                    field.set(o, null);
                } else if (line.charAt(0) == '^') {
                    field.set(o, deserializeObject(list));
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return o;
    }

    @Override
    public List<Object> deserialize(File file) {
        List<String> list = null;
        List<Object> result = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            list = new ArrayList<>();
            String str = br.readLine();
            while (str != null) {
                list.add(str);
                str = br.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        if (list != null) {
            Class<?> clazz = null;
            i = 0;
            while (i != list.size()) {
                result.add(deserializeObject(list));
                i++;
            }
        }
        return result;
    }

    public List<Field> getFields(Class<?> c) {
        List<Field> list = new ArrayList<>();
        while (c != null) {
            list.addAll(Arrays.asList(c.getDeclaredFields()));
            c = c.getSuperclass();
        }
        return list;
    }

}
