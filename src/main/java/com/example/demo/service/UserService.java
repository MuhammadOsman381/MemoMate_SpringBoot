package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.example.demo.config.SecurityConfig;
import com.example.demo.model.UserModel;
import com.example.demo.repository.UserRepo;
import com.example.demo.resources.JWT;

@Component
public class UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private JWT jwt;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private SecurityConfig securityConfig;

    public Boolean addNewUser(UserModel user) {
        user.setPassword(securityConfig.hashPassword(user.getPassword()));
        Query query = new Query();
        query.addCriteria(new Criteria().orOperator(
                Criteria.where("name").is(user.getName())));
        UserModel userData = mongoTemplate.findOne(query, UserModel.class);
        if (userData != null) {
            return (false);
        }
        userRepo.save(user);
        return true;
    }

    public String signInUser(UserModel userData) {
        try {
            Query query = new Query();
            query.addCriteria(new Criteria().orOperator(
                    Criteria.where("name").is(userData.getName())));
            UserModel user = mongoTemplate.findOne(query, UserModel.class);
            if (user == null) {
                return "User not found";
            }
            Boolean isPasswordCorrect = securityConfig.checkPassword(userData.getPassword(), user.getPassword());
            if (!isPasswordCorrect) {
                return "Password is incorrect";
            }
            return jwt.generateToken(user.getId().toString());
        } catch (NullPointerException e) {
            System.out.println("NullPointerException: " + e.getMessage());
            return "Error: User data is missing or incorrect.";
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return "An unexpected error occurred during sign-in.";
        }
    }

}
