package com.example.demo.infrastructure.http.controller;

import static java.util.Collections.singleton;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.domain.exception.NotFoundException;
import com.example.demo.domain.model.ProductDetail;
import com.example.demo.domain.service.SimilarProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@AutoConfigureMockMvc
@SpringBootTest
class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private SimilarProductService mockService;

    @Test
    void getProductSimilarFailedByNotFoundException() throws Exception {
        // arrange
        when(mockService.getByProductId(any()))
            .thenThrow(NotFoundException.class);

        // act
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders
            .get("/product/{productId}/similar", "1")
        );

        // assert
        resultActions
            .andDo(print())
            .andExpect(status().isNotFound());
    }

    @Test
    void getProductSimilarSuccess() throws Exception {
        // arrange
        ProductDetail detail = new ProductDetail();
        detail.setId("1");

        when(mockService.getByProductId(any()))
            .thenReturn(singleton(detail));

        // act
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders
            .get("/product/{productId}/similar", "1")
        );

        // assert
        resultActions
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", is(not(empty()))));
    }

}