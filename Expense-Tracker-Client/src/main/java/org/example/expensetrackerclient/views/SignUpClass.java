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
import org.example.expensetrackerclient.Controller.SignUpController;
import org.example.expensetrackerclient.utils.Utlities;
import org.example.expensetrackerclient.utils.ViewNavigator;

import javax.swing.text.Utilities;

public class SignUpClass {
    private Label expenseTrackerLabel=new Label("Expense Tracker");
    private TextField nameField=new TextField();
    private TextField usernameField=new TextField();
    private PasswordField passwordField=new PasswordField();
    private PasswordField rePasswordField=new PasswordField();

    private Button registerButton =new Button("Sign Up");
    private Label signInLabel =new Label("Already have a Account? Login here");

    public void show(){
        Scene scene=createScene();

        //integrating CSS
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        new SignUpController(this);

        ViewNavigator.switchViews(scene);
    }

    private Scene createScene(){
        VBox mainContainer=new VBox(20);
        mainContainer.getStyleClass().addAll("main-background");
        mainContainer.setAlignment(Pos.TOP_CENTER);
        expenseTrackerLabel.getStyleClass().addAll("header","text-white");
        VBox.setMargin(expenseTrackerLabel, new Insets(30, 0, 0, 0));

        VBox signUpFormContainer=createSignUpForm();

        mainContainer.getChildren().addAll(expenseTrackerLabel,signUpFormContainer);
        return new Scene(mainContainer, Utlities.APP_WIDTH,Utlities.APP_HEIGHT);
    }

    public VBox createSignUpForm(){
        VBox signUpForm=new VBox(30);
        signUpForm.setAlignment(Pos.CENTER);
        VBox.setMargin(signUpForm, new Insets(50, 0, 0, 0));

        nameField.getStyleClass().addAll("field-background","text-light-gray","text-size-lg","rounded-border");
        nameField.setPromptText("Enter Name");
        nameField.setMaxWidth(320);

        usernameField.getStyleClass().addAll("field-background","text-light-gray","text-size-lg","rounded-border");
        usernameField.setPromptText("Enter Email");
        usernameField.setMaxWidth(320);

        passwordField.getStyleClass().addAll("field-background","text-light-gray","text-size-lg","rounded-border");
        passwordField.setPromptText("Enter Password");
        passwordField.setMaxWidth(320);

        rePasswordField.getStyleClass().addAll("field-background","text-light-gray","text-size-lg","rounded-border");
        rePasswordField.setPromptText("Re-enter Password");
        rePasswordField.setMaxWidth(320);

        registerButton.getStyleClass().addAll("text-size-lg","bg-light-blue","text-white","text-weight-700","rounded-border");
        registerButton.setMaxWidth(320);

        signInLabel.getStyleClass().addAll("text-size-md","text-light-gray","text-underline","link-text");

        signUpForm.getChildren().addAll(nameField,usernameField,passwordField,rePasswordField,registerButton,signInLabel);
        return signUpForm;
    }

    public Label getExpenseTrackerLabel() {
        return expenseTrackerLabel;
    }

    public void setExpenseTrackerLabel(Label expenseTrackerLabel) {
        this.expenseTrackerLabel = expenseTrackerLabel;
    }

    public TextField getNameField() {
        return nameField;
    }

    public void setNameField(TextField nameField) {
        this.nameField = nameField;
    }

    public TextField getUsernameField() {
        return usernameField;
    }

    public void setUsernameField(TextField usernameField) {
        this.usernameField = usernameField;
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public void setPasswordField(PasswordField passwordField) {
        this.passwordField = passwordField;
    }

    public PasswordField getRePasswordField() {
        return rePasswordField;
    }

    public void setRePasswordField(PasswordField rePasswordField) {
        this.rePasswordField = rePasswordField;
    }

    public Button getRegisterButton() {
        return registerButton;
    }

    public void setRegisterButton(Button registerButton) {
        this.registerButton = registerButton;
    }

    public Label getSignInLabel() {
        return signInLabel;
    }

    public void setSignInLabel(Label signInLabel) {
        this.signInLabel = signInLabel;
    }
}
