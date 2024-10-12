package org.example.doanki2.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class Cart_items {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cart_items_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_id")
    private Carts cart;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Products product;

    private int quantity;
}
