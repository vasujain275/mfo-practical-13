package me.vasujain.mfo_practical_13.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "invoice")
public class Invoice {
    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    private UUID invoiceId;

    private String billingAddress;
    private LocalDate invoiceDate;

    @OneToOne
    @JoinColumn(name = "order_id") // FK to Order
    private Order order;
}