package dev.fklein.demoservice.services;

import dev.fklein.demoservice.entities.Order;
import dev.fklein.demoservice.repositories.OrderRepository;

import java.util.List;

public interface OrderService {

    void createOrder(Order order);
    Order getOrdersByUserAndId(Long userId, Long orderId);
}
