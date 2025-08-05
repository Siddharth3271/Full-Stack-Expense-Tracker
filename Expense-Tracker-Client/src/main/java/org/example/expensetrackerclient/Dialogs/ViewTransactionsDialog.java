package org.example.expensetrackerclient.Dialogs;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import org.example.expensetrackerclient.Controller.DashBoardController;
import org.example.expensetrackerclient.Models.Transaction;
import org.example.expensetrackerclient.Models.User;
import org.example.expensetrackerclient.components.TransactionComponent;
import org.example.expensetrackerclient.utils.SQLUtil;

import java.time.Month;
import java.util.List;

public class ViewTransactionsDialog extends CustomDialog{

    private DashBoardController dashBoardController;
    private String monthName;
    public ViewTransactionsDialog(DashBoardController dashBoardController,String monthName) {
        super(dashBoardController.getUser());
        this.dashBoardController=dashBoardController;
        this.monthName=monthName;

        setTitle("View Transactions");
        getDialogPane().setPrefSize(500, 400);

        ScrollPane transactionScrollPane=createTransactionScrollPane();
        getDialogPane().setContent(transactionScrollPane);
    }

    ScrollPane createTransactionScrollPane(){
        VBox vBox = new VBox(15);

        ScrollPane scrollPane = new ScrollPane(vBox);
        scrollPane.setMinHeight(getHeight() - 40);
        scrollPane.setFitToWidth(true);

        List<Transaction> monthTransactions = SQLUtil.getAllTransactionsByUserId(
                dashBoardController.getUser().getId(),
                dashBoardController.getCurrentYear(),
                Month.valueOf(monthName).getValue()
        );

        if(monthTransactions != null){
            for(Transaction transaction : monthTransactions){
                TransactionComponent transactionComponent = new TransactionComponent(
                        dashBoardController,
                        transaction
                );
                transactionComponent.getStyleClass().addAll("border-light-gray");

                vBox.getChildren().add(transactionComponent);
            }
        }

        return scrollPane;
    }
}
