package com.bits.wilp.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.bits.wilp.product.model.ProductDTO;
import com.bits.wilp.product.service.ProductService;

@RestController
public class ProductController {
    
    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<?> getAllProducts() {
        List<ProductDTO> prodcuts = productService.getAllProducts();
        return new ResponseEntity<>(prodcuts, prodcuts.size() > 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }


    @GetMapping("/products/{id}")
    public ResponseEntity<?> getSingleProduct(@PathVariable("id") String id) {
        try{
            ProductDTO product = productService.getSingleProduct(id);
            return new ResponseEntity<ProductDTO>(product, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/products")
    public ResponseEntity<?> createProduct(@RequestBody ProductDTO product) {
        try{
            productService.createProduct(product);
            return new ResponseEntity<ProductDTO>(product, HttpStatus.OK);
        } catch(Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/products/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") String id, @RequestBody ProductDTO product) {
        try{
            ProductDTO newProduct = productService.updateProduct(id, product);
            return new ResponseEntity<ProductDTO>(newProduct, HttpStatus.OK);
        } catch(Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    
    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") String id) {
        try{
            productService.deleteProduct(id);
            return new ResponseEntity<>("Successfully deleted product with id: " + id, HttpStatus.OK);
        } catch(Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
 