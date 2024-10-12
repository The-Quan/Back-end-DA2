package org.example.doanki2.entity.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.doanki2.entity.Products;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order_Detail_List {

    private int order_detail_id;

    private int orders;

   private Products products;

   private int quantity;
}
