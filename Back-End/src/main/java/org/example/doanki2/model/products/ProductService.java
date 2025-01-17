package org.example.doanki2.model.products;

import org.example.doanki2.entity.*;
import org.example.doanki2.entity.Products;
import org.example.doanki2.model.author.AuthorRepository;
import org.example.doanki2.model.categoris.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoriesRepository categoriesRepository;
    @Autowired
    private AuthorRepository authorRepository;

    public ResponseEntity<Page<Products>> getAllProducts(Optional<Integer> p) {
        Pageable pageable = PageRequest.of(p.orElse(0), 10);
        Page<Products> productsPage = productRepository.findByIsDeleted(pageable);
        return ResponseEntity.ok(productsPage);
    }
    public ResponseEntity<Page<Products>> getAllProductsFalse(Optional<Integer> p) {
        Pageable pageable = PageRequest.of(p.orElse(0), 10);
        Page<Products> productsPage = productRepository.findByIsDeletedFalse(pageable);
        return ResponseEntity.ok(productsPage);
    }
  
    public ResponseEntity<Products> getById(int id){
        Optional<Products> getById = productRepository.findById(id);
        if (!getById.isPresent()){
            throw new IllegalArgumentException("id product not found");
        }
        return ResponseEntity.ok(getById.get());
    }

    public ResponseEntity<Products> addProduct(Products product){
        Optional<Author> authorOptional = authorRepository.findById(product.getAuthor().getAuthor_id());
        if (!authorOptional.isPresent()){
            throw new IllegalArgumentException("name not found");
        }
        Optional<Categories> categoriesOptional = categoriesRepository.findById(product.getCategories().getCategory_id());
        if (categoriesOptional.isEmpty()){
            throw new IllegalArgumentException("name category not found");
        }
        product.setAuthor(authorOptional.get());
        product.setCategories(categoriesOptional.get());
        Products savedProduct = productRepository.save(product);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedProduct.getProduct_id()).toUri();
        return ResponseEntity.created(location).body(savedProduct);
    }
    public ResponseEntity<Products> updateProduct(int id, Products products){
        Optional<Products> productsOptional = productRepository.findById(id);
        if (!productsOptional.isPresent()){
           throw new IllegalArgumentException("id product not found");
        }
        Optional<Author> authorOptional = authorRepository.findById(products.getAuthor().getAuthor_id());
        if (!authorOptional.isPresent()){
            throw new IllegalArgumentException("id author not found");
        }
        Optional<Categories> categoriesOptional = categoriesRepository.findById(products.getCategories().getCategory_id());
        if (!categoriesOptional.isPresent()){
            throw new IllegalArgumentException("id category not found");
        }
        Products existingProduct = productsOptional.get();

        // Cập nhật các trường của sản phẩm
        existingProduct.setProduct_name(products.getProduct_name());
        existingProduct.setAuthor(authorOptional.get());
        existingProduct.setDescription(products.getDescription());
        existingProduct.setPrice(products.getPrice());
        existingProduct.setCategories(categoriesOptional.get());
        existingProduct.setCreated_at(products.getCreated_at());
        existingProduct.setUpdated_at(products.getUpdated_at());
        existingProduct.setIs_deleted(products.getIs_deleted());
        // Ghi lại các thay đổi
        Products updatedProduct = productRepository.save(existingProduct);

        return ResponseEntity.ok().body(updatedProduct);
    }
    public ResponseEntity<Products> deleteProduct(int id){
        Optional<Products> productsOptional = productRepository.findById(id);
        if (!productsOptional.isPresent()){
            throw new IllegalArgumentException("id product not found");
        }
        productRepository.deleteById(productsOptional.get().getProduct_id());

        return ResponseEntity.noContent().build();
    }
}
