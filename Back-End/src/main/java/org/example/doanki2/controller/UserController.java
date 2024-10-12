package org.example.doanki2.controller;


import jakarta.validation.Valid;
import org.example.doanki2.entity.Users;
import org.example.doanki2.model.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/v1/")
@CrossOrigin(origins = "http://localhost:3000")

public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<Users>> getAllUser(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("UserName: {}", authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));
        return userService.getAllUser();
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<Users> post (@RequestBody Users users){
        return userService.post(users);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<Users> getByIdUser(@PathVariable Integer id){
        return userService.getByIdUser(id);
    }

    @GetMapping("/myinfo")
    @PostAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Users> getByName() {
        return userService.getByName();
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'USER')")
    public ResponseEntity<Users> putUser(@PathVariable Integer id, @RequestBody Users users){

        return userService.putUsers(id, users);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<Users> deleteUser (@PathVariable Integer id){
        return userService.deleteUser(id);
    }
}
