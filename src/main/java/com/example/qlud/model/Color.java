package com.example.qlud.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Color {
    String imageUrl;
    String name;
    String remainingProducts;
    String price;
    String saleProductCount;
}
