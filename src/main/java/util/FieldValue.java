package main.java.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class FieldValue {

    private Field field;
    private Node node;
    private List<ObjectInfo> list = new ArrayList<>();

    public FieldValue(Field field, Node node, List<ObjectInfo> list) {
        this.field = field;
        this.node = node;
        this.list = list;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public FieldValue(Field field, Node node) {
        this.field = field;
        this.node = node;
    }

    public void setField(Object o) throws IllegalAccessException, NumberFormatException {
        var type = field.getType().getTypeName();
        if (type.equals("boolean") || type.equals("java.lang.Boolean")) {
            CheckBox checkBox = (CheckBox) node;
            if (type.equals("boolean"))
                field.setBoolean(o, checkBox.isSelected());
            else
                field.set(o, Boolean.valueOf(checkBox.isSelected()));
        } else if (field.getType().isEnum()) {
            ComboBox<String> comboBox = (ComboBox) node;
            field.set(o, Enum.valueOf((Class<Enum>) field.getType(), comboBox.getValue()));
        } else if (field.getType().isPrimitive()
                    || type.equals("java.lang.Integer")
                    || type.equals("java.lang.Long")
                    || type.equals("java.lang.Byte")
                    || type.equals("java.lang.String")
                    || type.equals("java.lang.Float")
                    || type.equals("java.lang.Double")
                    || type.equals("java.lang.Character")
                    || type.equals("java.lang.Short")) {
                TextField textField = (TextField) node;
                if (type.equals("int"))
                    field.setInt(o, Integer.parseInt(textField.getText()));
                if (type.equals("long"))
                    field.setLong(o, Long.parseLong(textField.getText()));
                if (type.equals("byte"))
                    field.setByte(o, Byte.parseByte(textField.getText()));
                if (type.equals("float"))
                    field.setFloat(o, Float.parseFloat(textField.getText()));
                if (type.equals("double"))
                    field.setDouble(o, Double.parseDouble(textField.getText()));
                if (type.equals("char"))
                    field.setChar(o, textField.getText().charAt(0));
                if (type.equals("short"))
                    field.setShort(o, Short.parseShort(textField.getText()));
                if (type.equals("java.lang.Integer"))
                    field.set(o, Integer.parseInt(textField.getText()));
                if (type.equals("java.lang.Long"))
                    field.set(o, Long.parseLong(textField.getText()));
                if (type.equals("java.lang.Byte"))
                    field.set(o, Byte.parseByte(textField.getText()));
                if (type.equals("java.lang.Float"))
                    field.set(o, Float.parseFloat(textField.getText()));
                if (type.equals("java.lang.Double"))
                    field.set(o, Double.parseDouble(textField.getText()));
                if (type.equals("java.lang.Character"))
                    field.set(o, textField.getText().charAt(0));
                if (type.equals("java.lang.Short"))
                    field.set(o, Short.parseShort(textField.getText()));
                if (type.equals("java.lang.String"))
                    field.set(o, textField.getText());
            } else {
                ComboBox<String> comboBox = (ComboBox<String>) node;
                for (var el: list)
                    if (el.getObjectName().equals(comboBox.getValue())) {
                        field.set(o, el.getObject());
                        break;
                    }
            }
    }

    public void setNode(Object o) throws IllegalAccessException {
        var type = field.getType().getTypeName();
        if (type.equals("boolean") || type.equals("java.lang.Boolean")) {
            CheckBox checkBox = (CheckBox) node;
            checkBox.setSelected((Boolean) field.get(o));
        } else if (field.getType().isEnum()) {
            ComboBox<String> comboBox = (ComboBox) node;
            comboBox.setValue(field.getType().cast(field.get(o)).toString());
        } else if (field.getType().isPrimitive()
                || type.equals("java.lang.Integer")
                || type.equals("java.lang.Long")
                || type.equals("java.lang.Byte")
                || type.equals("java.lang.String")
                || type.equals("java.lang.Float")
                || type.equals("java.lang.Double")
                || type.equals("java.lang.Character")
                || type.equals("java.lang.Short")) {
            TextField textField = (TextField) node;
            if (type.equals("int"))
                textField.setText(String.valueOf((Integer) field.getInt(o)));
            if (type.equals("long"))
                textField.setText(String.valueOf((Long) field.getLong(o)));
            if (type.equals("byte"))
                textField.setText(String.valueOf((Byte) field.getByte(o)));
            if (type.equals("float"))
                textField.setText(String.valueOf((Float) field.getFloat(o)));
            if (type.equals("double"))
                textField.setText(String.valueOf((Double) field.getDouble(o)));
            if (type.equals("char"))
                textField.setText(String.valueOf((Character) field.getChar(o)));
            if (type.equals("short"))
                textField.setText(String.valueOf((Short) field.getShort(o)));
            if (type.equals("java.lang.Integer"))
                textField.setText(Integer.toString((Integer) field.get(o)));
            if (type.equals("java.lang.Long"))
                textField.setText(Long.toString((Long) field.get(o)));
            if (type.equals("java.lang.Byte"))
                textField.setText(Byte.toString((Byte) field.get(o)));
            if (type.equals("java.lang.Float"))
                textField.setText(Float.toString((Float) field.get(o)));
            if (type.equals("java.lang.Double"))
                textField.setText(Double.toString((Double) field.get(o)));
            if (type.equals("java.lang.Character"))
                textField.setText(Character.toString((Character) field.get(o)));
            if (type.equals("java.lang.Short"))
                textField.setText(Short.toString((Short) field.get(o)));
            if (type.equals("java.lang.String"))
                textField.setText((String) field.get(o));
        } else {
            ComboBox<String> comboBox = (ComboBox<String>) node;
            ObservableList<String> objectInfos = FXCollections.observableArrayList();
            Class<?> clazz = field.getType();
            for (var el: list)
                if (el.getClassName().equals(clazz))
                    objectInfos.add(el.getObjectName());
            comboBox.setItems(objectInfos);
            if (o == null)
                System.out.println("Объект равен НУЛЛ");
            System.out.println(list);
            System.out.println(field);
            for (var el: list)
                if (field.get(o) != null && field.get(o).equals(el.getObject())) {
                    comboBox.setValue(el.getObjectName());
                    break;
                }
        }
    }
}
