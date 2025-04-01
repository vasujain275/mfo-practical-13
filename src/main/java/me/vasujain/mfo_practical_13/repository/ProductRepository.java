package me.vasujain.mfo_practical_13.repository;

import me.vasujain.mfo_practical_13.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
}
