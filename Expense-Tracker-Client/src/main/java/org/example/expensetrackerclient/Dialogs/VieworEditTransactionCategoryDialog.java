package org.example.expensetrackerclient.Dialogs;

import javafx.scene.layout.VBox;
import org.example.expensetrackerclient.Controller.DashBoardController;
import org.example.expensetrackerclient.Models.TransactionCategory;
import org.example.expensetrackerclient.Models.User;
import org.example.expensetrackerclient.components.CategoryComponent;
import org.example.expensetrackerclient.utils.SQLUtil;
import javafx.scene.control.ScrollPane;
import java.util.List;

public class VieworEditTransactionCategoryDialog extends CustomDialog{
    private DashBoardController dashBoardController;
    public VieworEditTransactionCategoryDialog(User user, DashBoardController dashBoardController){
        super(user);
        this.dashBoardController = dashBoardController;
        setTitle("View Categories");

        // Main ScrollPane with VBox inside
        ScrollPane mainContainer = createMainContainerContent();

        // Force dialog pane size
        getDialogPane().setPrefSize(600, 500);

        // Force scrollPane size
//      mainContainer.setPrefViewportHeight(750);
//        mainContainer.setPrefViewportWidth(700);

        getDialogPane().setContent(mainContainer);
    }

    private ScrollPane createMainContainerContent(){
        VBox dialogVBox=new VBox(25);
        ScrollPane scrollPane=new ScrollPane(dialogVBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(500);


        List<TransactionCategory>transactionCategories= SQLUtil.getAllTransactionCategoryByUser(user);

        for(TransactionCategory transactionCategory:transactionCategories){
            CategoryComponent categoryComponent=new CategoryComponent(dashBoardController,transactionCategory);

            dialogVBox.getChildren().add(categoryComponent);
        }
        return scrollPane;
    }


}
