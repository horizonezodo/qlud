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
    private String offerId;
    private String link;
    private String productTitle;
    private List<String> size;
    private List<Price> currentPrice;
    private List<Price> originalPrice;
}
