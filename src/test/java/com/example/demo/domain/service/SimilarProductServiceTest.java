package com.example.demo.domain.service;

import static java.util.Collections.singleton;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.demo.domain.exception.NotFoundException;
import com.example.demo.domain.model.ProductDetail;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SimilarProductServiceTest {

    private ProductClient mockClient;
    private SimilarProductService service;

    @BeforeEach
    void setup() {
        mockClient = mock(ProductClient.class);
        service = new SimilarProductService(mockClient);
    }

    @Test
    void getByProductIdFailedByEmptyIds() {
        // arrange
        when(mockClient.getSimilarProductIds(any()))
            .thenReturn(new HashSet<>());

        // act
        NotFoundException thrown = assertThrows(NotFoundException.class,
            () -> service.getByProductId("1"));

        // assert
        assertThat(thrown.getMessage(), containsString("Not found"));
    }

    @Test
    void getByProductIdFailedByEmptyDetails() {
        // arrange
        when(mockClient.getSimilarProductIds(any()))
            .thenReturn(singleton("2"));

        when(mockClient.getProductDetail(any()))
            .thenThrow(NotFoundException.class);

        // act
        NotFoundException thrown = assertThrows(NotFoundException.class,
            () -> service.getByProductId("1"));

        // assert
        assertThat(thrown.getMessage(), containsString("Not found"));
    }

    @Test
    void getByProductIdSuccess() {
        // arrange
        when(mockClient.getSimilarProductIds(any()))
            .thenReturn(singleton("2"));

        when(mockClient.getProductDetail(any()))
            .thenReturn(new ProductDetail());

        // act
        Set<ProductDetail> products = service.getByProductId("1");

        // assert
        assertEquals(1, products.size());
    }

}