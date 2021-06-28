package com.springSecurities.mySpringSecurities.controller;

import com.springSecurities.mySpringSecurities.model.JwtRequest;
import com.springSecurities.mySpringSecurities.model.JwtResponse;
import com.springSecurities.mySpringSecurities.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtController {

    @Autowired
    private JwtService jwtService;

    @PostMapping("/authenticate")
    public JwtResponse authenticate(@RequestBody JwtRequest jwtRequest) throws Exception{
        return jwtService.createJwtToken(jwtRequest);
    }
}
