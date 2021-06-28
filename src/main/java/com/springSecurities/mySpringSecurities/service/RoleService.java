package com.springSecurities.mySpringSecurities.service;

import com.springSecurities.mySpringSecurities.model.Role;
import com.springSecurities.mySpringSecurities.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role createNewRole(Role role){
        return roleRepository.save(role);
    }
}
