package com.federico.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.federico.domain.OrderType;
import com.federico.model.Coin;
import com.federico.model.Order;
import com.federico.model.OrderItem;
import com.federico.model.User;
import com.federico.repository.OrderRepository;

public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order createOrder(User user, OrderItem orderItem, OrderType orderType) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createOrder'");
    }

    @Override
    public Order getOrderById(Long orderId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOrderById'");
    }

    @Override
    public List<Order> getAllOrderOfUser(Long userId, OrderType orderType, String assetSymbol) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllOrderOfUser'");
    }

    @Override
    public Order processOrder(Coin coin, double quantity, OrderType orderType, User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'processOrder'");
    }
    
    
}
