package org.example.expensetrackerclient.Dialogs;

import com.google.gson.JsonObject;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.example.expensetrackerclient.Models.User;
import org.example.expensetrackerclient.utils.SQLUtil;
import org.example.expensetrackerclient.utils.Utlities;

import javax.swing.text.Utilities;


public class CreateNewCategoryDialog extends CustomDialog{
    private TextField newCategoryTextField;
    private ColorPicker colorPicker;
    private Button createCategoryButton;

    public CreateNewCategoryDialog(User user){
        super(user);
        setTitle("Create new Category");
        getDialogPane().setContent(createDialogContentBox());
    }

    private VBox createDialogContentBox(){
        VBox dialogContentBox=new VBox(10);

        newCategoryTextField=new TextField();
        newCategoryTextField.setPromptText("Enter Category Name");
        newCategoryTextField.getStyleClass().addAll("text-size-md","field-background","text-light-gray");

        colorPicker=new ColorPicker();
        colorPicker.getStyleClass().add("text-size-md");
        colorPicker.setMaxWidth(Double.MAX_VALUE);

        createCategoryButton=new Button("Create");
        createCategoryButton.getStyleClass().addAll("text-size-md","bg-light-blue","text-white");
        createCategoryButton.setMaxWidth(Double.MAX_VALUE);
        createCategoryButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //extract the data
                String categoryName=newCategoryTextField.getText();
                String color= Utlities.getHexColorValue(colorPicker);
                System.out.println(color);

                if (categoryName.isEmpty()) {
                    System.out.println("Category name cannot be empty!");
                    Utlities.showAlertDialog(Alert.AlertType.ERROR,"Category name cannot be empty!");
                    return;
                }

                JsonObject userData=new JsonObject();
                userData.addProperty("id",user.getId());

                JsonObject transactionCategoryData=new JsonObject();
                transactionCategoryData.add("user",userData);
                transactionCategoryData.addProperty("categoryName",categoryName);
                transactionCategoryData.addProperty("categoryColor",color);

                boolean postTransactionCategoryStatus= SQLUtil.postTransactionCategory(transactionCategoryData);

                if(postTransactionCategoryStatus){
                    Utlities.showAlertDialog(Alert.AlertType.INFORMATION,"Success: Create a Transaction Category");
                }
                else{
                    Utlities.showAlertDialog(Alert.AlertType.ERROR,"Error: Failed to create a Transaction Category");
                }
            }
        });

        dialogContentBox.getChildren().addAll(newCategoryTextField,colorPicker,createCategoryButton);
        return dialogContentBox;
    }

}
