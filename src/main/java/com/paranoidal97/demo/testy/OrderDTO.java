package com.paranoidal97.demo.testy;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDTO {
    Long id;
    String customerName;
    LocalDateTime orderDate;
    List<ProductDTO> products;
}
