package org.example.doanki2.authentication.register;

import org.example.doanki2.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/register")
@CrossOrigin(origins = "http://localhost:3000")
public class RegisterController {
    @Autowired
    private RegisterService registerService;

    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody Register users){
        try {
            Users response = registerService.registerUser(users).getBody();
            return ResponseEntity.ok(response); // Trả về phản hồi thành công với JWT token
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage()); // Trả về lỗi xác thực
        }
    }
}
