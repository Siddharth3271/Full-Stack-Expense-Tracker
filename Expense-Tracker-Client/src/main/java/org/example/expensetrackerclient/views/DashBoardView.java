package org.example.expensetrackerclient.views;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import org.example.expensetrackerclient.Controller.DashBoardController;
import org.example.expensetrackerclient.Models.MonthlyFinance;
import org.example.expensetrackerclient.animations.LoadingAnimationPane;
import org.example.expensetrackerclient.utils.ViewNavigator;
import org.example.expensetrackerclient.utils.Utlities;

import java.math.BigDecimal;
import java.time.Year;

public class DashBoardView {

    private String email;
    private LoadingAnimationPane loadingAnimationPane;
    private Label currentBalanceLabel,currentBalance;
    private Label totalIncomeLabel,totalIncome;
    private Label totalExpenseLabel,totalExpense;

    private ComboBox<Integer>yearComboBox;
    private MenuItem createCategoryMenuItem,viewCategoriesMenuItem,logoutMenuItem;
    private Button addTransactionButton,viewChartButton;
    private VBox recentTransactionsBox;
    private ScrollPane recentTransactionScrollPane;

    //table
    private TableView<MonthlyFinance>transactionTable;
    private TableColumn<MonthlyFinance,String>monthColumn;
    private TableColumn<MonthlyFinance, BigDecimal>incomeColumn;
    private TableColumn<MonthlyFinance,BigDecimal>expenseColumn;

    public DashBoardView(String email){
        this.email=email;
        loadingAnimationPane=new LoadingAnimationPane(Utlities.APP_WIDTH, Utlities.APP_HEIGHT);
        currentBalanceLabel=new Label("Current Balance: ");
        totalExpenseLabel=new Label("Total Expense: ");
        totalIncomeLabel=new Label("Total Income: ");

        addTransactionButton=new Button("+");

        currentBalance=new Label("₹0.00");
        totalExpense=new Label("₹0.00");
        totalIncome=new Label("₹0.00");

    }

