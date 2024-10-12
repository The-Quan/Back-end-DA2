package org.example.doanki2.controller;

import org.example.doanki2.email.EmailService;
import org.example.doanki2.entity.Orders;
import org.example.doanki2.model.orders.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order/v1/")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private EmailService emailService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'USER')")
    public ResponseEntity<List<Orders>> getAll(){
        return orderService.getAllOrder();
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Orders>> getById(@PathVariable Integer id){
        return orderService.getById(id);
    }
    @GetMapping("/userOrder/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'USER')")
    public ResponseEntity<List<Orders>> getByUserId(@PathVariable Integer id){
        return orderService.getByUserId(id);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'USER')")
    public ResponseEntity<Orders> post(@RequestBody Orders orders){
        Orders save = orderService.postOrder(orders).getBody();
        emailService.sendOrderConfirmationEmail(save);
        return ResponseEntity.ok(save);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'USER')")
    public ResponseEntity<Orders> put(@PathVariable Integer id, @RequestBody Orders orders){
        // Cập nhật đơn hàng
        Orders save = orderService.putOrder(id, orders).getBody();

        if (save != null) {
            // Gửi email thông báo trạng thái
            emailService.sendOrderStatusUpdateEmail(save);
        } else {
            // Nếu không cập nhật thành công, có thể log hoặc trả về lỗi
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok().body(save);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'USER')")
    public ResponseEntity<Orders> delete(@PathVariable Integer id){
        return orderService.deleteOrder(id);
    }
}
