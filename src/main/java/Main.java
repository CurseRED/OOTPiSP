package main.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import main.java.controller.MainController;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/view/mainLayout.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("CRUD");
        primaryStage.getIcons().add(new Image("/main/resources/image/logo.png"));
        MainController controller = loader.getController();
        controller.setStage(primaryStage);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        controller.initForm();
    }

    public static void main(String[] args) {
        launch(args);
    }
}