package com.siddh.Expense_Tracker_Client.service;

import com.siddh.Expense_Tracker_Client.Entity.User;
import com.siddh.Expense_Tracker_Client.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class UserService {
    //debugging code facility in terminal
    private static final Logger logger=Logger.getLogger(UserService.class.getName());

    @Autowired
    private UserRepository userRepository;

    //get
    public Optional<User> getUserById(int userId){
        logger.info("Getting the user by id: "+userId);
        return userRepository.findById(userId);
    }

    public Optional<User>getUserByEmail(String email){
        logger.info("Getting usr by Email "+email);
        return userRepository.findByEmail(email);  //calling query
    }

    //post
    public User createUser(String name,String username,String password){
        User user=new User();
        user.setName(name);
        user.setPassword(password);
        user.setEmail(username);
        user.setCreatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }
}
