package com.ecommerce.product.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.DefaultValue;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    private String name;

    private String description;

    @NotNull
    private Double availableQuantity;

    @NotNull
    private BigDecimal price;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @NotNull
    private LocalDate createdDate;
}
