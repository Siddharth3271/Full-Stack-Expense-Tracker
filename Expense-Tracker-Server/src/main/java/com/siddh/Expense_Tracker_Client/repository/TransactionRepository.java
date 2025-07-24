package com.siddh.Expense_Tracker_Client.repository;

import com.siddh.Expense_Tracker_Client.Entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,Integer> {

}
