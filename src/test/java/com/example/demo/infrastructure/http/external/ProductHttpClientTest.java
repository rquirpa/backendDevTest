package com.example.demo.infrastructure.http.external;

import static java.util.Collections.singleton;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.demo.domain.exception.InvalidResponse;
import com.example.demo.domain.model.ProductDetail;
import feign.FeignException;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class ProductHttpClientTest {

    private ProductsApiClient mockApiClient;
    private ProductHttpClient client;

    @BeforeEach
    void setup() {
        mockApiClient = mock(ProductsApiClient.class);
        client = new ProductHttpClient(mockApiClient);
    }

    @Test
    void getSimilarProductIdsFailedByApiException() {
        // arrange
        when(mockApiClient.getProductSimilarids(any()))
            .thenThrow(FeignException.NotFound.class);

        // act
        Set<String> result = client.getSimilarProductIds("1");

        // assert
        assertTrue(result.isEmpty());
    }

    @Test
    void getSimilarProductIdsSuccess() {
        // arrange
        when(mockApiClient.getProductSimilarids(any()))
            .thenReturn(new ResponseEntity<>(singleton("2"), HttpStatus.OK));

        // act
        Set<String> result = client.getSimilarProductIds("1");

        // assert
        assertTrue(result.contains("2"));
    }

    @Test
    void getProductDetailFailedByApiException() {
        // arrange
        when(mockApiClient.getProductProductId(any()))
            .thenThrow(FeignException.NotFound.class);

        // act and assert
        assertThrows(FeignException.NotFound.class,
            () -> client.getProductDetail("1"));
    }

    @Test
    void getProductDetailFailedByInvalidResponse() {
        // arrange
        when(mockApiClient.getProductProductId(any()))
            .thenReturn(new ResponseEntity<>(HttpStatus.NO_CONTENT));

        // act
        InvalidResponse thrown = assertThrows(InvalidResponse.class,
            () -> client.getProductDetail("1"));

        assertThat(thrown.getMessage(), containsString("Error retrieving details"));
    }

    @Test
    void getProductDetailSuccess() {
        // arrange
        com.example.demo.infrastructure.http.external.model.ProductDetail mockResponse =
            new com.example.demo.infrastructure.http.external.model.ProductDetail();
        mockResponse.setId("2");

        when(mockApiClient.getProductProductId(any()))
            .thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        // act
        ProductDetail result = client.getProductDetail("2");

        // assert
        assertEquals("2", result.getId());
    }

}