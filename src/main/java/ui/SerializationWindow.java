package main.java.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.java.util.SerializationMode;

import java.io.File;

public class SerializationWindow {

    private Stage stage = new Stage();
    private ToggleGroup toggleGroup = new ToggleGroup();
    private RadioButton binaryRadioButton = new RadioButton("Бинарная");
    private RadioButton xmlRadioButton = new RadioButton("XML");
    private RadioButton textRadioButton = new RadioButton("Текстовая");
    private Button chooseButton = new Button("Выбрать файл");
    private FileChooser fileChooser = new FileChooser();
    private File chosenFile;
    private Label label = new Label("Файл не выбран");
    private Button okButton = new Button("Ок");
    private Button cancelButton = new Button("Отменить");
    private SerializationMode mode;

    public SerializationWindow(Stage primaryStage, SerializationMode mode) {
        this.mode = mode;
        prepareStage(primaryStage);
        addListeners();
        stage.setScene(prepareScene());
    }

    public void showSerializationWindow() {
        stage.show();
    }

    private void prepareStage(Stage primaryStage) {
        stage.setTitle("Сериализация");
        stage.getIcons().add(new Image("/main/resources/image/logo.png"));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(primaryStage);
        stage.setResizable(false);
    }

    private Scene prepareScene() {
        VBox vBox = new VBox(8);
        vBox.setPadding(new Insets(8, 8, 8, 8));
        vBox.getChildren().addAll(binaryRadioButton, xmlRadioButton, textRadioButton);
        binaryRadioButton.setToggleGroup(toggleGroup);
        xmlRadioButton.setToggleGroup(toggleGroup);
        textRadioButton.setToggleGroup(toggleGroup);
        binaryRadioButton.fire();
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

    private void addListeners() {
        okButton.setOnAction(event -> {
            if (mode == SerializationMode.LOAD) {

            } else {

            }
            stage.close();
        });
        cancelButton.setOnAction(event -> {
            stage.close();
        });
        chooseButton.setOnAction(event -> {
            chosenFile = fileChooser.showOpenDialog(stage);
            if (chosenFile != null) {
                label.setText(chosenFile.getName());
            }
        });
    }
}
