package org.example.expensetrackerclient.utils;

import javafx.scene.Scene;
import javafx.stage.Stage;

/*
* ViewNavigator class is a utility class responsible for managing navigation between different scenes within the same primary stage of a application.
* it provides methods to set the main stage and switch between different views(scenes)
* */
public class ViewNavigator {
    private static Stage mainStage;      //we are updating it only within class

    public static void setMainStage(Stage stage) {
        mainStage=stage;
    }

    public static void switchViews(Scene scene){
        if(mainStage!=null){
            mainStage.setScene(scene);
            mainStage.show();
        }
    }
}
