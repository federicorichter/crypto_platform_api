package com.federico.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.federico.domain.OrderType;
import com.federico.model.Coin;
import com.federico.model.Order;
import com.federico.model.User;
import com.federico.request.CreateOrderRequest;
import com.federico.service.CoinService;
import com.federico.service.OrderService;
import com.federico.service.UserService;
import com.federico.service.WalletService;

@RestController
@RequestMapping("api/orders")
public class OrderController {
    
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;
    
    @Autowired
    private CoinService coinService;

    //private WalletService

    @PostMapping("/pay")
    public ResponseEntity<Order> payOrderPayment(
        @RequestHeader("Authorization") String jwt,
        @RequestBody CreateOrderRequest req
    ) throws Exception{

        User user = userService.findUserByJwt(jwt);
        Coin coin = coinService.findById(req.getCoinId());

        Order order = orderService.processOrder(coin, req.getQuantity(), req.getOrderType(), user);

        return ResponseEntity.ok(order);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(
        @RequestHeader("Authorization") String jwt,
        @PathVariable Long orderId
    )throws Exception {

        if(jwt == null){
            throw new Exception("jwt token null");
        }

        User user = userService.findUserByJwt(jwt);

        Order order = orderService.getOrderById(orderId);

        if(order.getUser().getId().equals(user.getId())){
            return ResponseEntity.ok(order);
        }
        else{
            throw new Exception("ypu dont have access");
        }
    }

    @GetMapping()
    public ResponseEntity<List<Order>> getAllOrdersForUser(
        @RequestHeader("Authorization") String jwt,
        @RequestParam(required = false) OrderType order_type,
        @RequestParam(required = false) String asset_symbol
    ) throws Exception {

        Long userId = userService.findUserByJwt(jwt).getId();

        List<Order> userOrders = orderService.getAllOrderOfUser(userId, order_type, asset_symbol);

        return ResponseEntity.ok(userOrders);
    }

}
