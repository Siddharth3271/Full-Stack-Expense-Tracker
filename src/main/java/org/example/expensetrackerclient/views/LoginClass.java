package org.example.expensetrackerclient.views;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.example.expensetrackerclient.utils.ViewNavigator;

/**/
public class LoginClass {
    private Label expenseTrackerLabel=new Label("Expense Tracker");

    private TextField userNameField=new TextField();
    private PasswordField passwordField=new PasswordField();

    private Button loginButton =new Button("Login");
    private Label signUpLabel =new Label("Don't have a Account? Click here");

    public void show(){
        Scene scene=createScene();
        ViewNavigator.switchViews(scene);
    }

    private Scene createScene(){
        VBox mainContainer=new VBox();
        VBox loginFormBox=new VBox();
        loginFormBox.getChildren().addAll(userNameField,passwordField,loginButton,signUpLabel);

        mainContainer.getChildren().addAll(expenseTrackerLabel,loginFormBox);

        return new Scene(mainContainer,900,500);
    }


}
