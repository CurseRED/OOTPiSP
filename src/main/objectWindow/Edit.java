package main.objectWindow;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.util.LinkedList;

public class Edit extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("objectLayout.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    public static LinkedList<Class<?>> getAllClassesFromPackage(File config) throws IOException, ClassNotFoundException {
        BufferedReader br = new BufferedReader(new FileReader(config));
        String packageName = "main.classes.";
        String line;
        LinkedList<String> list = new LinkedList<>();
        while ((line = br.readLine()) != null) {
            list.add(line);
        }
        LinkedList<Class<?>> classNames = new LinkedList<>();
        for (String className: list) {
            classNames.add(Class.forName(packageName + className));
        }
        return classNames;
    }


}
