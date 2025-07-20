package org.example.expensetrackerclient.Controller;

import com.google.gson.JsonObject;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import org.example.expensetrackerclient.utils.SQLUtil;
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

                //created a json data to send to our post request
                JsonObject jsondata=new JsonObject();
                jsondata.addProperty("name",name);
                jsondata.addProperty("email",username);
                jsondata.addProperty("password",password);

                //send in our post request to create a user
                boolean postCreateAccountStatus= SQLUtil.postCreateUser(jsondata);

                //depending on the result we will display the corresponding alert message
                if(postCreateAccountStatus){
                    Utlities.showAlertDialog(Alert.AlertType.INFORMATION,"Successfully created new account");
                    new LoginClass().show();
                }
                else{
                    Utlities.showAlertDialog(Alert.AlertType.ERROR,"Failed to create account...");
                }
            }
        });
    }


    private boolean validateInput(){
        if(signUpClass.getNameField().getText().isEmpty()) return false;
        if(signUpClass.getUsernameField().getText().isEmpty()) return false;
        if(signUpClass.getPasswordField().getText().isEmpty()) return false;
        if(signUpClass.getRePasswordField().getText().isEmpty()) return false;

        //entered email already exist in database or not(prevent duplicate registration)
        if(SQLUtil.getUserByEmail(signUpClass.getUsernameField().getText())!=null){
            return false;
        }

        return true;
    }
}
