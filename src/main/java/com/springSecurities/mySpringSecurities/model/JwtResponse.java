package com.springSecurities.mySpringSecurities.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {

    private UserAccount userAccount;
    private String jwtToken;
}
