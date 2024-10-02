package com.example.qlud.control;

import com.example.qlud.DTO.ProductDTO;
import com.example.qlud.model.Product;
import com.example.qlud.repo.ProductRepo;
import com.example.qlud.response.GetAllProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AppController {

    @Autowired
    ProductRepo repo;

    @GetMapping("/home")
    public String getHome(Model model){
        return "home";
    }

    @GetMapping("/app-show-crawl")
    public String showCrawlPage(Model model){
        return "crawl";
    }

    @GetMapping("/show-all-product")
    public String ShowAllProduct(Model model){
        List<Product> listProduct = repo.findAll();
        List<GetAllProductResponse> listResult = listProduct.stream()
                .map(this::convertProductResponse)
                .collect(Collectors.toList());
        model.addAttribute("result",listResult);
        return "all-product-page";
    }

    private GetAllProductResponse convertProductResponse(Product product){
        GetAllProductResponse productResponse = new GetAllProductResponse();
        productResponse.setProductId(product.getProductId());
        productResponse.setProductBrand(product.getProductBrand());
        productResponse.setProductColor(product.getProductColor());
        productResponse.setProductSize(product.getProductSize());
        productResponse.setProductType(product.getProductType());
        productResponse.setMaterial(product.getMaterial());
        productResponse.setProductRelease(product.getProductRelease());
        productResponse.setProductSaleArea(product.getProductSaleArea());
        productResponse.setPrices(productResponse.getPrices());
        return  productResponse;
    }
}
