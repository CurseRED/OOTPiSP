package main.java.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jdk.jfr.Description;
import main.java.controller.MainController;
import main.java.util.Plugin;
import main.java.util.SerializationMode;
import main.java.util.Serializer;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class SerializationWindow {

    // Key length must be 16 or 32 bytes
    private static final String KEY = "1234567891234567";
    private final Stage stage = new Stage();
    private final ComboBox<String> serializersComboBox = new ComboBox<>();
    private final ComboBox<String> pluginsComboBox = new ComboBox<>();
    private final Button chooseButton = new Button("Выбрать файл");
    private final FileChooser fileChooser = new FileChooser();
    private List<File> chosenFiles;
    private File chosenFile;
    private final Label label = new Label("Файл не выбран");
    private final Button okButton = new Button("Ок");
    private final Button cancelButton = new Button("Отменить");
    private final SerializationMode mode;
    private final List<Class<Serializer>> serializers = new ArrayList<>();
    private final List<Class<Plugin>> plugins = new ArrayList<>();
    private Class<Serializer> chosenSerializer;
    private Class<Plugin> chosenPlugin;
    private final List<String> serNames = new ArrayList<>();
    private final List<String> plugNames = new ArrayList<>();
    private final MainController controller;

    public SerializationWindow(Stage primaryStage, SerializationMode mode, MainController controller) {
        this.mode = mode;
        this.controller = controller;
        loadSerializers();
        loadPlugins();
        prepareStage(primaryStage);
        addListeners();
        stage.setScene(prepareScene());
    }

    private void loadPlugins() {
        System.out.println(KEY.getBytes().length);
        File dir = new File("src/main/java/plugins/");
        String packageName = "main.java.plugins.";
        for (var file : dir.listFiles()) {
            try {
                plugins.add((Class<Plugin>) Class.forName(packageName + file.getName().substring(0, file.getName().indexOf("."))));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadSerializers() {
        File dir = new File("src/main/java/serializers/");
        String packageName = "main.java.serializers.";
        for (var file : dir.listFiles()) {
            try {
                serializers.add((Class<Serializer>) Class.forName(packageName + file.getName().substring(0, file.getName().indexOf("."))));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void showSerializationWindow() {
        stage.show();
    }

    private void prepareStage(Stage primaryStage) {
        if (mode.equals(SerializationMode.SAVE))
            stage.setTitle("Сериализация");
        else
            stage.setTitle("Десериализация");
        stage.getIcons().add(new Image("/main/resources/image/logo.png"));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(primaryStage);
        stage.setResizable(false);
        fileChooser.setInitialDirectory(new File("./"));
    }

    private Scene prepareScene() {
        VBox vBox = new VBox(8);
        vBox.setPadding(new Insets(8, 8, 8, 8));
        initComboBox((List<Class<?>>)(List<?>)serializers, serNames, serializersComboBox);
        initComboBox((List<Class<?>>)(List<?>)plugins, plugNames, pluginsComboBox);
        vBox.getChildren().addAll(new Label("Сериализаторы: "), serializersComboBox, new Label("Плагины:"), pluginsComboBox);
        HBox hBox = new HBox(8);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.getChildren().addAll(chooseButton, label);
        vBox.getChildren().add(hBox);
        hBox = new HBox(8);
        hBox.setAlignment(Pos.CENTER_RIGHT);
        hBox.getChildren().addAll(okButton, cancelButton);
        vBox.getChildren().add(hBox);
        return new Scene(vBox);
    }

    private void initComboBox(List<Class<?>> classes, List<String> names, ComboBox<String> cb) {
        ObservableList<String> list = FXCollections.observableArrayList();
        for (var e : classes)
            if (e.getAnnotation(Description.class) != null)
                names.add(e.getAnnotation(Description.class).value());
            else
                names.add(e.getName());
        list.setAll(names);
        cb.setItems(list);
        if (cb.getItems().size() > 0)
            cb.setValue(cb.getItems().get(0));
    }

    private void addListeners() {
        okButton.setOnAction(event -> {
            try {
                if (mode == SerializationMode.LOAD) {
                    deserialize(chosenSerializer, chosenPlugin);
                } else
                    serialize(chosenSerializer, chosenPlugin);
            } finally {
                if (chosenFile != null || chosenFiles != null)
                    stage.close();
            }
        });
        cancelButton.setOnAction(event -> {
            stage.close();
        });
        chooseButton.setOnAction(event -> {
            fileChooser.getExtensionFilters().clear();
            if (mode.equals(SerializationMode.SAVE)) {
                try {
                    Method method = chosenSerializer.getMethod("serialize", File.class, List.class);
                    if (method.getAnnotation(Description.class) != null)
                        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Serialization files", "*." + method.getAnnotation(Description.class).value()));
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                fileChooser.setTitle("Сериализовать");
                chosenFile = fileChooser.showSaveDialog(stage);
                if (chosenFile != null) {
                    label.setText("Файл выбран!");
                }
            } else {
                try {
                    Method method = chosenSerializer.getMethod("serialize", File.class, List.class);
                    if (method.getAnnotation(Description.class) != null)
                        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Deserialization files", "*." + method.getAnnotation(Description.class).value()));
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                fileChooser.setTitle("Десериализовать");
                chosenFiles = fileChooser.showOpenMultipleDialog(stage);
                if (chosenFiles != null) {
                    label.setText("Файлы выбраны!");
                }
            }
        });
        serializersComboBox.getSelectionModel().selectedItemProperty().addListener((o, oVal, nVal) -> {
            if (nVal != null)
                for (var e : serializers) {
                    if (e.getAnnotation(Description.class) != null) {
                        if (e.getAnnotation(Description.class).value().equals(nVal)) {
                            chosenSerializer = e;
                            break;
                        }
                    } else if (e.getName().equals(nVal)) {
                        chosenSerializer = e;
                        break;
                    }
                }
        });
        pluginsComboBox.getSelectionModel().selectedItemProperty().addListener((o, oVal, nVal) -> {
            if (nVal != null)
                for (var e : plugins) {
                    if (e.getAnnotation(Description.class) != null) {
                        if (e.getAnnotation(Description.class).value().equals(nVal)) {
                            chosenPlugin = e;
                            break;
                        }
                    } else if (e.getName().equals(nVal)) {
                        chosenPlugin = e;
                        break;
                    }
                }
        });
    }

    private void serialize(Class<Serializer> serClass, Class<Plugin> plugClass) {
        if (chosenFile != null)
            try {
                Serializer serializer = serClass.getDeclaredConstructor().newInstance();
                serializer.serialize(chosenFile, controller.getObjects());
                Plugin plugin = plugClass.getDeclaredConstructor().newInstance();
                //plugin.decode(chosenFile, KEY);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка сериализации!");
            alert.setHeaderText(null);
            alert.setContentText("Выберите файл!");
            alert.showAndWait();
        }
    }

    private void deserialize(Class<Serializer> serClass, Class<Plugin> plugClass) {
        if (chosenFiles != null)
            for (var file : chosenFiles) {
                try {
                    Plugin plugin = plugClass.getDeclaredConstructor().newInstance();
                    //plugin.decode(file, KEY);
                    Serializer serializer = serClass.getDeclaredConstructor().newInstance();
                    controller.addObjects(serializer.deserialize(file));
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка сериализации!");
            alert.setHeaderText(null);
            alert.setContentText("Выберите файлы!");
            alert.showAndWait();
        }
    }
}
