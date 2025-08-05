package org.example.expensetrackerclient.Dialogs;

import com.google.gson.JsonObject;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.expensetrackerclient.Controller.DashBoardController;
import org.example.expensetrackerclient.Models.Transaction;
import org.example.expensetrackerclient.Models.TransactionCategory;
import org.example.expensetrackerclient.Models.User;
import org.example.expensetrackerclient.components.TransactionComponent;
import org.example.expensetrackerclient.utils.SQLUtil;
import org.example.expensetrackerclient.utils.Utlities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CreateOrEditTransactionDialog extends CustomDialog{

    private List<TransactionCategory>transactionCategories;
    private TextField transactionNameField,transactionAmountField;
    private DatePicker transactionDatePicker;
    private ComboBox<String>transactionCategoryBox;
    private ToggleGroup transactionTypeToggleGroup;
    private TransactionComponent transactionComponent;
    private boolean isEditing;
    private DashBoardController dashBoardController;
    public CreateOrEditTransactionDialog(DashBoardController dashBoardController, TransactionComponent transactionComponent, boolean isEditing) {
        super(dashBoardController.getUser());
        this.isEditing=isEditing;
        this.transactionComponent=transactionComponent;
        this.dashBoardController=dashBoardController;

        setTitle(isEditing?"Edit Transaction":"Create new Transaction");
        getDialogPane().setPrefSize(500, 400);
        transactionCategories= SQLUtil.getAllTransactionCategoryByUser(user);

        VBox mainContentBox=createMainContentBox();
        getDialogPane().setContent(mainContentBox);

    }

    //use for creating transactions
    public CreateOrEditTransactionDialog(DashBoardController dashBoardController,boolean isEditing){
        this(dashBoardController,null,isEditing);
    }



    private VBox createMainContentBox(){
        VBox mainContentBox=new VBox(25);
        mainContentBox.setAlignment(Pos.CENTER);

        transactionNameField=new TextField();
        transactionNameField.setPromptText("Enter Transaction Name");
        transactionNameField.getStyleClass().addAll("field-background","text-size-md","text-light-gray","rounded-border");

        transactionAmountField=new TextField();
        transactionAmountField.setPromptText("Enter Transaction Amount");
        transactionAmountField.getStyleClass().addAll("field-background","text-size-md","text-light-gray","rounded-border");

        transactionDatePicker=new DatePicker();
        transactionDatePicker.setPromptText("Enter Transaction Date");
        transactionDatePicker.getStyleClass().addAll("field-background","text-size-md","text-light-gray","rounded-border");
        transactionDatePicker.setMaxWidth(Double.MAX_VALUE);

        transactionCategoryBox=new ComboBox<>();
        transactionCategoryBox.setPromptText("Choose Category: ");
        transactionCategoryBox.getStyleClass().addAll("field-background","text-size-md","text-light-gray","rounded-border");
        transactionCategoryBox.setMaxWidth(Double.MAX_VALUE);
        for(TransactionCategory transactionCategory:transactionCategories){
            transactionCategoryBox.getItems().add(transactionCategory.getCategoryName());
        }

        if(isEditing){
            Transaction transaction=transactionComponent.getTransaction();
            transactionNameField.setText(transaction.getTransactionName());
            transactionAmountField.setText(String.valueOf(transaction.getTransactionAmount()));
            transactionDatePicker.setValue(transaction.getTransactionDate());
            transactionCategoryBox.setValue(
                    transaction.getTransactionCategory()==null?"":transaction.getTransactionCategory().getCategoryName()
            );
        }

        mainContentBox.getChildren().addAll(transactionNameField,transactionAmountField,transactionDatePicker,transactionCategoryBox,createTransactionTypeRadioButtons(),createConfirmCancelButtonBox());
        return mainContentBox;
    }

    private HBox createTransactionTypeRadioButtons(){
        HBox radioButtonBox=new HBox(40);
        radioButtonBox.setAlignment(Pos.CENTER);

        transactionTypeToggleGroup=new ToggleGroup();

        RadioButton incomeRadioButton=new RadioButton("Income");
        incomeRadioButton.setToggleGroup(transactionTypeToggleGroup);
        incomeRadioButton.getStyleClass().addAll("text-size-md","text-light-gray");

        RadioButton expenseRadioButton=new RadioButton("Expense");
        expenseRadioButton.setToggleGroup(transactionTypeToggleGroup);
        expenseRadioButton.getStyleClass().addAll("text-size-md","text-light-gray");

        if(isEditing){
            Transaction transaction=transactionComponent.getTransaction();
            if(transaction.getTransactionType().equalsIgnoreCase("income")){
                incomeRadioButton.setSelected(true);
            }
            else{
                expenseRadioButton.setSelected(true);
            }
        }

        radioButtonBox.getChildren().addAll(incomeRadioButton,expenseRadioButton);
        return radioButtonBox;
    }

    private HBox createConfirmCancelButtonBox(){
        HBox confirmAndCancelBox=new HBox(50);
        confirmAndCancelBox.setAlignment(Pos.CENTER);

        Button saveButton=new Button("Save");
        saveButton.setPrefWidth(150);
        saveButton.getStyleClass().addAll("bg-light-blue","text-white","text-size-md","rounded-border");
        saveButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                JsonObject transactionDataObject=new JsonObject();

                if(isEditing){
                    transactionDataObject.addProperty("id",transactionComponent.getTransaction().getId());
                }

                //extract data from the nodes
                String transactionName=transactionNameField.getText();
                transactionDataObject.addProperty("transactionName", transactionName);

                try {
                    // Proceed with saving logic
                    double transactionAmount=Double.parseDouble(transactionAmountField.getText());
                    transactionDataObject.addProperty("transactionAmount",transactionAmount);

                } catch (NumberFormatException ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid Input");
                    alert.setHeaderText("Invalid Transaction Amount");
                    alert.setContentText("Please enter a valid numeric value for the transaction amount.");
                    alert.showAndWait();
                    return;
                }
                LocalDate transactionDate=transactionDatePicker.getValue();
                if (transactionDate == null) {
                    Utlities.showAlertDialog(Alert.AlertType.ERROR, "Please select a date.");
                    return;
                }
//                transactionDataObject.addProperty("transactionDate",transactionDate.format(
//                        DateTimeFormatter.BASIC_ISO_DATE
//                ));
                transactionDataObject.addProperty("transactionDate",transactionDate.toString());
                String transactionType=((RadioButton)transactionTypeToggleGroup.getSelectedToggle()).getText();
                transactionDataObject.addProperty("transactionType",transactionType.toLowerCase());
                String transactionCategoryName=transactionCategoryBox.getValue();

                if(transactionCategoryName!=null){
                    TransactionCategory transactionCategory= Utlities.findTransactionCategoryByName(
                            transactionCategories,transactionCategoryName
                    );

                    if(transactionCategory!=null){
                        JsonObject transactionCategoryData=new JsonObject();
                        transactionCategoryData.addProperty("id",transactionCategory.getId());
                        transactionDataObject.add("transactionCategory",transactionCategoryData);
                    }
                }

                JsonObject userData=new JsonObject();
                userData.addProperty("id",user.getId());
                transactionDataObject.add("user",userData);

                System.out.println("Sending JSON: " + transactionDataObject.toString());

                //perform the post request to perform the transaction
                if(!isEditing?SQLUtil.postTransaction(transactionDataObject):SQLUtil.putTransaction(transactionDataObject)){
                    //display alert message
                    Utlities.showAlertDialog(Alert.AlertType.INFORMATION,isEditing?"Successfully saved transaction":"Successfully created Transaction");
                    //refresh our dashboard
                    dashBoardController.fetchUserData();

                    //refresh the transaction component
                    if(isEditing){
                        transactionComponent.getTransactionCategoryLabel().setText(
                                transactionCategoryName.isEmpty()?"Undefined":transactionCategoryName
                        );
                        transactionComponent.getTransactionNameLabel().setText(transactionName);
                        transactionComponent.getTransactionDateLabel().setText(transactionDate.toString());
                        transactionComponent.getTransactionAmountLabel().setText(String.valueOf(transactionAmountField));
                    }

                }

                else{
                    Utlities.showAlertDialog(Alert.AlertType.ERROR,isEditing?"Error: Failed to save transaction":"Error: Failed to create transaction...");
                }
            }
        });

        Button cancelButton=new Button("Cancel");
        cancelButton.setPrefWidth(150);
        cancelButton.getStyleClass().addAll("text-size-md","rounded-border","bg-light-red","text-white");
        cancelButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                CreateOrEditTransactionDialog.this.close();
            }
        });

        confirmAndCancelBox.getChildren().addAll(saveButton,cancelButton);

        return confirmAndCancelBox;
    }

}
