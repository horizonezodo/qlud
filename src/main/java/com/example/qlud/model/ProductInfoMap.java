package com.example.qlud.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfoMap {
    private String id;
    private String name;
    private int saleCount;
    private int canBookCount;
    private String currentPrice;
    private String discountPrice;
}
