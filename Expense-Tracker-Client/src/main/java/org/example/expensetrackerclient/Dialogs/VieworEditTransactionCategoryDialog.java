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
        this.dashBoardController=dashBoardController;
        setTitle("View Categories");
        setWidth(800);
        setHeight(800);

        ScrollPane mainContainer=createMainContainerContent();
        getDialogPane().setContent(mainContainer);
    }

    private ScrollPane createMainContainerContent(){
        VBox dialogVBox=new VBox(20);
        ScrollPane scrollPane=new ScrollPane(dialogVBox);
        scrollPane.setFitToWidth(true);


        List<TransactionCategory>transactionCategories= SQLUtil.getAllTransactionCategoryByUser(user);

        for(TransactionCategory transactionCategory:transactionCategories){
            CategoryComponent categoryComponent=new CategoryComponent(dashBoardController,transactionCategory);

            dialogVBox.getChildren().add(categoryComponent);
        }
        return scrollPane;
    }


}
