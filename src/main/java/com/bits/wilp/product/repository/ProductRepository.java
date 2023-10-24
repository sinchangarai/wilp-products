package com.bits.wilp.product.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import com.bits.wilp.product.model.Product;


@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    @Query("{'name': ?0}")
    Optional<Product> findByProduct(String productName);
    // @Query("{'name': {$regex: ?0 }}")
    @Query("{'$or':[ {'name': {$regex: ?0 }}, {'description': {$regex: ?0 }} ] }")
    List<Product> findByCustomQuery(String name);
    
}
