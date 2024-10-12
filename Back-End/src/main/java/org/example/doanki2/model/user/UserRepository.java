package org.example.doanki2.model.user;
import org.example.doanki2.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
    @Query("SELECT u FROM Users u WHERE u.username = :name")
    Optional<Users> findByUserName(@Param("name") String name);

    @Query("select u from Users u where u.email = :email")
    Optional<Users> findByEmail(@Param("email") String email);
}

