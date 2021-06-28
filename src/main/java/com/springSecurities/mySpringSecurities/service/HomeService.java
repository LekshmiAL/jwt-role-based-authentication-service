package com.springSecurities.mySpringSecurities.service;

import com.springSecurities.mySpringSecurities.model.Role;
import com.springSecurities.mySpringSecurities.model.UserAccount;
import com.springSecurities.mySpringSecurities.repository.RoleRepository;
import com.springSecurities.mySpringSecurities.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class HomeService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void initRoleAndUser() {
        Role adminRole = new Role();
        adminRole.setRoleName("Admin");
        adminRole.setRoleDescription("Admin role");
        roleRepository.save(adminRole);

        Role userRole = new Role();
        userRole.setRoleName("User");
        userRole.setRoleDescription("Default role for newly created record");
        roleRepository.save(userRole);

        UserAccount adminUser = new UserAccount();
        adminUser.setUsername("admin123");
        adminUser.setUserFirstName("admin");
        adminUser.setUserLastName("admin");
        adminUser.setPassword(getEncodedPassword("admin@pass"));
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminUser.setRole(adminRoles);
        userRepository.save(adminUser);

    }

    public UserAccount registerNewUser(UserAccount user) {
        Role role = roleRepository.findById("User").get();
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(role);
        user.setRole(userRoles);
        user.setPassword(getEncodedPassword(user.getPassword()));

        return userRepository.save(user);
    }

    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
