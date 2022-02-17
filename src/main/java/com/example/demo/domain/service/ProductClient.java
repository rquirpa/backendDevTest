package com.example.demo.domain.service;

import com.example.demo.domain.model.ProductDetail;
import java.util.Set;

public interface ProductClient {

    Set<String> getSimilarProductIds(String productId);

    ProductDetail getProductDetail(String productId);

}
