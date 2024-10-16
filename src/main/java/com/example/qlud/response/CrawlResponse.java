package com.example.qlud.response;

import com.example.qlud.model.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrawlResponse {
    private String offerId;
    private String link;
    private String productTitle;
    private ProductDetail productDetail;
    private ProductColorAndSize productColorAndSize;
    private ProductPrice productPrices;
    private List<String> imageProductList;
    private String videoUrl;
    private ElectricOtherProduct electricOtherProduct;
    private List<String> productImageDetail;
}
