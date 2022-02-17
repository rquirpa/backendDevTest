package com.example.demo.infrastructure.http.external;

import com.example.demo.infrastructure.http.external.api.ProductApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "ProductsApiClient", url = "${similar-products.url}")
public interface ProductsApiClient extends ProductApi {

}
