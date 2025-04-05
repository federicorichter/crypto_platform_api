package com.federico.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.federico.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    
}
