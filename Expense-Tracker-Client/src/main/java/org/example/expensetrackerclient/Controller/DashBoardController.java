package org.example.expensetrackerclient.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import org.example.expensetrackerclient.Dialogs.CreateNewCategoryDialog;
import org.example.expensetrackerclient.Dialogs.CreateOrEditTransactionDialog;
import org.example.expensetrackerclient.Dialogs.VieworEditTransactionCategoryDialog;
import org.example.expensetrackerclient.Models.MonthlyFinance;
import org.example.expensetrackerclient.Models.Transaction;
import org.example.expensetrackerclient.Models.User;
import org.example.expensetrackerclient.components.TransactionComponent;
import org.example.expensetrackerclient.utils.SQLUtil;
import org.example.expensetrackerclient.views.DashBoardView;
import org.example.expensetrackerclient.views.LoginClass;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public class DashBoardController {
    private final int recentTransactionSize=10;
    private DashBoardView dashBoardView;

    private User user;

    private int currentPage;
    private int currentYear;
    private List<Transaction>recentTransactions,currentTransactionsByYear;

    public DashBoardController(DashBoardView dashBoardView){
        this.dashBoardView=dashBoardView;
        currentYear=dashBoardView.getYearComboBox().getValue();  //defaults to the current year
//        currentYear=2024;
        fetchUserData();
        initialize();
    }

    public void fetchUserData(){
        //load the loader animation
        dashBoardView.getLoadingAnimationPane().setVisible(true);

        //remove all the children from the dashboard view
        dashBoardView.getRecentTransactionsBox().getChildren().clear();

        user= SQLUtil.getUserByEmail(dashBoardView.getEmail());

        //get the transactions for the year
        currentTransactionsByYear=SQLUtil.getAllTransactionsByUserId(user.getId(),currentYear);
        dashBoardView.getYearComboBox().setItems(FXCollections.observableList(SQLUtil.getAllDistinctYears(user.getId())));
        dashBoardView.getTransactionTable().setItems(calculateMonthlyFinances());

        createRecentTransactionComponents();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(1000);
                    dashBoardView.getLoadingAnimationPane().setVisible(false);
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }).start();
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

    private ObservableList<MonthlyFinance>calculateMonthlyFinances(){
        double[] incomeCounter=new double[12];
        double[] expenseCounter=new double[12];

        for(Transaction transaction:currentTransactionsByYear){
            LocalDate transactionDate=transaction.getTransactionDate();
            if(transaction.getTransactionType().equalsIgnoreCase("income")){
                incomeCounter[transactionDate.getMonth().getValue()-1]+=transaction.getTransactionAmount();
            }
            else{
                expenseCounter[transactionDate.getMonth().getValue()-1]+=transaction.getTransactionAmount();
            }
        }

        ObservableList<MonthlyFinance>monthlyFinances= FXCollections.observableArrayList();
        for(int i=0;i<12;i++){
            MonthlyFinance monthlyFinance=new MonthlyFinance(
                    Month.of(i+1).name(),new BigDecimal(String.valueOf(incomeCounter[i])),
                    new BigDecimal(String.valueOf(expenseCounter[i]))
            );
            monthlyFinances.add(monthlyFinance);
        }
        return monthlyFinances;
    }

    private void initialize(){

        addMenuActions();
        addRecentTransactionActions();
        addComboBoxActions();

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

        dashBoardView.getLogoutMenuItem().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                new LoginClass().show();
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

    private void addComboBoxActions(){
        dashBoardView.getYearComboBox().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // update the current year
                currentYear = dashBoardView.getYearComboBox().getValue();
                // refresh the data
                fetchUserData();
            }
        });
    }

    public User getUser(){
        return user;
    }

}
