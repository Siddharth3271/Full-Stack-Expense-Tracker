package org.example.expensetrackerclient.Dialogs;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.example.expensetrackerclient.Models.User;


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

            }
        });

        dialogContentBox.getChildren().addAll(newCategoryTextField,colorPicker,createCategoryButton);
        return dialogContentBox;
    }

}
