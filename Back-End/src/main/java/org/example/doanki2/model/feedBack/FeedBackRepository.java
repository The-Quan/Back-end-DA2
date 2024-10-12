package org.example.doanki2.model.feedBack;

import org.example.doanki2.entity.Feed_Back;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedBackRepository extends JpaRepository<Feed_Back, Integer> {
    @Query("select fb from Feed_Back fb where fb.products.product_id = :id")
    List<Feed_Back> findByProductId(@Param("id") int id);
}
