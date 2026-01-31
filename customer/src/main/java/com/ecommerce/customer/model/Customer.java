package com.ecommerce.customer.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull()
    private String firstName;

    @NotNull()
    private String lastName;

    @NotNull
    @Column(unique = true)
    @Email()
    private String email;

    @OneToMany(mappedBy = "customer")
    private List<Address> adress;

    @NotNull
    private LocalDate createdDate;
}
