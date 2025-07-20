package org.example.expensetrackerclient.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.example.expensetrackerclient.Controller.LoginController;
import org.example.expensetrackerclient.utils.Utlities;
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

        //integrating CSS
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        new LoginController(this);
        ViewNavigator.switchViews(scene);
    }

    private Scene createScene(){
        VBox mainContainer=new VBox(80);
        mainContainer.setAlignment(Pos.TOP_CENTER);
        mainContainer.getStyleClass().addAll("main-background");
        VBox loginFormBox=createLoginFormBox();

        expenseTrackerLabel.getStyleClass().addAll("text-white","header");
        VBox.setMargin(expenseTrackerLabel, new Insets(30, 0, 0, 0));

        mainContainer.getChildren().addAll(expenseTrackerLabel,loginFormBox);

        return new Scene(mainContainer, Utlities.APP_WIDTH,Utlities.APP_HEIGHT);
    }


    private VBox createLoginFormBox(){
        VBox loginFormVBox=new VBox(40);

        loginFormVBox.setAlignment(Pos.CENTER);
        userNameField.getStyleClass().addAll("field-background","text-light-gray","text-size-lg","rounded-border");
        userNameField.setPromptText("Enter UserName");
        userNameField.setMaxWidth(320);

        passwordField.getStyleClass().addAll("field-background","text-light-gray","text-size-lg","rounded-border");
        passwordField.setMaxWidth(320);
        passwordField.setPromptText("Enter Password");

        loginButton.getStyleClass().addAll("text-size-lg","bg-light-blue","text-white","text-weight-700","rounded-border");
        loginButton.setMaxWidth(320);

        signUpLabel.getStyleClass().addAll("text-size-md","text-light-gray","text-underline","link-text");
        loginFormVBox.getChildren().addAll(userNameField,passwordField,loginButton,signUpLabel);

        return loginFormVBox;
    }

    public Label getExpenseTrackerLabel() {
        return expenseTrackerLabel;
    }

    public void setExpenseTrackerLabel(Label expenseTrackerLabel) {
        this.expenseTrackerLabel = expenseTrackerLabel;
    }

    public TextField getUserNameField() {
        return userNameField;
    }

    public void setUserNameField(TextField userNameField) {
        this.userNameField = userNameField;
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public void setPasswordField(PasswordField passwordField) {
        this.passwordField = passwordField;
    }

    public Button getLoginButton() {
        return loginButton;
    }

    public void setLoginButton(Button loginButton) {
        this.loginButton = loginButton;
    }

    public Label getSignUpLabel() {
        return signUpLabel;
    }

    public void setSignUpLabel(Label signUpLabel) {
        this.signUpLabel = signUpLabel;
    }
}
