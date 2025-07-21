package com.siddh.Expense_Tracker_Client.repository;

import com.siddh.Expense_Tracker_Client.Entity.TransactionCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionCategoryRepository extends JpaRepository<TransactionCategory,Integer> {
    List<TransactionCategory> findAllByUserId(int userId);



}
