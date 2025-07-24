package com.siddh.Expense_Tracker_Client.service;

import com.siddh.Expense_Tracker_Client.Entity.Transaction;
import com.siddh.Expense_Tracker_Client.Entity.TransactionCategory;
import com.siddh.Expense_Tracker_Client.Entity.User;
import com.siddh.Expense_Tracker_Client.repository.TransactionCategoryRepository;
import com.siddh.Expense_Tracker_Client.repository.TransactionRepository;
import com.siddh.Expense_Tracker_Client.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
public class TransactionService {
    private static final Logger logger=Logger.getLogger(TransactionService.class.getName());

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionCategoryRepository transactionCategoryRepository;

    @Autowired
    private UserRepository userRepository;

    //post
    public Transaction createTransaction(Transaction transaction){
        logger.info("Creating transaction");

        //find category
        Optional<TransactionCategory>transactionCategoryOptional=Optional.empty();
        if(transaction.getTransactionCategory()!=null){
            logger.info(String.valueOf(transaction.getTransactionCategory().getId()));
            transactionCategoryOptional=transactionCategoryRepository.findById(transaction.getTransactionCategory().getId());
        }

        //find user
        User user=userRepository.findById(transaction.getUser().getId()).get();

        //create the new transaction object
        Transaction newTransaction=new Transaction();
        newTransaction.setTransactionCategory(transactionCategoryOptional.isEmpty()?null:transactionCategoryOptional.get());
        newTransaction.setUser(user);
        newTransaction.setTransactionName(transaction.getTransactionName());
        newTransaction.setTransactionAmount(transaction.getTransactionAmount());
        newTransaction.setTransactionDate(transaction.getTransactionDate());
        newTransaction.setTransactionType(transaction.getTransactionType());

        return transactionRepository.save(newTransaction);
    }
}
