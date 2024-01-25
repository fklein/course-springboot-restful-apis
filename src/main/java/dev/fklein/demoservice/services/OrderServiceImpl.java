package dev.fklein.demoservice.services;

import dev.fklein.demoservice.entities.Order;
import dev.fklein.demoservice.entities.User;
import dev.fklein.demoservice.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void createOrder(Order order) {
        orderRepository.save(order);
    }

    @Override
    public Order getOrdersByUserAndId(Long userId, Long orderId) {
        return orderRepository.findByOrderIdAndUser_Id(orderId, userId);

        // This will also work:
//        User u = new User();
//        u.setId(userId);
//        return orderRepository.findByOrderIdAndUser(orderId, u);
    }
}
