package org.example.doanki2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.doanki2.authentication.login.Role;

import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@Getter
@Setter
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_id;

    private String username;

    @JsonIgnore
    private String password;

    private String email;

    private String phone;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    private Timestamp created_at;

    public Users(){
        this.role = new Role();
        this.role.setRole_id(2);
    }
}
