package com.federico.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.federico.model.Wallet;


public interface WalletRepository extends JpaRepository<Wallet, Long> {
    
    Wallet findWalletByUserId(Long userId);
    
}
