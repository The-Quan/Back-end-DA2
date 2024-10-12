package org.example.doanki2.model.user;

import org.example.doanki2.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;


    public ResponseEntity<List<Users>> getAllUser(){
        List<Users> usersList = userRepository.findAll();
        return ResponseEntity.ok(usersList);
    }

    public ResponseEntity<Users> getByIdUser(int id){
        Optional<Users> usersOptional = userRepository.findById(id);
        if (!usersOptional.isPresent()){
            throw new IllegalArgumentException("id not found");
        }
        return ResponseEntity.ok(usersOptional.get());
    }
    public ResponseEntity<Users> getByName(){
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        Optional<Users> users = userRepository.findByUserName(name);
        if (!users.isPresent()){
            throw new  IllegalArgumentException("user name not found");
        }

        return ResponseEntity.ok().body(users.get());
    }
    public ResponseEntity<Users> post (Users users){
        Users userss = userRepository.save(users);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(userss.getUser_id()).toUri();
        return ResponseEntity.created(location).body(userss);
    }


    public ResponseEntity<Users> putUsers(int id, Users users){
        Optional<Users> usersOptional = userRepository.findById(id);
        if (!usersOptional.isPresent()){
            throw new IllegalArgumentException("id not found");
        }
        users.setUser_id(usersOptional.get().getUser_id());
        userRepository.save(users);
        return ResponseEntity.ok().body(users);
    }

    public ResponseEntity<Users> deleteUser(int id) {
        Optional<Users> usersOptional = userRepository.findById(id);
       if (!usersOptional.isPresent()){
           throw new IllegalArgumentException("id not found");
       }
       userRepository.deleteById(usersOptional.get().getUser_id());
       return ResponseEntity.noContent().build();
    }
}
