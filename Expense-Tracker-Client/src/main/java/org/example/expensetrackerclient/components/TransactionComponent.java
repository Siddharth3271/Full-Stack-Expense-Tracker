package org.example.expensetrackerclient.components;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import org.example.expensetrackerclient.Controller.DashBoardController;
import org.example.expensetrackerclient.Dialogs.CreateOrEditTransactionDialog;
import org.example.expensetrackerclient.Models.Transaction;
import org.example.expensetrackerclient.utils.SQLUtil;

public class TransactionComponent extends HBox {
    private Label transactionCategoryLabel,transactionNameLabel, transactionDateLabel,transactionAmountLabel;
    private Button editButton,deleteButton;

    private DashBoardController dashBoardController;

    private Transaction transaction;

    public TransactionComponent(DashBoardController dashBoardController,Transaction transaction){
        this.dashBoardController=dashBoardController;
        this.transaction=transaction;

        setSpacing(10);
        setAlignment(Pos.CENTER_LEFT);
        getStyleClass().addAll("main-background","rounded-border","padding-8px");

        VBox categoryNameDateSection=createCategoryNameDateSection();
        Region region=new Region();
        HBox.setHgrow(region, Priority.ALWAYS);

        transactionAmountLabel=new Label("₹"+transaction.getTransactionAmount());
        transactionAmountLabel.getStyleClass().add("text-size-md");
        if(transaction.getTransactionType().equalsIgnoreCase("expense")){
            transactionAmountLabel.getStyleClass().add("text-light-red");
        }
        else{
            transactionAmountLabel.getStyleClass().add("text-light-green");
        }

        HBox actionButtonSection=createActionButtons();

        getChildren().addAll(categoryNameDateSection,region,transactionAmountLabel,actionButtonSection);
    }

    private VBox createCategoryNameDateSection(){
        VBox categoryNameDateSection=new VBox();
        if(transaction.getTransactionCategory()==null){
            transactionCategoryLabel=new Label("Undefined");
            transactionCategoryLabel.getStyleClass().addAll("text-light-gray");
        }
        else{
            transactionCategoryLabel=new Label(transaction.getTransactionCategory().getCategoryName());
            transactionCategoryLabel.setTextFill(Paint.valueOf("#"+transaction.getTransactionCategory().getCategoryColor()));
        }

        transactionNameLabel=new Label(transaction.getTransactionName());
        transactionNameLabel.getStyleClass().addAll("text-light-gray","text-size-md");

        transactionDateLabel=new Label(transaction.getTransactionDate().toString());
        transactionDateLabel.getStyleClass().addAll("text-light-gray");

        categoryNameDateSection.getChildren().addAll(transactionCategoryLabel,transactionNameLabel,transactionDateLabel);
        return categoryNameDateSection;
    }

    private HBox createActionButtons(){
        HBox actionButtonSection=new HBox(15);
        actionButtonSection.setAlignment(Pos.CENTER);
        editButton=new Button("Edit");
        editButton.getStyleClass().addAll("text-size-md","rounded-border");
        editButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                new CreateOrEditTransactionDialog(dashBoardController,TransactionComponent.this,true).showAndWait();
            }
        });

        deleteButton=new Button("Delete");
        deleteButton.getStyleClass().addAll("text-size-md","rounded-border","bg-light-red","text-white");
        deleteButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(!SQLUtil.deleteTransactionById(transaction.getId())){
                    return;
                }
                //remove the component from the dashboard
                setVisible(false);
                setManaged(false);
                if(getParent() instanceof VBox){
                    ((VBox) getParent()).getChildren().remove(TransactionComponent.this);
                }
            }
        });

        actionButtonSection.getChildren().addAll(editButton,deleteButton);
        return actionButtonSection;
    }

    public Transaction getTransaction(){
        return transaction;
    }
}
