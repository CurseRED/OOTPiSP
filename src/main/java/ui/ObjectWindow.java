package main.java.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jdk.jfr.Description;
import main.java.controller.MainController;
import main.java.util.FieldValue;
import main.java.util.ObjectInfo;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedList;

public class ObjectWindow {

    final Stage stage = new Stage();
    final Button okButton = new Button("Ок");
    final Button cancelButton = new Button("Отмена");
    private TextField objectName;

    private ArrayList<FieldValue> list = new ArrayList<>();
    private Class<?> className;
    private ObjectInfo objectInfo;
    private MainController controller;

    ArrayList<Field> fields = new ArrayList<>();
    LinkedList<Node> nodes = new LinkedList<>();

    private boolean flag = false;

    public ObjectWindow(Stage primaryStage, Class<?> className, MainController controller) {
        this.className = className;
        this.controller = controller;
        prepareStage(primaryStage);
        addListeners();
        stage.setScene(prepareScene());
    }

    public ObjectWindow(Stage primaryStage, ObjectInfo objectInfo, MainController controller) {
        flag = true;
        this.className = objectInfo.getClassName();
        this.objectInfo = objectInfo;
        this.controller = controller;
        prepareStage(primaryStage);
        addListeners();
        stage.setScene(prepareScene());
        for (var el : list) {
            try {
                el.setNode(objectInfo.getObject());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        objectName.setText(objectInfo.getObjectName());
    }

    public ObjectInfo getObjectInfo() {
        return objectInfo;
    }

    public void showObjectWindow() {
        stage.show();
    }

    private void prepareStage(Stage primaryStage) {
        stage.getIcons().add(new Image("/main/resources/image/logo.png"));
        stage.setTitle("Редактор объекта");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(primaryStage);
        stage.setResizable(false);
    }

    private Scene prepareScene() {
        Scene scene = new Scene(getRootFromClass(className));
        return scene;
    }

    private void addListeners() {
        okButton.setOnAction((event) -> {
            addNewObject();
        });
        cancelButton.setOnAction((event) -> {
            stage.close();
        });
    }

    private void addNewObject() {
        Object o = null;
        try {
            o = className.getDeclaredConstructors()[0].newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        if (o != null && !(objectName.getText().length() == 0)) {
            System.out.println("Объект создан!");
            try {
                for (var el : list)
                    el.setField(o);
                if (!flag) {
                    System.out.println("Поля проинициализированы!");
                    objectInfo = new ObjectInfo(objectName.getText(), className, o);
                    controller.addObjectToList(objectInfo);
                    System.out.println(objectInfo.getObjectName());
                } else {
                    System.out.println("Поля проинициализированы!");
                    objectInfo.setObjectName(objectName.getText());
                    objectInfo.setObject(o);
                    System.out.println(objectInfo.getObjectName());
                    System.out.println("Объект изменен!");
                }
                controller.updateList();
                stage.close();
            } catch (IllegalAccessException | NumberFormatException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка ввода!");
                alert.setHeaderText(null);
                alert.setContentText("Данные введены не верно!");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка ввода!");
            alert.setHeaderText(null);
            alert.setContentText("Данные введены не верно!");
            alert.showAndWait();
        }
    }

    private VBox getRootFromClass(Class<?> className) {
        nodes.clear();
        VBox root = new VBox(8);
        root.setPadding(new Insets(8, 8, 8, 8));
        objectName = new TextField();
        HBox objectNameHBox = new HBox(8);
        objectNameHBox.setAlignment(Pos.CENTER_LEFT);
        objectNameHBox.getChildren().addAll(new Label("Имя объекта:"), objectName);
        root.getChildren().add(objectNameHBox);
        while (className != null) {
            for (Field field : className.getDeclaredFields()) {
                HBox objectFieldHBox = new HBox(8);
                objectFieldHBox.setAlignment(Pos.CENTER_LEFT);
                field.setAccessible(true);
                FieldValue fieldValue = getNodeFromFieldType(field);
                list.add(fieldValue);
                if (field.getAnnotation(Description.class) != null)
                    objectFieldHBox.getChildren().addAll(new Label(field.getAnnotation(Description.class).value() + ":"), fieldValue.getNode());
                else
                    objectFieldHBox.getChildren().addAll(new Label(field.getName() + ": "), fieldValue.getNode());
                root.getChildren().add(objectFieldHBox);
            }
            className = className.getSuperclass();
        }
        HBox controlPanel = new HBox(8);
        controlPanel.setAlignment(Pos.CENTER_RIGHT);
        controlPanel.getChildren().addAll(okButton, cancelButton);
        root.getChildren().add(controlPanel);
        return root;
    }

    private FieldValue getNodeFromFieldType(Field field) {
        Class<?> fieldType = field.getType();
        String typeName = fieldType.getTypeName();
        if (typeName.equals("boolean") || typeName.equals("java.lang.Boolean")) {
            return new FieldValue(field, new CheckBox());
        } else if (fieldType.isEnum()) {
            ComboBox<String> comboBox = new ComboBox<>();
            for (Object obj : fieldType.getEnumConstants()) {
                comboBox.getItems().add(obj.toString());
            }
            comboBox.setValue(comboBox.getItems().get(0));
            return new FieldValue(field, comboBox);
        } else if (fieldType.isPrimitive()
                || typeName.equals("java.lang.Integer")
                || typeName.equals("java.lang.Long")
                || typeName.equals("java.lang.Byte")
                || typeName.equals("java.lang.String")
                || typeName.equals("java.lang.Float")
                || typeName.equals("java.lang.Double")
                || typeName.equals("java.lang.Character")
                || typeName.equals("java.lang.Short"))
            return new FieldValue(field, new TextField());
        ComboBox<String> comboBox = new ComboBox<>();
        ObservableList<String> objectInfos = FXCollections.observableArrayList();
        for (var el : controller.getList())
            if (el.getClassName().equals(field.getType()))
                objectInfos.add(el.getObjectName());
        comboBox.setItems(objectInfos);
        System.out.println(field);
        return new FieldValue(field, comboBox, controller.getList());
    }
}