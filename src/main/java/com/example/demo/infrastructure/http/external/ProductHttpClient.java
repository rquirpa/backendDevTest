package com.example.demo.infrastructure.http.external;

import static java.lang.String.format;

import com.example.demo.domain.exception.InvalidResponse;
import com.example.demo.domain.model.ProductDetail;
import com.example.demo.domain.service.ProductClient;
import com.example.demo.infrastructure.http.mapper.ProductDetailMapper;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductHttpClient implements ProductClient {

    private final ProductsApiClient apiClient;

    @Override
    public Set<String> getSimilarProductIds(String productId) {
        Set<String> result = new HashSet<>();
        try {
            ResponseEntity<Set<String>> response = apiClient.getProductSimilarids(productId);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                result.addAll(response.getBody());
            }
        } catch (Exception e) {
            log.error(format("Error retrieving similar product ids [%s]", productId), e);
        }

        return result;
    }

    @Override
    @Cacheable("products")
    public ProductDetail getProductDetail(String productId) {
        ResponseEntity<com.example.demo.infrastructure.http.external.model.ProductDetail> response =
            apiClient.getProductProductId(productId);

        if (response.getStatusCode().isError() || response.getBody() == null) {
            throw new InvalidResponse("Error retrieving details. " + response.getStatusCode());
        }

        return ProductDetailMapper.INSTANCE.parse(response.getBody());
    }

}
