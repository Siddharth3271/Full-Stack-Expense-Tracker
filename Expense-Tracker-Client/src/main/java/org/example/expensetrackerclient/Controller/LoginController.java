package org.example.expensetrackerclient.Controller;

import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import org.example.expensetrackerclient.utils.ApiUtil;
import org.example.expensetrackerclient.utils.SQLUtil;
import org.example.expensetrackerclient.utils.Utlities;
import org.example.expensetrackerclient.views.DashBoardView;
import org.example.expensetrackerclient.views.LoginClass;
import org.example.expensetrackerclient.views.SignUpClass;

import java.net.HttpURLConnection;

public class LoginController {
    private LoginClass loginClass;

    public LoginController(LoginClass loginClass){
        this.loginClass=loginClass;
        intialize();
    }

    //providing action listeners
    private void intialize(){
        loginClass.getLoginButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
//                System.out.println("Click");

                if(!validateUser()) return;

                String email=loginClass.getUserNameField().getText();
                String password=loginClass.getPasswordField().getText();

                //authenticate email and password
                if(SQLUtil.postLoginUser(email,password)){
                    Utlities.showAlertDialog(Alert.AlertType.INFORMATION,"Login Sucessfull");
                    new DashBoardView(email).show();
                }
                else{
                    Utlities.showAlertDialog(Alert.AlertType.ERROR,"Failed to Authenticate");
                }
            }
        });

        loginClass.getSignUpLabel().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                new SignUpClass().show();
            }
        });
    }

    private boolean validateUser(){
        if(loginClass.getUserNameField().getText().isEmpty()){
            return false;
        }

        // empty password
        if(loginClass.getPasswordField().getText().isEmpty()){
            return false;
        }

        return true;
    }


}
