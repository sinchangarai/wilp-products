package com.bits.wilp.product.model;

import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "products")
public class ProductDTO {
    @Id
    private String id;
    private String name;
    private String description;
    private String productImageLink;
    private Double price;
    private Integer availableQuantity;
    private Date createdAt;
    private Date updatedAt;
}
