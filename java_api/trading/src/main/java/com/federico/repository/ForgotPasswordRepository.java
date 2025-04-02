package com.federico.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.federico.model.ForgotPasswordToken;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPasswordToken, String>{
    ForgotPasswordToken findByUserId(Long userId);
}
