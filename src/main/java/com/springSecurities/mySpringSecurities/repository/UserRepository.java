package com.springSecurities.mySpringSecurities.repository;

import com.springSecurities.mySpringSecurities.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserAccount,String> {

         public UserAccount findByUsername(String username);

}
