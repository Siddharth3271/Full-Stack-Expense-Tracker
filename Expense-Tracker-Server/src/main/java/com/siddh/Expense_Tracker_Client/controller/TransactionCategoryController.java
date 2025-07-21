package com.siddh.Expense_Tracker_Client.controller;

import com.siddh.Expense_Tracker_Client.Entity.TransactionCategory;
import com.siddh.Expense_Tracker_Client.service.TransactionCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/transaction-category")
public class TransactionCategoryController {
    private static final Logger logger=Logger.getLogger(TransactionCategoryController.class.getName());

    @Autowired
    TransactionCategoryService transactionCategoryService;

    //get
    @GetMapping("/user/{userId}")     //here {} means value inside {} is extracted and is putted into the argument of below function
    public ResponseEntity<List<TransactionCategory>>getAllTransactionCategoriesByUserId(@PathVariable int userId){
        logger.info("Getting all transaction categories from user: "+userId);

        List<TransactionCategory>transactionCategories=transactionCategoryService.getAllTransactionCategoriesByUserId(userId);

        return ResponseEntity.status(HttpStatus.OK).body(transactionCategories);
    }

    @PostMapping
    public ResponseEntity<TransactionCategory>createTransactionCategory(@RequestBody TransactionCategory transactionCategory){
        logger.info("Create Transaction Category for: "+transactionCategory.getCategoryName());
        transactionCategoryService.createTransactionCategory(
                transactionCategory.getUser().getId(),
                transactionCategory.getCategoryName(),
                transactionCategory.getCategoryColor()
        );

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
