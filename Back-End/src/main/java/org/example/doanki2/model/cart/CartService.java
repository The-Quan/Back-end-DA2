package org.example.doanki2.model.cart;

import org.example.doanki2.entity.*;
import org.example.doanki2.entity.DTO.Cart_item_list;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public ResponseEntity<List<Carts>> getall(){
        List<Carts> carts = cartRepository.findAll();
        return ResponseEntity.ok().body(carts.stream().map(this::carts).collect(Collectors.toList()));
    }

    public Carts carts (Carts carts){
        Carts carts1 = new Carts();
        carts1.setCart_id(carts.getCart_id());
        carts1.setUser(carts.getUser());
        carts1.setCart_Product((List) carts.getCart_Product().stream().map(this::cartItemList).collect(Collectors.toList()));
        carts1.setCreated_at(carts.getCreated_at());
        return carts1;
    }
    public Cart_item_list cartItemList (Cart_items cartItems){
        Cart_item_list cartItemList = new Cart_item_list();
        cartItemList.setCart_item_id(cartItems.getCart_items_id());
        cartItemList.setCart(cartItems.getCart().getCart_id());
        cartItemList.setProduct(cartItems.getProduct());
        cartItemList.setQuantity(cartItems.getQuantity());
        return cartItemList;
    }
    public  ResponseEntity<List<Carts>>  getById (int id){
        Optional<Carts> cartsOptional = cartRepository.findByUserId(id);
        if (!cartsOptional.isPresent()){
            throw new IllegalArgumentException("id user not found");
        }
        return ResponseEntity.ok().body(cartsOptional.stream().map(this::carts).collect(Collectors.toList()));
    }
    public ResponseEntity<Carts> postCart(Carts carts){
        Carts carts1 = cartRepository.save(carts);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(carts1.getCart_id()).toUri();
        return ResponseEntity.created(location).body(carts1);
    }
    public ResponseEntity<Carts> delete(int id){
        Optional<Carts> cartsOptional = cartRepository.findById(id);
        if (!cartsOptional.isPresent()){
            throw new IllegalArgumentException("id cart not found");
        }
        cartRepository.deleteById(cartsOptional.get().getCart_id());
        return ResponseEntity.noContent().build();
    }

}
