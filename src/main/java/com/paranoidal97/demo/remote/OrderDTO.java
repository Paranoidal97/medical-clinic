package com.paranoidal97.demo.remote;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class OrderDTO {
    Long id;
    String customerName;
    LocalDateTime orderDate;
    List<ProductDTO> products;
}
