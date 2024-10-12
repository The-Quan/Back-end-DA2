package org.example.doanki2.model.cart;

import org.example.doanki2.entity.Carts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Carts, Integer> {
    @Query("SELECT c FROM Carts c WHERE c.user.user_id = :userId")
    Optional<Carts> findByUserId(@Param("userId") int userId);
}
