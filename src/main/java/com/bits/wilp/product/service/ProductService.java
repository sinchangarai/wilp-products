package com.bits.wilp.product.service;

import java.util.List;

import com.bits.wilp.product.model.ProductDTO;

public interface ProductService {
    
    public void createProduct(ProductDTO product);
    public List<ProductDTO> getAllProducts();
    public ProductDTO getSingleProduct(String id);
    public ProductDTO updateProduct(String id, ProductDTO product);
    public void deleteProduct(String id);
    public List<ProductDTO> findProduct(String name);
}
