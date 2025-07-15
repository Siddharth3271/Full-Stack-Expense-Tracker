package org.example.expensetrackerclient;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.expensetrackerclient.utils.ViewNavigator;
import org.example.expensetrackerclient.views.LoginClass;

public class main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Expense-Tracker");
        ViewNavigator.setMainStage(stage);
        new LoginClass().show();
    }
}