    public void show(){
        Scene scene=createScene();
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        new DashBoardController(this);
        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                loadingAnimationPane.resizeWidth(t1.doubleValue());
            }
        });

        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                loadingAnimationPane.resizeHeight(t1.doubleValue());
            }
        });

        ViewNavigator.switchViews(scene);
    }

    private Scene createScene(){

        MenuBar menuBar=createMenuBar();

        VBox mainContainer=new VBox();
        mainContainer.getStyleClass().addAll("main-background");

        VBox mainContainerWrapper=new VBox();
        mainContainerWrapper.getStyleClass().addAll("dashboard-padding");
        VBox.setVgrow(mainContainerWrapper,Priority.ALWAYS);

        HBox balanceSummaryBox=createBalanceSummaryBox();
        GridPane contentGridPane=createContentGridPane();
        VBox.setVgrow(contentGridPane,Priority.ALWAYS);

        mainContainerWrapper.getChildren().addAll(balanceSummaryBox,contentGridPane);
        mainContainer.getChildren().addAll(menuBar,mainContainerWrapper,loadingAnimationPane);
        return new Scene(mainContainer, Utlities.APP_WIDTH,Utlities.APP_HEIGHT);
    }

    private MenuBar createMenuBar(){
        MenuBar menuBar=new MenuBar();
        Menu fileMenu=new Menu("File");

        createCategoryMenuItem=new MenuItem("Create Category");
        viewCategoriesMenuItem=new MenuItem("View Categories");
        logoutMenuItem=new MenuItem("Logout");

        fileMenu.getItems().addAll(createCategoryMenuItem,viewCategoriesMenuItem,logoutMenuItem);
        menuBar.getMenus().addAll(fileMenu);

        return menuBar;
    }

    private HBox createBalanceSummaryBox(){
        HBox balanceSummaryBox=new HBox();

        VBox currentBalanceBox=new VBox();
        currentBalanceLabel.getStyleClass().addAll("text-size-lg","text-light-gray");
        currentBalance.getStyleClass().addAll("text-size-lg","text-white");
        currentBalanceBox.getChildren().addAll(currentBalanceLabel,currentBalance);

        //to get some space between containers
        Region region1=new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);

        VBox totalIncomeBox=new VBox();
        totalIncomeLabel.getStyleClass().addAll("text-size-lg","text-light-gray");
        totalIncome.getStyleClass().addAll("text-size-lg","text-white");
        totalIncomeBox.getChildren().addAll(totalIncomeLabel,totalIncome);

        Region region2=new Region();
        HBox.setHgrow(region2, Priority.ALWAYS);

        VBox totalExpenseBox=new VBox();
        totalExpenseLabel.getStyleClass().addAll("text-size-lg","text-light-gray");
        totalExpense.getStyleClass().addAll("text-size-lg","text-white");
        totalExpenseBox.getChildren().addAll(totalExpenseLabel,totalExpense);


        balanceSummaryBox.getChildren().addAll(currentBalanceBox,region1,totalIncomeBox,region2,totalExpenseBox);
        return balanceSummaryBox;
    }

    private GridPane createContentGridPane(){
        GridPane gridPane=new GridPane();

        //set the constraints to the cells in the gridpane
        ColumnConstraints columnConstraints=new ColumnConstraints();
        columnConstraints.setPercentWidth(50);
        gridPane.getColumnConstraints().addAll(columnConstraints,columnConstraints);

        //transaction table summary(left side)
        VBox transactionTableSummaryBox=new VBox(15);
        HBox yearComboBoxAndChartButtonBox=createYearComboBoxAndChartButtonBox();
        VBox transactionTableContentBox=createTransactionTableContentBox();
        transactionTableSummaryBox.getChildren().addAll(yearComboBoxAndChartButtonBox,transactionTableContentBox);

        //recent transactions(right side)
        VBox recentTransactions=createRecentTransactionsVBox();
        recentTransactions.getStyleClass().addAll("field-background","rounded-border","padding-10px");
        GridPane.setVgrow(recentTransactions,Priority.ALWAYS);

        gridPane.add(transactionTableSummaryBox,0,0);
        gridPane.add(recentTransactions,1,0);
        return gridPane;
    }

    private HBox createYearComboBoxAndChartButtonBox(){
        HBox hBox=new HBox(15);
        yearComboBox=new ComboBox<>();
        yearComboBox.getStyleClass().addAll("text-size-md");
        yearComboBox.setValue(Year.now().getValue());

        viewChartButton=new Button("View Chart");
        viewChartButton.getStyleClass().addAll("field-background","text-light-gray","text-size-md");

        hBox.getChildren().addAll(yearComboBox,viewChartButton);
        return hBox;
    }

    private VBox createTransactionTableContentBox(){
        VBox vbox = new VBox();
        transactionTable = new TableView<>();
        VBox.setVgrow(transactionTable, Priority.ALWAYS);

        monthColumn = new TableColumn<>("Month");
        monthColumn.setCellValueFactory(new PropertyValueFactory<>("month"));
        monthColumn.getStyleClass().addAll("main-background", "text-size-md", "text-light-gray");

        incomeColumn = new TableColumn<>("Income");
        incomeColumn.setCellValueFactory(new PropertyValueFactory<>("income"));
        incomeColumn.getStyleClass().addAll("main-background", "text-size-md", "text-light-gray");

        expenseColumn = new TableColumn<>("Expense");
        expenseColumn.setCellValueFactory(new PropertyValueFactory<>("expense"));
        expenseColumn.getStyleClass().addAll("main-background", "text-size-md", "text-light-gray");

        transactionTable.getColumns().addAll(monthColumn, incomeColumn, expenseColumn);
        vbox.getChildren().addAll(transactionTable);

//        resizeTableWidthColumns();
        return vbox;
    }

    private VBox createRecentTransactionsVBox(){
        VBox recentTransactions=new VBox();

        //label and add button
        HBox recentTransactionLabelandButton=new HBox();
        Label recentTransactionLabel=new Label("Recent Transactions");
        recentTransactionLabel.getStyleClass().addAll("text-size-lg","text-light-gray");

        Region labelandButtonSpace=new Region();
        HBox.setHgrow(labelandButtonSpace,Priority.ALWAYS);

        addTransactionButton.getStyleClass().addAll("field-background","text-size-md","text-light-gray","rounded-border");

        recentTransactionLabelandButton.getChildren().addAll(recentTransactionLabel,labelandButtonSpace,addTransactionButton);

        //recent transactions box
        recentTransactionsBox=new VBox(8);
        recentTransactionScrollPane=new ScrollPane(recentTransactionsBox);
        recentTransactionScrollPane.setFitToWidth(true);
        recentTransactionScrollPane.setFitToHeight(true);

        recentTransactions.getChildren().addAll(recentTransactionLabelandButton,recentTransactionScrollPane);
        return recentTransactions;
    }

    public MenuItem getCreateCategoryMenuItem() {
        return createCategoryMenuItem;
    }

    public void setCreateCategoryMenuItem(MenuItem createCategoryMenuItem) {
        this.createCategoryMenuItem = createCategoryMenuItem;
    }

    public MenuItem getViewCategoriesMenuItem() {
        return viewCategoriesMenuItem;
    }

    public MenuItem getLogoutMenuItem(){
        return logoutMenuItem;
    }

    public String getEmail() {
        return email;
    }

    public Button getAddTransactionButton() {
        return addTransactionButton;
    }

    public VBox getRecentTransactionsBox() {
        return recentTransactionsBox;
    }

    public LoadingAnimationPane getLoadingAnimationPane(){
        return loadingAnimationPane;
    }
}
