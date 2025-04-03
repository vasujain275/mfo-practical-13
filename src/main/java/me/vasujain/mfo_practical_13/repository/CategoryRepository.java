package me.vasujain.mfo_practical_13.repository;

import me.vasujain.mfo_practical_13.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
