package com.example.qlud.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ElectricOtherProduct {
    private String name;
    private int canBookCount;
    private int saleCount;
}
