package com.example.demo.infrastructure.http.controller;

import com.example.demo.domain.service.SimilarProductService;
import com.example.demo.infrastructure.http.mapper.ProductDetailMapper;
import com.example.demo.infrastructure.http.api.ProductApi;
import com.example.demo.infrastructure.http.model.ProductDetail;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ProductController implements ProductApi {

    private final SimilarProductService service;

    @Override
    public ResponseEntity<Set<ProductDetail>> getProductSimilar(String productId) {
        return ResponseEntity.ok(
            ProductDetailMapper.INSTANCE.parse(service.getByProductId(productId)));
    }

}
