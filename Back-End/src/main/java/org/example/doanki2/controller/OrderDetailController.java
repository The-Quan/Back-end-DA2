package org.example.doanki2.controller;

import org.example.doanki2.entity.Order_Details;
import org.example.doanki2.model.orderDetail.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orderdetail/v1/")
@CrossOrigin(origins = "http://localhost:3000")

public class OrderDetailController {
    @Autowired
    OrderDetailService orderDetailService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'USER')")
    public ResponseEntity<List<Order_Details>> getAll() {
        return orderDetailService.getAllOrderDetail();
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'USER')")
    public ResponseEntity<List<Order_Details>> getByIdOrderDetail (@PathVariable Integer id){
        return orderDetailService.getById(id);
    }
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'USER')")
    public ResponseEntity<Order_Details> postOrderDetail (@RequestBody Order_Details orderDetails){
        return orderDetailService.post(orderDetails);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'USER')")
    public ResponseEntity<Order_Details> putOrderDetail(@PathVariable Integer id, @RequestBody Order_Details orderDetails){
        return orderDetailService.put(id, orderDetails);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'USER')")
    public ResponseEntity<Order_Details> orderDetails (@PathVariable Integer id){
        return orderDetailService.delete(id);
    }
}
