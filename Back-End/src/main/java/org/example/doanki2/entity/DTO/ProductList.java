package org.example.doanki2.entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.doanki2.entity.Author;
import org.example.doanki2.entity.Categories;
import org.example.doanki2.entity.ImgProduct;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductList {
    private int product_id;

    private String product_name;

    private Author author;

    private String description;

    private BigDecimal price;

    private Categories categories;

    private List<ImgProduct> imgProducts;

    private Timestamp created_at;

    private Timestamp updated_at;
}
