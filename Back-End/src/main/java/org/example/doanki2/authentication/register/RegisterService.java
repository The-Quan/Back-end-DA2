package org.example.doanki2.authentication.register;

import org.example.doanki2.entity.Users;
import org.example.doanki2.model.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Service
public class RegisterService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder ;

    public ResponseEntity<Users> registerUser(Register register){
        Optional<Users> usersOptional = userRepository.findByUserName(register.getUsername());
        if (usersOptional.isPresent()){
           throw new IllegalArgumentException("user name already exists");
        }
        Optional<Users> emailOptional = userRepository.findByEmail(register.getEmail());
        if (emailOptional.isPresent()){
            throw new IllegalArgumentException("email already exists");
        }

        Users users = new Users();
        users.setUsername(register.getUsername());
        users.setPassword(passwordEncoder.encode(register.getPassword()));
        users.setEmail(register.getEmail());
        users.setPhone(register.getPhone());
        Users save = userRepository.save(users);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(save.getUsername()).toUri();
        return ResponseEntity.created(location).body(save);
    }
}
