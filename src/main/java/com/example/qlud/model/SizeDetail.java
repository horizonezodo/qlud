package com.example.qlud.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SizeDetail {
    private String id;
    private String name;
    private String price;
    private int canBookCount;
    private int saleCount;
}
