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
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    private UUID orderId;

    private String customerName;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Invoice invoice;

    @OneToMany
    @JoinColumn(name = "order_id") // Adds FK in product table
    private List<Product> productList;
}