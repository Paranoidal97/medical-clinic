package com.paranoidal97.demo.remote;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Getter
public class ProductDTO {
    Long id;
    String name;
    String description;
    BigDecimal price;
}
