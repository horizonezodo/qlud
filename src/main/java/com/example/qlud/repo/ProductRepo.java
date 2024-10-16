package com.example.qlud.repo;

import com.example.qlud.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends MongoRepository<Product, String> {
    @Query("{ $or: [ " +
            "{ 'offerId': { $regex: ?0, $options: 'i' } }, " +
            "{ 'link': { $regex: ?0, $options: 'i' } }, " +
            "{ 'productTitle': { $regex: ?0, $options: 'i' } }, " +
            "{ 'productDetail.name': { $regex: ?0, $options: 'i' } }, " +
            "{ 'productInfos.name': { $regex: ?0, $options: 'i' } }, " +
            "{ 'productInfos.value': { $regex: ?0, $options: 'i' } }, " +
            "{ 'productColorAndSize.productColor.name': { $regex: ?0, $options: 'i' } }, " +
            "{ 'productColorAndSize.productColor.colors.name': { $regex: ?0, $options: 'i' } }, " +
            "{ 'productColorAndSize.productColor.colors.imageUrl': { $regex: ?0, $options: 'i' } }, " +
            "{ 'productColorAndSize.productSize.name': { $regex: ?0, $options: 'i' } }, " +
            "{ 'productColorAndSize.productSize.size': { $regex: ?0, $options: 'i' } }, " +
            "{ 'productColorAndSize.productInfoMap.name': { $regex: ?0, $options: 'i' } }, " +
            "{ 'productColorAndSize.productInfoMap.currentPrice': { $regex: ?0, $options: 'i' } }, " +
            "{ 'productColorAndSize.productInfoMap.discountPrice': { $regex: ?0, $options: 'i' } }, " +
            "{ 'productPrices.name': { $regex: ?0, $options: 'i' } }, " +
            "{ 'productPrices.currentPrice.price': { $regex: ?0, $options: 'i' } }, " +
            "{ 'productPrices.currentPrice.priceAmount': { $regex: ?0, $options: 'i' } }, " +
            "{ 'productPrices.originalPrice.price': { $regex: ?0, $options: 'i' } }, " +
            "{ 'productPrices.originalPrice.priceAmount': { $regex: ?0, $options: 'i' } }, " +
            "{ 'imageProductList': { $regex: ?0, $options: 'i' } }, " +
            "{ 'videoUrl': { $regex: ?0, $options: 'i' } }, " +
            "{ 'productImageDetail': { $regex: ?0, $options: 'i' } } " +
            "] }")
    List<Product> findByKey(String key);


}
