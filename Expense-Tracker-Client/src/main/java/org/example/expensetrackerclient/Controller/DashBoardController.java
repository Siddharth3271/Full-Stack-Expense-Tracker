package org.example.expensetrackerclient.Controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import org.example.expensetrackerclient.Dialogs.CreateNewCategoryDialog;
import org.example.expensetrackerclient.Dialogs.CreateOrEditTransactionDialog;
import org.example.expensetrackerclient.Dialogs.VieworEditTransactionCategoryDialog;
import org.example.expensetrackerclient.Models.Transaction;
import org.example.expensetrackerclient.Models.User;
import org.example.expensetrackerclient.components.TransactionComponent;
import org.example.expensetrackerclient.utils.SQLUtil;
import org.example.expensetrackerclient.views.DashBoardView;

import java.util.List;

public class DashBoardController {
    private final int recentTransactionSize=10;
    private DashBoardView dashBoardView;

    private User user;

    private int currentPage;
    private List<Transaction>recentTransactions;

    public DashBoardController(DashBoardView dashBoardView){
        this.dashBoardView=dashBoardView;
        fetchUserData();
        initialize();
    }

    public void fetchUserData(){
        //load the loader animation


        //remove all the children from the dashboard view
        dashBoardView.getRecentTransactionsBox().getChildren().clear();
        user= SQLUtil.getUserByEmail(dashBoardView.getEmail());
        createRecentTransactionComponents();
    }

    private void createRecentTransactionComponents(){
        recentTransactions=SQLUtil.getRecentTransactionByUserId(user.getId(), 0,currentPage,recentTransactionSize);
        if(recentTransactions==null){
            return;
        }
        for(Transaction transaction:recentTransactions){
            dashBoardView.getRecentTransactionsBox().getChildren().add(new TransactionComponent(this,transaction));
        }
    }

    private void initialize(){

        addMenuActions();
        addRecentTransactionActions();

    }

    private void addMenuActions(){
        dashBoardView.getCreateCategoryMenuItem().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                new CreateNewCategoryDialog(user).showAndWait();
            }
        });

        dashBoardView.getViewCategoriesMenuItem().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                new VieworEditTransactionCategoryDialog(user,DashBoardController.this).showAndWait();
            }
        });
    }

    private void addRecentTransactionActions(){
        dashBoardView.getAddTransactionButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                new CreateOrEditTransactionDialog(DashBoardController.this,false).showAndWait();
            }
        });
    }

    public User getUser(){
        return user;
    }

}
