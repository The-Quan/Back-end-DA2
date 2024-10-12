package org.example.doanki2.model.img_product;

import org.example.doanki2.entity.ImgProduct;
import org.example.doanki2.entity.DTO.ImgProductList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ImgProductService {
    @Autowired
    private ImgProductRepository imgProductRepository;

     public ResponseEntity<List<ImgProductList>> getAll(){
         List<ImgProduct> imgProducts = imgProductRepository.findAll();
         return ResponseEntity.ok(imgProducts.stream().map(this::imgProductList).collect(Collectors.toList()));
     }
     public ImgProductList imgProductList (ImgProduct imgProduct){
         ImgProductList imgProductList = new ImgProductList();
         imgProductList.setImg_id(imgProduct.getImg_id());
         imgProductList.setImg_name(imgProduct.getImg_name());
         imgProductList.setImg_url(imgProduct.getImg_url());
         imgProductList.setProducts(imgProduct.getProducts());
         return imgProductList;
     }

     public ResponseEntity<Optional<ImgProductList>> getById(int id){
         Optional<ImgProduct> imgProduct = imgProductRepository.findById(id);
         if (!imgProduct.isPresent()){
             throw new IllegalArgumentException("id not found");
         };
         return ResponseEntity.ok().body(imgProduct.map(this::imgProductList));
     }
    public ResponseEntity<ImgProduct> post(ImgProduct img_product){
         ImgProduct imgProduct = imgProductRepository.save(img_product);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(imgProduct.getImg_id()).toUri();
        return ResponseEntity.created(location).body(imgProduct);
    }
    public ResponseEntity<ImgProduct> put(int id, ImgProduct img_product){
        Optional<ImgProduct> imgProduct = imgProductRepository.findById(id);
        if (!imgProduct.isPresent()){
            throw new IllegalArgumentException("id img not found");
        }
        img_product.setImg_id(imgProduct.get().getImg_id());
        imgProductRepository.save(img_product);

        return ResponseEntity.ok().body(img_product);
    }
    public ResponseEntity<ImgProduct> delete(int id){
         Optional<ImgProduct> imgProduct = imgProductRepository.findById(id);
         if (!imgProduct.isPresent()){
             throw new IllegalArgumentException("id img product not found");
         }
         imgProductRepository.deleteById(imgProduct.get().getImg_id());
         return ResponseEntity.noContent().build();
    }

}
