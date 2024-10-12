package org.example.doanki2.controller;

import org.example.doanki2.entity.ImgProduct;
import org.example.doanki2.entity.DTO.ImgProductList;
import org.example.doanki2.model.img_product.ImgProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/imgproduct/v1/")
@CrossOrigin(origins = "http://localhost:3000")
public class ImgProductController {
    @Autowired
    private ImgProductService imgProductService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<List<ImgProductList>> getAll(){
        return imgProductService.getAll();
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<Optional<ImgProductList>> getById(@PathVariable Integer id){
        return imgProductService.getById(id);
    }
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<ImgProduct> post(@RequestBody ImgProduct imgProduct){
        return imgProductService.post(imgProduct);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<ImgProduct> put(@PathVariable Integer id, @RequestBody ImgProduct img_product){
        return imgProductService.put(id, img_product);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<ImgProduct> delete(@PathVariable Integer id){
        return imgProductService.delete(id);
    }
}
