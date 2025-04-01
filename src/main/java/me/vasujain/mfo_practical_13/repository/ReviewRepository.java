package me.vasujain.mfo_practical_13.repository;

import me.vasujain.mfo_practical_13.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
}