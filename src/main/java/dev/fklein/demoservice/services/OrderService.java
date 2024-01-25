package dev.fklein.demoservice.services;

import dev.fklein.demoservice.entities.Order;
import dev.fklein.demoservice.repositories.OrderRepository;

public interface OrderService {

    void createOrder(Order order);
}
