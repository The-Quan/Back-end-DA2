package org.example.doanki2.controller;

import org.example.doanki2.entity.Cart_items;
import org.example.doanki2.model.cart.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/cartitems/v1/")
@CrossOrigin(origins = "http://localhost:3000")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'USER')")
    public ResponseEntity<Cart_items> postCart(@RequestBody Cart_items cartItems){
        return cartItemService.postCart(cartItems);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'USER')")
    public ResponseEntity<Cart_items> deleteCart(@PathVariable Integer id){
        return cartItemService.deleteCart(id);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'USER')")
    public ResponseEntity<Cart_items> put(@PathVariable Integer id, @RequestBody Cart_items cartItems){
        return cartItemService.putCart(id, cartItems);
    }
}
