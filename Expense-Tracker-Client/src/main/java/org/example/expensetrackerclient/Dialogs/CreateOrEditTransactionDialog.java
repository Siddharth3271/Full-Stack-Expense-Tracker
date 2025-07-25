package org.example.expensetrackerclient.Dialogs;

import javafx.geometry.Pos;
import javafx.scene.control.*;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.expensetrackerclient.Models.TransactionCategory;
import org.example.expensetrackerclient.Models.User;
import org.example.expensetrackerclient.utils.SQLUtil;

import java.util.List;

public class CreateOrEditTransactionDialog extends CustomDialog{

    private List<TransactionCategory>transactionCategories;
    private TextField transactionNameField,transactionAmountField;
    private DatePicker transactionDatePicker;
    private ComboBox<String>transactionCategoryBox;
    private ToggleGroup transactionTypeToggleGroup;

    private boolean isEditing;

    public CreateOrEditTransactionDialog(User user,boolean isEditing) {
        super(user);
        this.isEditing=isEditing;

        setTitle(isEditing?"Edit Transaction":"Create new Transaction");
        getDialogPane().setPrefSize(500, 400);
        transactionCategories= SQLUtil.getAllTransactionCategoryByUser(user);

        VBox mainContentBox=createMainContentBox();
        getDialogPane().setContent(mainContentBox);

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

        radioButtonBox.getChildren().addAll(incomeRadioButton,expenseRadioButton);
        return radioButtonBox;
    }

    private HBox createConfirmCancelButtonBox(){
        HBox confirmAndCancelBox=new HBox(50);
        confirmAndCancelBox.setAlignment(Pos.CENTER);

        Button saveButton=new Button("Save");
        saveButton.setPrefWidth(150);
        saveButton.getStyleClass().addAll("bg-light-blue","text-white","text-size-md","rounded-border");

        Button cancelButton=new Button("Cancel");
        cancelButton.setPrefWidth(150);
        cancelButton.getStyleClass().addAll("text-size-md","rounded-border","bg-light-red","text-white");

        confirmAndCancelBox.getChildren().addAll(saveButton,cancelButton);

        return confirmAndCancelBox;
    }
}
