package org.example.doanki2.model.img_product;

import org.example.doanki2.entity.ImgProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImgProductRepository extends JpaRepository<ImgProduct, Integer> {
}
