package com.example.qlud.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.persistence.Id;
import java.util.List;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    private String productId;
    private String productType;
    private String material;
    private String productSaleArea;
    private String productModel;
    private String productSize;
    private String productBrand;
    private String productColor;
    private String productRelease;
    private List<Color> colors;
    private List<Price> price;
}
