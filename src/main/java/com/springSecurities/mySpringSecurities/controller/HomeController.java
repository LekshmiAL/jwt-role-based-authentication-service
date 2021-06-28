package com.springSecurities.mySpringSecurities.controller;

import com.springSecurities.mySpringSecurities.model.UserAccount;
import com.springSecurities.mySpringSecurities.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

@RestController
public class HomeController {

    @Autowired
    private HomeService homeService;

    /*The PostConstruct annotation is used on a method that needs to be executed after
    dependency injection is done to perform any initialization.
    This method MUST be invoked before the class is put into service.*/
    @PostConstruct
    public void initRoleAndUser() {
        homeService.initRoleAndUser();
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('User')")
    public String helloUser() {
        return "Hello User";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('Admin')")
    public String helloAdmin() {
        return "Hello Admin";
    }

    @PostMapping({"/registerNewUser"})
    public UserAccount registerNewUser(@RequestBody UserAccount user){
        return homeService.registerNewUser(user);
    }

}
