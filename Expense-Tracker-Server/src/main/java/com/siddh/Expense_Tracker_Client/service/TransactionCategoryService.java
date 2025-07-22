package com.siddh.Expense_Tracker_Client.service;

import com.siddh.Expense_Tracker_Client.Entity.TransactionCategory;
import com.siddh.Expense_Tracker_Client.Entity.User;
import com.siddh.Expense_Tracker_Client.repository.TransactionCategoryRepository;
import com.siddh.Expense_Tracker_Client.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class TransactionCategoryService {
    private static final Logger logger=Logger.getLogger(TransactionCategoryService.class.getName());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionCategoryRepository transactionCategoryRepository;

    //get
    public Optional<TransactionCategory>getTransactionCategoryById(int id){
        logger.info("Getting Transaction Category by ID :"+id);
        return transactionCategoryRepository.findById(id);
    }

    public List<TransactionCategory>getAllTransactionCategoriesByUserId(int userId){
        logger.info("Getting all transaction Categories from user: "+userId);
        return transactionCategoryRepository.findAllByUserId(userId);
    }

    //post
    public TransactionCategory createTransactionCategory(int userId,String categoryName,String categoryColor){
        logger.info("Create transaction category with user: "+userId);

        //find the user with userid
        Optional<User>user=userRepository.findById(userId);

        if(user.isEmpty()) return null;

        TransactionCategory transactionCategory=new TransactionCategory();
        transactionCategory.setUser(user.get());
        transactionCategory.setCategoryName(categoryName);
        transactionCategory.setCategoryColor(categoryColor);

        return transactionCategoryRepository.save(transactionCategory);
    }

    //update category(put)
    public TransactionCategory updateTransactionCategoryById(int transactionCategoryId, String newCategoryName, String newCategoryColor){
        logger.info("Updating Transaction Category with ID :"+transactionCategoryId);
        Optional<TransactionCategory>optionalTransactionCategory=transactionCategoryRepository.findById(transactionCategoryId);

        if(optionalTransactionCategory.isEmpty()){
            return null;
        }
        TransactionCategory updatedTransactionCategory=optionalTransactionCategory.get();
        updatedTransactionCategory.setCategoryName(newCategoryName);
        updatedTransactionCategory.setCategoryColor(newCategoryColor);

        return transactionCategoryRepository.save(updatedTransactionCategory);
    }

    //delete
    public boolean deleteTransactionCategoryById(int transactionCategoryById){
        logger.info("Deleting transaction Category: "+transactionCategoryById);

        Optional<TransactionCategory>transactionCategoryOptional=transactionCategoryRepository.findById(transactionCategoryById);

        if(transactionCategoryOptional.isEmpty()){
            return false;
        }

        transactionCategoryRepository.delete(transactionCategoryOptional.get());
        return true;
    }

}
