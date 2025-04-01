package me.vasujain.mfo_practical_13.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    private UUID productId;

    private String productName;
    private Double price;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Review> reviews;
}