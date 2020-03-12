package com.company.mainWindow;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    @FXML
    private Button addButton;

    @FXML
    private void onClickAddButton(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/company/objectWindow/objectLayout.fxml"));
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
