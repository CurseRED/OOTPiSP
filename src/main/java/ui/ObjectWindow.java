package main.java.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.LinkedList;

public class ObjectWindow {

    final Stage stage = new Stage();
    final Button okButton = new Button();
    final Button cancelButton = new Button();

    private Class<?> className;

    public ObjectWindow(Stage primaryStage, Class<?> className) {
        prepareStage(primaryStage);
        addListeners();
        stage.setScene(prepareScene());
        this.className = className;
    }

    public void showObjectWindow() {
        stage.show();
    }

    private void prepareStage(Stage primaryStage) {
        stage.getIcons().add(new Image("/main/resources/image/logo.png"));
        stage.setTitle("Редактор объекта");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(primaryStage);
    }

    private Scene prepareScene() {
        Scene scene = new Scene(getRootFromClass(className));
        return scene;
    }

    private void addListeners() {
        okButton.setOnAction((event) -> {

        });
        cancelButton.setOnAction((event) -> {

        });
    }

    private VBox getRootFromClass(Class<?> className) {
        VBox root = new VBox(8);
        root.setPadding(new Insets(8, 8, 8, 8));
        // Creating object name field
        TextField objectName = new TextField();
        HBox objectNameHBox = new HBox(8);
        objectNameHBox.setAlignment(Pos.CENTER_LEFT);
        objectNameHBox.getChildren().addAll(new Label("Имя объекта:"), objectName);
        root.getChildren().add(objectNameHBox);
        for (Field field: className.getDeclaredFields()) {
            HBox objectFieldHBox = new HBox(8);
            field.setAccessible(true);
            objectFieldHBox.getChildren().addAll(new Label(field.getName(), getNodeFromFieldType(field.getType())));
        }
        HBox controlPanel = new HBox(8);
        controlPanel.getChildren().addAll(okButton, cancelButton);
        return root;
    }

    private Node getNodeFromFieldType(Class<?> fieldType) {
        String typeName = fieldType.getTypeName();
        if (typeName.equals("int")
                || typeName.equals("long")
                || typeName.equals("byte")
                || typeName.equals("String")
                || typeName.equals("float")
                || typeName.equals("double")
                || typeName.equals("char")
                || typeName.equals("short"))
            return new TextField();
        else {
            return new Button("Открыть");
        }
    }
}