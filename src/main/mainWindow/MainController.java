package main.mainWindow;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import main.objectWindow.Edit;
import main.objectWindow.ObjectController;

import java.io.File;
import java.io.IOException;

public class MainController {

    @FXML
    private Button addButton;

    @FXML
    private void onClickAddButton(ActionEvent event) throws IOException, ClassNotFoundException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/objectWindow/objectLayout.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (root != null) {
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Добавить объект");
            stage.setScene(scene);
            stage.show();
        }
    }
}
