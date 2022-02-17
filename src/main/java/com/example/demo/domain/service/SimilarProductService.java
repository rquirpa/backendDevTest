package com.example.demo.domain.service;

import com.example.demo.domain.exception.NotFoundException;
import com.example.demo.domain.model.ProductDetail;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SimilarProductService {

    private final ProductClient productClient;

    public Set<ProductDetail> getByProductId(String productId) {
        Set<String> similarProductIds = productClient.getSimilarProductIds(productId);
        Set<ProductDetail> products = getProductDetails(similarProductIds);

        if (products.isEmpty()) {
            throw new NotFoundException("Not found similar products for productId: " + productId);
        }

        return products;
    }

    private Set<ProductDetail> getProductDetails(Set<String> similarProductIds) {
        Set<ProductDetail> products = new HashSet<>();

        similarProductIds.forEach(similarProductId -> {
            try {
                products.add(productClient.getProductDetail(similarProductId));
            } catch (Exception e) {
                log.error("Error retrieving detail for productId " + similarProductId, e);
            }
        });

        return products;
    }

}
