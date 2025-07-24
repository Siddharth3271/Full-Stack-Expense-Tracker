package com.siddh.Expense_Tracker_Client.controller;

import com.siddh.Expense_Tracker_Client.Entity.Transaction;
import com.siddh.Expense_Tracker_Client.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionController{
    private static final Logger logger=Logger.getLogger(TransactionController.class.getName());

    @Autowired
    private TransactionService transactionService;

    //post
    @PostMapping
    public ResponseEntity<Transaction>createTransaction(@RequestBody Transaction transaction){
        logger.info("Creating the transaction");
        Transaction newTransaction=transactionService.createTransaction(transaction);
        if(newTransaction==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
