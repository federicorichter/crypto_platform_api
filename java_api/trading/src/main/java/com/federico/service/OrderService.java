package com.federico.service;

import java.util.List;

import com.federico.domain.OrderType;
import com.federico.model.Coin;
import com.federico.model.Order;
import com.federico.model.OrderItem;
import com.federico.model.User;

public interface OrderService {
    
    Order createOrder(User user, OrderItem orderItem, OrderType orderType);

    Order getOrderById(Long orderId) throws Exception;

    List<Order> getAllOrderOfUser(Long userId, OrderType orderType, String assetSymbol);

    Order processOrder(Coin coin, double quantity, OrderType orderType, User user) throws Exception;
}
