package org.example.doanki2.news;

import org.example.doanki2.entity.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<News_Book, Integer> {
    @Query("SELECT n FROM News_Book n WHERE n.approved = true")
    List<News_Book> findByApproved();

    @Query("SELECT n FROM News_Book n WHERE n.approved = false ")
    List<News_Book> findByApprovedFalse();
}

