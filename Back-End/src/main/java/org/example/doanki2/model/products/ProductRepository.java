package org.example.doanki2.model.products;

import org.example.doanki2.entity.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Products, Integer>{
    @Query("SELECT p FROM Products p WHERE p.is_deleted = true")
    Page<Products> findByIsDeleted(Pageable pageable);

    @Query("SELECT p FROM Products p WHERE p.is_deleted = false ")
    Page<Products> findByIsDeletedFalse(Pageable pageable);
}
