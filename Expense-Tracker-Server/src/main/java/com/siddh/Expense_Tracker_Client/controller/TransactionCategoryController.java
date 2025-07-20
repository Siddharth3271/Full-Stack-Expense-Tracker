package com.siddh.Expense_Tracker_Client.controller;

import com.siddh.Expense_Tracker_Client.Entity.TransactionCategory;
import com.siddh.Expense_Tracker_Client.service.TransactionCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/transaction-category")
public class TransactionCategoryController {
    private static final Logger logger=Logger.getLogger(TransactionCategoryController.class.getName());

    @Autowired
    TransactionCategoryService transactionCategoryService;
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
