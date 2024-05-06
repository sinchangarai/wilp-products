package com.bits.wilp.product.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.bits.wilp.product.model.Product;
import com.bits.wilp.product.service.ProductService;
import com.bits.wilp.product.util.HttpUtil;

@RestController
@RequestMapping("/catalog")
public class ProductController {
    
    @Autowired
    private ProductService productService;
    @Autowired
    private HttpUtil httpUtil;

    @GetMapping("/products")
    public ResponseEntity<?> getAllProducts() {
        List<Product> prodcuts = productService.getAllProducts();
        return new ResponseEntity<>(prodcuts, prodcuts.size() > 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }


    @GetMapping("/products/{id}")
    public ResponseEntity<?> getSingleProduct(@PathVariable("id") String id) {
        try{
            Product product = productService.getSingleProduct(id);
            return new ResponseEntity<Product>(product, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/products")
    public ResponseEntity<?> createProduct(@RequestBody Product product, HttpServletRequest request) {

        try{
            productService.createProduct(product);
            return new ResponseEntity<Product>(product, HttpStatus.OK);
        } catch(Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/products/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") String id, @RequestBody Product product, HttpServletRequest request) {

        try{
            Product newProduct = productService.updateProduct(id, product);
            return new ResponseEntity<Product>(newProduct, HttpStatus.OK);
        } catch(Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    
    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") String id, HttpServletRequest request) {

        try{
            productService.deleteProduct(id);
            return new ResponseEntity<>("Successfully deleted product with id: " + id, HttpStatus.OK);
        } catch(Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/products/search")
    public ResponseEntity<?> findProduct(@RequestParam("query") String query, HttpServletRequest request) {

        List<Product> products = productService.findProduct(query);
        if(products.size() > 0){
            return new ResponseEntity<>(products, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(products, HttpStatus.NOT_FOUND);
        }
    }
}
 
