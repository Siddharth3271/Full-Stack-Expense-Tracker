package org.example.expensetrackerclient.Controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.example.expensetrackerclient.Dialogs.CreateNewCategoryDialog;
import org.example.expensetrackerclient.Dialogs.VieworEditTransactionCategoryDialog;
import org.example.expensetrackerclient.Models.User;
import org.example.expensetrackerclient.utils.SQLUtil;
import org.example.expensetrackerclient.views.DashBoardView;

public class DashBoardController {
    private DashBoardView dashBoardView;

    private User user;

    public DashBoardController(DashBoardView dashBoardView){
        this.dashBoardView=dashBoardView;
        fetchUserData();
        initialize();
    }

    public void fetchUserData(){
        user= SQLUtil.getUserByEmail(dashBoardView.getEmail());
    }

    private void initialize(){
        addMenuActions();
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
}
