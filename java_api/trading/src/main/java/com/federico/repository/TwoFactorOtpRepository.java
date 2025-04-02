package com.federico.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.federico.model.TwoFactorOTP;

public interface TwoFactorOtpRepository extends JpaRepository<TwoFactorOTP, String>{
    
    TwoFactorOTP findByUserId(Long userId);
}
