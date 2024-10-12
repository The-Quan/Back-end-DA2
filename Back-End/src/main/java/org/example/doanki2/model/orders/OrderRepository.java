package org.example.doanki2.model.orders;

import org.example.doanki2.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Integer> {
    @Query("select o from Orders o where o.user.user_id = :userId")
    List<Orders> findByUserId (@Param ("userId") int userId);
}
