package com.federico.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.federico.model.VerificationCode;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long>{
    
    public VerificationCode findByUserId(Long userId);
}
