package com.example.qlud.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    private String offerId;
    private String link;
    private String productTitle;
    private ProductDetail productDetail;
    private ProductColorAndSize productColorAndSize;
    private ProductPrice productPrices;
    private List<String> imageProductList;
    private String videoUrl;
    private List<String> productImageDetail;
}
