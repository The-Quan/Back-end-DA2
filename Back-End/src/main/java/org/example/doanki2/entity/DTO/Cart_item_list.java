package org.example.doanki2.entity.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.doanki2.entity.Products;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart_item_list {

    private int cart_item_id;

    private int cart;

    private Products product;

    private int quantity;

    private BigDecimal total_price;
}
