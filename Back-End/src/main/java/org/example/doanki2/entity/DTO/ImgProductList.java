package org.example.doanki2.entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.doanki2.entity.Products;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImgProductList {
    private int img_id;

    private String img_name;

    private String img_url;

    private Products products;

}
