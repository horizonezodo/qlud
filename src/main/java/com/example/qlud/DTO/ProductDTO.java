package com.example.qlud.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private int productId;
    private String productType;
    private String material;
    private String productSaleArea;
    private String productModel;
    private String productSize;
    private String productBrand;
    private String productColor;
    private String productRelease;
}
