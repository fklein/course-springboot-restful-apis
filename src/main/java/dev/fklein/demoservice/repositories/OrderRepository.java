package dev.fklein.demoservice.repositories;

import dev.fklein.demoservice.entities.Order;
import dev.fklein.demoservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findByOrderIdAndUser_Id(Long id, Long user);
    Order findByOrderIdAndUser(Long id, User user);
}
