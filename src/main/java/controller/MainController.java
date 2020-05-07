package main.java.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import jdk.jfr.Description;
import main.java.ui.ObjectWindow;
import main.java.ui.SerializationWindow;
import main.java.util.ObjectInfo;
import main.java.util.SerializationMode;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainController {

    private HashMap<String, Class<?>> hashMap = new HashMap<>();
    private List<ObjectInfo> objectList = new ArrayList<>();
    private ObservableList<ObjectInfo> tableList = FXCollections.observableArrayList(objectList);
    private Stage stage;

    @FXML
    private Button addButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button loadButton;
    @FXML
    private Button saveButton;
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private TableView<ObjectInfo> tableView;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void onClickAddButton(ActionEvent e) {
        addObject();
    }

    @FXML
    private void onClickEditButton(ActionEvent e) {
        editObject();
    }

    @FXML
    private void onClickDeleteButton(ActionEvent e) {
        deleteObject();
    }

    @FXML
    private void onClickLoadButton(ActionEvent e) {
        SerializationWindow serializationWindow = new SerializationWindow(stage, SerializationMode.LOAD);
        serializationWindow.showSerializationWindow();
    }

    @FXML
    private void onClickSaveButton(ActionEvent e) {
        SerializationWindow serializationWindow = new SerializationWindow(stage, SerializationMode.SAVE);
        serializationWindow.showSerializationWindow();
    }

    public void initForm() {
        initTableView();
        initComboBox();
    }

    private void initTableView() {
        tableView.setItems(tableList);
        TableColumn<ObjectInfo, String> objectNameColumn = new TableColumn<>("Имя объекта");
        objectNameColumn.setCellValueFactory(new PropertyValueFactory<>("objectName"));
        TableColumn<ObjectInfo, String> classNameColumn = new TableColumn<>("Имя класса");
        classNameColumn.setCellValueFactory(new PropertyValueFactory<>("classNameRus"));
        tableView.getColumns().addAll(objectNameColumn, classNameColumn);
    }

    private void initComboBox() {
        ArrayList<String> classNames = null;
        try {
            classNames = getAllClassesFromPackage(new File("src/main/java/classes"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (classNames != null) {
            ObservableList<String> list = FXCollections.observableArrayList(classNames);
            comboBox.setItems(list);
            if (list.size() > 0)
                comboBox.setValue(list.get(0));
        }
    }

    private ArrayList<String> getAllClassesFromPackage(File classes) throws ClassNotFoundException {
        var files = classes.listFiles();
        ArrayList<String> list = new ArrayList<>();
        if (files != null)
            for (var file: files) {
                String packageName = "main.java.classes.";
                String className = file.getName().substring(0, file.getName().indexOf("."));
                System.out.println(packageName + className);
                Class<?> clazz = Class.forName(packageName + className);
                if (!(clazz.isEnum() || clazz.isInterface())) {
                    className = clazz.getAnnotation(Description.class).value();
                    list.add(className);
                }
                hashMap.put(className, clazz);
            }
        return list;
    }

    public void addObject() {
        ObjectWindow objectWindow = new ObjectWindow(stage, hashMap.get(comboBox.getValue()), this);
        objectWindow.showObjectWindow();
    }

    private void deleteObject() {
        tableList.remove(tableView.getSelectionModel().getSelectedItem());
    }

    private void editObject() {
        ObjectWindow objectWindow = new ObjectWindow(stage, tableView.getSelectionModel().getSelectedItem(), this);
        objectWindow.showObjectWindow();
    }

    public void addObjectToList(ObjectInfo objectInfo) {
        tableList.add(objectInfo);
    }

    public HashMap<String, Class<?>> getHashMap() {
        return hashMap;
    }

    public List<ObjectInfo> getList() {
        return objectList;
    }
}
