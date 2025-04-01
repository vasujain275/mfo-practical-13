package me.vasujain.mfo_practical_13.repository;

import me.vasujain.mfo_practical_13.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
}