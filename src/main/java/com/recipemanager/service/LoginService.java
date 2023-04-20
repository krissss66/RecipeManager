package com.recipemanager.service;


import org.springframework.stereotype.Service;

@Service
public class LoginService {

    public boolean login(String username, String password){
        return username.equals("admin") && password.equals("admin");
    }
}
