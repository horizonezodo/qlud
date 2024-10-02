package com.example.qlud.response;

import com.example.qlud.model.Price;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllProductResponse {
    private String productId;
    private String productType;
    private String material;
    private String productSaleArea;
    private String productModel;
    private String productSize;
    private String productBrand;
    private String productColor;
    private String productRelease;
    private List<Price> prices;
}
