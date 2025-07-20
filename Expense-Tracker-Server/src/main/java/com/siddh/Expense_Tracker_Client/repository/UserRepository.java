package com.siddh.Expense_Tracker_Client.repository;

import com.siddh.Expense_Tracker_Client.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


//performing crud operations
public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User>findByEmail(String email);

    void deleteByEmail(String email);
}
