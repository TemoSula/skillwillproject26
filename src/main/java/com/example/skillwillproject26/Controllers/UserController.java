package com.example.skillwillproject26.Controllers;

import com.example.skillwillproject26.Models.UserModel;
import com.example.skillwillproject26.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {


    @Autowired
    UserService userService;

    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password)
    {
        if(!username.equals("temo") && password.equals("temotemo123"))
        {
            throw new RuntimeException("user it not correct");
        }
        return userService.generateToken(username,password);
    }

    @GetMapping("Hello")
    public String whoIsInSystem()
    {
        UserModel um = (UserModel) SecurityContextHolder.getContext().getAuthentication();
       return um.getName();
    }

}
