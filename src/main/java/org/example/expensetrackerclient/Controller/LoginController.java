package org.example.expensetrackerclient.Controller;

import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import org.example.expensetrackerclient.utils.ApiUtil;
import org.example.expensetrackerclient.utils.Utlities;
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
                HttpURLConnection conn=null;
                try{
                    conn= ApiUtil.fetchApi(
                            "/api/v1/user/login?email="+email+"&password="+password,
                            ApiUtil.RequestMethod.POST,
                            null
                    );

                    if(conn.getResponseCode()!=200){
                        System.out.println("Failed to Authenticate");
                        Utlities.showAlertDialog(Alert.AlertType.ERROR,"Failed to Authenticate");
                    }
                    else{
                        System.out.println("Login Successfull");
                        Utlities.showAlertDialog(Alert.AlertType.INFORMATION,"Login Sucessfull");
                    }
                }
                catch(Exception e){
                    e.printStackTrace();
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
