package me.vasujain.mfo_practical_13.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    private UUID reviewId;

    private String comment;
    private int rating;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
