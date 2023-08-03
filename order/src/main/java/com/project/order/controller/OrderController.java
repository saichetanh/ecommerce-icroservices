package com.project.order.controller;

import com.project.order.dto.OrderDTO;
import com.project.order.model.Orders;
import com.project.order.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    //@CircuitBreaker(name = "inventory", fallbackMethod = "fallBackMethod")
    public String saveOrder(@RequestBody OrderDTO orderDTO) {
        return orderService.saveOrder(orderDTO);
    }

    @GetMapping
    public List<Orders> getAllOrder() {
        return orderService.getAllOrders();
    }

    public String fallBackMethod(OrderDTO orderDTO , RuntimeException runtimeException){
        return "fallBackMethod";
    }

}
