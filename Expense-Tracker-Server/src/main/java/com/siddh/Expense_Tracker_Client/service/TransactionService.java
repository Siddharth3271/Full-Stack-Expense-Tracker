package com.siddh.Expense_Tracker_Client.service;

import com.siddh.Expense_Tracker_Client.Entity.Transaction;
import com.siddh.Expense_Tracker_Client.Entity.TransactionCategory;
import com.siddh.Expense_Tracker_Client.Entity.User;
import com.siddh.Expense_Tracker_Client.repository.TransactionCategoryRepository;
import com.siddh.Expense_Tracker_Client.repository.TransactionRepository;
import com.siddh.Expense_Tracker_Client.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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

    //get
    public List<Transaction>getRecentTransactionsByUserId(int userId,int startPage,int endPage,int size){
        logger.info("Getting the most recent Transactions for user "+userId);

        List<Transaction>combinedResults=new ArrayList<>();

        for(int page=startPage;page<=endPage;page++){
            Pageable pageable= PageRequest.of(page,size);
            List<Transaction>pageResults=transactionRepository.findAllByUserIdOrderByTransactionDateDesc(
                    userId,pageable
            );
            combinedResults.addAll(pageResults);
        }
        return combinedResults;
    }

    public List<Transaction>getAllTransactionsByUserIdAndYear(int userId,int year){
        logger.info("Getting all transaction in year: "+year+" for user: "+userId);
        LocalDate startDate=LocalDate.of(year,1,1);
        LocalDate endDate=LocalDate.of(year,12,31);

        return transactionRepository.findAllByUserIdAndTransactionDateBetweenOrderByTransactionDateDesc(
                userId,startDate,endDate
        );
    }
    public List<Transaction>getAllTransactionsByUserIdAndYearAndMonth(int userId,int year,int month){
        logger.info("Getting all transaction in month: "+month+"and year: "+year+" for user: "+userId);
        LocalDate startDate=LocalDate.of(year,month,1);
        LocalDate endDate=LocalDate.of(year,month,31);

        return transactionRepository.findAllByUserIdAndTransactionDateBetweenOrderByTransactionDateDesc(
                userId,startDate,endDate
        );
    }

    public List<Integer>getDistinctTransactionYears(int userId){
        return transactionRepository.findDistinctYears(userId);
    }

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

    //put
    public Transaction updateTransaction(Transaction transaction){
        logger.info("Updating transaction with id: "+transaction.getId());
        Optional<Transaction>transactionOptional=transactionRepository.findById(transaction.getId());

        if(transactionOptional.isEmpty()) return null;

        transactionRepository.save(transaction);

        return transaction;
    }

    //delete
    public void deleteTransactionById(int transactionId){
        logger.info("Deleting Transaction with id "+transactionId);
        Optional<Transaction>transactionOptional=transactionRepository.findById(transactionId);

        if(transactionOptional.isEmpty()) return;

        transactionRepository.delete(transactionOptional.get());
    }
}
