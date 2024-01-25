package dev.fklein.demoservice.repositories;

import dev.fklein.demoservice.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
