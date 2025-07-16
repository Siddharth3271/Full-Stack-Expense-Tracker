package org.example.expensetrackerclient.Controller;

import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import org.example.expensetrackerclient.utils.Utlities;
import org.example.expensetrackerclient.views.LoginClass;
import org.example.expensetrackerclient.views.SignUpClass;

public class SignUpController {
    private SignUpClass signUpClass;
    public SignUpController(SignUpClass signUpClass){
        this.signUpClass=signUpClass;
        initialize();
    }

    private void initialize(){
        signUpClass.getSignInLabel().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                new LoginClass().show();
            }
        });

        signUpClass.getRegisterButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(!validateInput()){
                    Utlities.showAlertDialog(Alert.AlertType.ERROR,"Invalid Input");
                    return;
                }
                else if(!signUpClass.getPasswordField().getText().equals(signUpClass.getRePasswordField().getText())){
                    Utlities.showAlertDialog(Alert.AlertType.ERROR,"Password doesn't match");
                    return;
                }

                //extract the data in the fields
                String name=signUpClass.getNameField().getText();
                String username=signUpClass.getUsernameField().getText();
                String password=signUpClass.getPasswordField().getText();
            }
        });
    }

    private boolean validateInput(){
        if(signUpClass.getNameField().getText().isEmpty()) return false;
        if(signUpClass.getUsernameField().getText().isEmpty()) return false;
        if(signUpClass.getPasswordField().getText().isEmpty()) return false;
        if(signUpClass.getRePasswordField().getText().isEmpty()) return false;

        return true;
    }
}
