package org.example.expensetrackerclient.components;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.example.expensetrackerclient.Controller.DashBoardController;
import org.example.expensetrackerclient.Models.TransactionCategory;
import org.example.expensetrackerclient.utils.SQLUtil;
import org.example.expensetrackerclient.utils.Utlities;

public class CategoryComponent extends HBox {
    private DashBoardController dashBoardController;
    private TransactionCategory transactionCategory;

    private TextField categoryTextField;
    private ColorPicker colorPicker;
    private Button editButton, saveButton, deleteButton;

    private boolean isEditing;

    public CategoryComponent(DashBoardController dashBoardController, TransactionCategory transactionCategory){
        this.dashBoardController=dashBoardController;
        this.transactionCategory=transactionCategory;

        setSpacing(10);
        setAlignment(Pos.CENTER_LEFT);
        getStyleClass().addAll("rounded-border","field-background","padding-8px");
        setPadding(new Insets(5, 15, 5, 10));

        categoryTextField=new TextField();
        categoryTextField.setText(transactionCategory.getCategoryName());
        categoryTextField.setMaxWidth(Double.MAX_VALUE);
        categoryTextField.setEditable(false);
        categoryTextField.getStyleClass().addAll("field-background","text-size-md","text-light-gray");

        colorPicker=new ColorPicker();
        colorPicker.setDisable(true);
        colorPicker.setValue(Color.valueOf(transactionCategory.getCategoryColor()));
        colorPicker.getStyleClass().addAll("text-size-sm");

        editButton=new Button("Edit");
        editButton.setMinWidth(20);
        editButton.getStyleClass().addAll("text-size-sm");
        editButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                handleToggle();
            }
        });

        saveButton=new Button("Save");
        saveButton.setMinWidth(20);
        saveButton.getStyleClass().addAll("text-size-sm");
        saveButton.setVisible(false);
        saveButton.setManaged(false);
        saveButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                handleToggle();

                //extract data
                String newCategoryName=categoryTextField.getText();
                String newCategoryColor= Utlities.getHexColorValue(colorPicker);

                //update the database
                SQLUtil.putTransactionCategory(transactionCategory.getId(),newCategoryName,newCategoryColor);

                //refreshes the dashboard
                dashBoardController.fetchUserData();
            }
        });

        deleteButton=new Button("Delete");
        deleteButton.setMinWidth(20);
        deleteButton.getStyleClass().addAll("text-size-sm","bg-light-red","text-white");
        deleteButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(!SQLUtil.deleteTransactionCategoryById(transactionCategory.getId())){
                    return;
                }

                //remove the component from the dialog
                setVisible(false);
                setManaged(false);

                if(getParent() instanceof VBox){
                    ((VBox)getParent()).getChildren().remove(CategoryComponent.this);
                }
            }
        });
        Region spacer = new Region();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);
        getChildren().addAll(categoryTextField,spacer,colorPicker,editButton,saveButton,deleteButton);
    }

    private void handleToggle(){
        if(!isEditing) {
            isEditing = true;

            //enable the category text field
            categoryTextField.setEditable(true);
            categoryTextField.setStyle("-fx-background-color: #fff ; -fx-text-fill:#000");

            //enable the color picker
            colorPicker.setDisable(false);

            //hide the edit button
            editButton.setVisible(false);
            editButton.setManaged(false);

            //display the save button
            saveButton.setVisible(true);
            saveButton.setManaged(true);
        }
        else{
            isEditing=false;

            //disable the category text field
            categoryTextField.setEditable(false);
            categoryTextField.setStyle("-fx-background-color: #515050 ; -fx-text-fill:#BEB9B9");

            //disable the color picker
            colorPicker.setDisable(true);

            //display the edit button
            editButton.setVisible(true);
            editButton.setManaged(true);

            //hide the save button
            saveButton.setVisible(false);
            saveButton.setManaged(false);
        }
    }
}
