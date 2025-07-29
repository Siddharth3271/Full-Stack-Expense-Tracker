package com.siddh.Expense_Tracker_Client.controller;

import com.siddh.Expense_Tracker_Client.Entity.Transaction;
import com.siddh.Expense_Tracker_Client.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionController{
    private static final Logger logger=Logger.getLogger(TransactionController.class.getName());

    @Autowired
    private TransactionService transactionService;
    //get

    @GetMapping("/recent/user/{userId}")
    public ResponseEntity<List<Transaction>>getRecentTransactionsByUserId(@PathVariable int userId,@RequestParam int startPage, @RequestParam int endPage,@RequestParam int size){
        logger.info("Getting transactions for userId "+userId+",Page: ("+startPage+","+endPage+")");
        List<Transaction>recentTransactionList=transactionService.getRecentTransactionsByUserId(userId,startPage,endPage,size);

        return ResponseEntity.status(HttpStatus.OK).body(recentTransactionList);
    }

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

    //put
    @PutMapping
    public ResponseEntity<Transaction>updateTransaction(@RequestBody Transaction transaction){
        logger.info("Updating transaction with id: "+transaction.getId());
        Transaction updatedTransaction= transactionService.updateTransaction(transaction);

        if(updatedTransaction==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    //delete
    @DeleteMapping("/{transactionId}")
    public ResponseEntity<Transaction>deleteTransactionById(@PathVariable int transactionId){
        logger.info("Delete Transaction with id "+transactionId);

        transactionService.deleteTransactionById(transactionId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
