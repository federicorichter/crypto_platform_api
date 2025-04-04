package com.federico.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.federico.model.Coin;

public interface CoinRespository extends JpaRepository<Coin, String>{
    
}
