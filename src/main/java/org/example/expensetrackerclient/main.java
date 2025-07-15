package org.example.expensetrackerclient;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Scene scene=new Scene(new VBox(),100,100);
        stage.setTitle("Hello World");
        stage.setScene(scene);
        stage.show();
    }
}
