package com.siddh.Expense_Tracker_Client.repository;

import com.siddh.Expense_Tracker_Client.Entity.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Integer> {
    //get the data by page
    List<Transaction>findAllByUserIdOrderByTransactionDateDesc(int userId, Pageable pageable);
}
