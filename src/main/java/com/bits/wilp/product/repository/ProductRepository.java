package com.bits.wilp.product.repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import com.bits.wilp.product.model.ProductDTO;


@Repository
public interface ProductRepository extends MongoRepository<ProductDTO, String> {

    @Query("{'name': ?0}")
    Optional<ProductDTO> findByProduct(String productName);
    
}
