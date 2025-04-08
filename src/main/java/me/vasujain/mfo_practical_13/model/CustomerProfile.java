package me.vasujain.mfo_practical_13.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "customer_profile")
@Getter
@Setter
@NoArgsConstructor
public class CustomerProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    public String fullName;

    public String email;

    public String location;
}
