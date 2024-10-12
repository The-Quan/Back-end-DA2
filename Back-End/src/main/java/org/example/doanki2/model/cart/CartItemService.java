package org.example.doanki2.model.cart;

import org.example.doanki2.entity.Cart_items;
import org.example.doanki2.entity.Carts;
import org.example.doanki2.entity.Products;
import org.example.doanki2.model.products.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    public ResponseEntity<Cart_items> postCart(Cart_items cartItems) {
        Cart_items save = cartItemRepository.save(cartItems);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(save.getCart_items_id()).toUri();
        return ResponseEntity.created(location).body(save);
    }

    public ResponseEntity<Cart_items> deleteCart(int id) {
        Optional<Cart_items> cartItems = cartItemRepository.findById(id);
        if (!cartItems.isPresent()) {
            throw new IllegalArgumentException("Cart item not found");
        }
        cartItemRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Cart_items> putCart(int id, Cart_items cartItems) {
        Optional<Cart_items> existingCartItem = cartItemRepository.findById(id);
        if (!existingCartItem.isPresent()) {
            throw new IllegalArgumentException("Cart item not found");
        }
        if (cartItems.getCart() == null) {
            throw new IllegalArgumentException("Cart cannot be null");
        }
        if (cartItems.getProduct() == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        Optional<Carts> cartsOptional = cartRepository.findById(cartItems.getCart().getCart_id());
        if (!cartsOptional.isPresent()) {
            throw new IllegalArgumentException("Cart not found");
        }
        Optional<Products> productsOptional = productRepository.findById(cartItems.getProduct().getProduct_id());
        if (!productsOptional.isPresent()) {
            throw new IllegalArgumentException("Product not found");
        }
        Cart_items updatedCartItem = existingCartItem.get();
        updatedCartItem.setCart(cartItems.getCart());
        updatedCartItem.setProduct(cartItems.getProduct());
        updatedCartItem.setQuantity(cartItems.getQuantity());
        cartItemRepository.save(updatedCartItem);
        return ResponseEntity.ok().body(updatedCartItem);
    }
}
