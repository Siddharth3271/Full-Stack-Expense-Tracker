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


}
