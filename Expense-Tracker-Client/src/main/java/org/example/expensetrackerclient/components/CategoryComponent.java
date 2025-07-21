package org.example.expensetrackerclient.components;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import org.example.expensetrackerclient.Controller.DashBoardController;
import org.example.expensetrackerclient.Models.TransactionCategory;

public class CategoryComponent extends HBox {
    private DashBoardController dashBoardController;
    private TransactionCategory transactionCategory;

    private TextField categoryTextField;
    private ColorPicker colorPicker;
    private Button editButton, saveButton, deleteButton;

    public CategoryComponent(DashBoardController dashBoardController, TransactionCategory transactionCategory){
        this.dashBoardController=dashBoardController;
        this.transactionCategory=transactionCategory;

        setSpacing(10);
        setAlignment(Pos.CENTER_LEFT);
        getStyleClass().addAll("rounded-border","field-background","padding-8px");

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

        saveButton=new Button("Save");
        saveButton.setMinWidth(20);
        saveButton.getStyleClass().addAll("text-size-sm");

        deleteButton=new Button("Delete");
        deleteButton.setMinWidth(20);
        deleteButton.getStyleClass().addAll("text-size-sm","bg-light-red","text-white");

        getChildren().addAll(categoryTextField,colorPicker,editButton,saveButton,deleteButton);
    }
}
