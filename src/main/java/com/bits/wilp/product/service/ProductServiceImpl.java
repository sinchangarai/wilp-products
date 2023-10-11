package com.bits.wilp.product.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bits.wilp.product.model.ProductDTO;
import com.bits.wilp.product.repository.ProductRepository;


@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void createProduct(ProductDTO product) {
        if(product.getName() == null || product.getName().isEmpty() || product.getPrice() == null) {
            throw new IllegalArgumentException("product name or price cannot be empty");
        }

        Optional<ProductDTO> optionalProduct = productRepository.findByProduct(product.getName());
        if(!optionalProduct.isPresent()) {
            if(product.getAvailableQuantity() == null)
                product.setAvailableQuantity(0);
            long currentTimeInMills = System.currentTimeMillis();
            product.setCreatedAt(new Date(currentTimeInMills));
            product.setUpdatedAt(new Date(currentTimeInMills));
            productRepository.save(product);
        } else {
            throw new IllegalArgumentException("The Product name: " + product.getName()
             + " is already present in the list.");
        }
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        List<ProductDTO> allProducts = productRepository.findAll();
        if(allProducts.size() > 0)
            return allProducts;
        else
            return new ArrayList<ProductDTO>();
    }

    @Override
    public ProductDTO getSingleProduct(String id) {
        Optional<ProductDTO> productOptional = productRepository.findById(id);
        if(productOptional.isPresent()) {
            return productOptional.get();
        } else {
            throw new IllegalArgumentException("Product not found with id: " + id);
        }
    }

    @Override
    public ProductDTO updateProduct(String id, ProductDTO product) {
        ProductDTO newProduct = getSingleProduct(id);
        if(product.getName() != null) {
            newProduct.setName(product.getName());
        }
        if(product.getDescription() != null) {
            newProduct.setDescription(product.getDescription());
        }
        if(product.getProductImageLink() != null) {
            newProduct.setProductImageLink(product.getProductImageLink());
        }
        if(product.getPrice() != null) {
            if(product.getPrice() < 0)
                product.setPrice(0.0);
            newProduct.setPrice(product.getPrice());
        }
        if(product.getAvailableQuantity() != null) {
            if(product.getAvailableQuantity() < 1)
                product.setAvailableQuantity(0);
            newProduct.setAvailableQuantity(product.getAvailableQuantity());
        }
        
        newProduct.setUpdatedAt(new Date(System.currentTimeMillis()));
        productRepository.save(newProduct);
        return newProduct;
    }

    @Override
    public void deleteProduct(String id) {
        ProductDTO product = getSingleProduct(id);
        productRepository.delete(product);
    }
}
