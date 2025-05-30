package com.federico.request;

import com.federico.domain.OrderType;

import lombok.Data;

@Data
public class CreateOrderRequest {
    private String coinId;
    private double quantity;
    private OrderType orderType;
}
