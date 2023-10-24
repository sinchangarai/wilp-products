package com.bits.wilp.product.service;

import java.util.List;

import com.bits.wilp.product.model.Product;

public interface ProductService {
    
    public void createProduct(Product product);
    public List<Product> getAllProducts();
    public Product getSingleProduct(String id);
    public Product updateProduct(String id, Product product);
    public void deleteProduct(String id);
    public List<Product> findProduct(String name);
}
