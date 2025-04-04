package com.federico.repository;

import com.federico.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long>{
    Order findByUserId(Long userId);
}
