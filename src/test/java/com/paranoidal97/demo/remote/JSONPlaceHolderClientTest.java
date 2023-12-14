package com.paranoidal97.demo.remote;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.paranoidal97.demo.data.TestDataFactory;
import com.paranoidal97.demo.remote.JSONPlaceHolderClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import com.paranoidal97.demo.remote.JSONPlaceHolderClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import static com.paranoidal97.demo.remote.WireMockStubs.setupMockJSONPlaceHolderResponse;

import java.io.IOException;
import java.util.List;

@SpringBootTest
@AutoConfigureWireMock(port = 8888)
public class JSONPlaceHolderClientTest {

    @Autowired
    JSONPlaceHolderClient jsonPlaceHolderClient;
    @Autowired
    WireMockServer wireMockServer;

    @BeforeEach
    void setUp() throws IOException {
        setupMockJSONPlaceHolderResponse(wireMockServer);
    }
    @Test
    void getProducts(){
        List<ProductDTO> products = jsonPlaceHolderClient.getProducts();

        System.out.println(products);

    }

    @Test
    void addProducts(){
        ProductDTO sampleProductDTO = TestDataFactory.createSampleProductDTO();
        jsonPlaceHolderClient.addProduct(sampleProductDTO);
        System.out.println("TestA");
    }

    @Test
    void getProduct(){
        ProductDTO product = jsonPlaceHolderClient.getProduct(2L);

        System.out.println(product);
    }

    @Test
    void editProduct(){

    }

    @Test
    void getOrders(){
        List<OrderDTO> orders = jsonPlaceHolderClient.getOrders();

        System.out.println(orders);
    }

    @Test
    void addOrder(){

    }

    @Test
    void getOrder(){
        OrderDTO order = jsonPlaceHolderClient.getOrder(1L);

        System.out.println(order);
    }

    @Test
    void editOrder(){

    }

    @Test
    void deleteOrder(){

    }
}

