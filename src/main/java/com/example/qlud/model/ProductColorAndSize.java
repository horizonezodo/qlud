package com.example.qlud.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductColorAndSize {
    private ProductColor productColor;
    private ProductSize productSize;
    private List<ProductInfoMap> productInfoMap;
}
