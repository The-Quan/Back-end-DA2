package org.example.doanki2.entity.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.doanki2.entity.Cart_items;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Cart_list {

    private int cart_id;


    private int user;

    private List<Cart_items> Cart_Product = new ArrayList<>();

    private Timestamp created_at;
}
