package com.siddh.Expense_Tracker_Client.controller;


import com.siddh.Expense_Tracker_Client.Entity.User;
import com.siddh.Expense_Tracker_Client.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private static final Logger logger=Logger.getLogger(UserService.class.getName());
    @Autowired
    private UserService userService;

//    @GetMapping
//    public ResponseEntity<User>getUserById(@RequestParam int userId){
//        logger.info("Getting user by id: "+userId);
//
//        Optional<User> userOptional=userService.getUserById(userId);
//
//        //if user not found
//        if(userOptional.isEmpty()){
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//        else{
//            //return user object
//            return ResponseEntity.status(HttpStatus.OK).body(userOptional.get());
//        }
//    }

    @GetMapping
    public ResponseEntity<User>getUserByEmail(@RequestParam String email){
        logger.info("Getting User by Email "+email);

        Optional<User>userOptional=userService.getUserByEmail(email);
        if(userOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        else{
            //return user object
            return ResponseEntity.status(HttpStatus.OK).body(userOptional.get());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<User>loginUser(@RequestParam String email, @RequestParam String password){
        //finding the user data that contains the given email

        Optional<User>userOptional=userService.getUserByEmail(email);

        if(userOptional.isEmpty()){
            //user not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        //checking match for password
        if(!password.equals(userOptional.get().getPassword())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping
    public ResponseEntity<User>createUser(@RequestBody User user){
        logger.info("Creating New User");
        User newUser=userService.createUser(
                user.getName(), user.getEmail(),user.getPassword()
        );

        return ResponseEntity.status(HttpStatus.OK).body(newUser);
    }
}
