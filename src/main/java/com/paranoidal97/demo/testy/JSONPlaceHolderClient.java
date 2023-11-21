package com.paranoidal97.demo.testy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "sklepInternetowy", url = "localhost:8888")
public interface JSONPlaceHolderClient {

    @GetMapping("/products")
    List<ProductDTO> getProducts();

    @PostMapping("/products")
    ProductDTO addProduct(@RequestBody ProductDTO product);

    @GetMapping("/products/{id}")
    ProductDTO getProduct(@PathVariable Long id);

    @PutMapping("/products/{id}")
    ProductDTO editProduct(@PathVariable Long id, @RequestBody ProductDTO product);

    @DeleteMapping("/products/{id}")
    void deleteProduct(@PathVariable Long id);

    @GetMapping("/orders")
    List<OrderDTO> getOrders();

    @PostMapping(value = "/orders")
    OrderDTO addOrder(@RequestBody OrderDTO order);

    @GetMapping("/orders/{id}")
    OrderDTO getOrders(@PathVariable Long id);

    @PutMapping("/orders/{id}")
    OrderDTO editOrder(@PathVariable Long id, @RequestBody OrderDTO order);

    @DeleteMapping("/orders/{id}")
    void deleteOrder(@PathVariable Long id);
}
