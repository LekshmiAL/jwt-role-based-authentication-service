package com.springSecurities.mySpringSecurities.service;

import com.springSecurities.mySpringSecurities.model.JwtRequest;
import com.springSecurities.mySpringSecurities.model.JwtResponse;
import com.springSecurities.mySpringSecurities.model.UserAccount;
import com.springSecurities.mySpringSecurities.repository.UserRepository;
import com.springSecurities.mySpringSecurities.utility.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Service
public class JwtService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private JwtService jwtService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //Logic to get the user form the Database
       UserAccount userAccount= userRepository.findByUsername(username);
        if (userAccount != null) {
            return new User(userAccount.getUsername(), userAccount.getPassword(), getAuthorities(userAccount));
        }else{
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

    }

    private Set getAuthorities(UserAccount user){
        Set authorities = new HashSet();
        user.getRole().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
        });
        return authorities;
    }
    public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception{

        String userName = jwtRequest.getUsername();
        String userPassword = jwtRequest.getPassword();
        authenticate(userName, userPassword);

        //to generate token
        final UserDetails userDetails
                = jwtService.loadUserByUsername(userName);
        final String token =
                jwtUtility.generateToken(userDetails);

        //user details
        UserAccount user = userRepository.findById(userName).get();

        //response with user details and token
        return new JwtResponse(user,token);

    }

    private void authenticate(String userName, String userPassword) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userName, userPassword));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
