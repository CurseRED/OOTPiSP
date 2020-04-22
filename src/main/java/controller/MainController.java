package main.java.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.java.ui.ObjectWindow;
import main.java.utils.ObjectInfo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class MainController {

    private static HashMap<String, Class<?>> hashMap = new HashMap<>();
    private static ObservableList<ObjectInfo> objectInfos = FXCollections.observableArrayList();
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

    }

    @FXML
    private void onClickDeleteButton(ActionEvent e) {
        deleteObject();
    }

    @FXML
    private void onClickLoadButton(ActionEvent e) {
        objectInfos.add(new ObjectInfo("ruby", "Gem"));
    }

    @FXML
    private void onClickSaveButton(ActionEvent e) {

    }

    public void initForm() {
        initTableView();
        initComboBox();
    }

    private void initTableView() {
        tableView.setItems(objectInfos);
        TableColumn<ObjectInfo, String> objectNameColumn = new TableColumn<>("Имя объекта");
        objectNameColumn.setCellValueFactory(new PropertyValueFactory<>("objectName"));
        TableColumn<ObjectInfo, String> classNameColumn = new TableColumn<>("Имя класса");
        classNameColumn.setCellValueFactory(new PropertyValueFactory<>("className"));
        tableView.getColumns().addAll(objectNameColumn, classNameColumn);
    }

    private void initComboBox() {
        ArrayList<String> classNames = null;
        try {
            classNames = getAllClassesFromPackage(new File("src/main/java/config/config.txt"));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (classNames != null) {
            ObservableList<String> list = FXCollections.observableArrayList(classNames);
            comboBox.setItems(list);
            if (list.size() > 0)
                comboBox.setValue(list.get(0));
        }
    }

    private static ArrayList<String> getAllClassesFromPackage(File config) throws IOException, ClassNotFoundException {
        BufferedReader br = new BufferedReader(new FileReader(config));
        String packageName = "main.java.classes.";
        String line;
        ArrayList<String> list = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            list.add(line);
        }
        for (String className: list) {
            hashMap.put(className, Class.forName(packageName + className));
        }
        return list;
    }

    public void addObject() {
        ObjectWindow objectWindow = new ObjectWindow(stage, hashMap.get(comboBox.getValue()));
        objectWindow.showObjectWindow();
    }

    private void deleteObject() {
        objectInfos.remove(tableView.getSelectionModel().getSelectedItem());
    }


}
