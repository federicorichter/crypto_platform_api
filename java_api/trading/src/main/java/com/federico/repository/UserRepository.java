package com.federico.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.federico.model.User;

public interface UserRepository  extends JpaRepository<User, Long>{

    User findByEmail(String email);

    

    
} 
