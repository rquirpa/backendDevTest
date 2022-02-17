package com.example.demo.infrastructure.http.mapper;

import com.example.demo.domain.model.ProductDetail;
import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductDetailMapper {

    ProductDetailMapper INSTANCE = Mappers.getMapper( ProductDetailMapper.class );

    Set<com.example.demo.infrastructure.http.model.ProductDetail> parse(Set<ProductDetail> set);

    com.example.demo.domain.model.ProductDetail parse(com.example.demo.infrastructure.http.external.model.ProductDetail productDetail);

}
