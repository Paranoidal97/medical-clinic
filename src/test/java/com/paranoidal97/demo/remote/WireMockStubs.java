package com.paranoidal97.demo.remote;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.charset.Charset;

import static java.nio.charset.Charset.defaultCharset;
import static org.springframework.util.StreamUtils.copyToString;

public class WireMockStubs {
    public static void setupMockJSONPlaceHolderResponse(WireMockServer mockServer) throws IOException {
        mockServer.stubFor(WireMock.get(WireMock.urlEqualTo("/products"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(
                                copyToString(
                                        ProductDTO.class.getClassLoader().getResourceAsStream("payload/get-products.json"),
                                        defaultCharset()
                                )
                        )
                )
        );

        mockServer.stubFor(WireMock.post(WireMock.urlEqualTo("/products"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(
                                copyToString(
                                        ProductDTO.class.getClassLoader().getResourceAsStream("payload/get-products.json"),
                                        defaultCharset()
                                )
                        )
                )
        );

        mockServer.stubFor(WireMock.get(WireMock.urlPathMatching("/products/.*"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(
                                copyToString(
                                        ProductDTO.class.getClassLoader().getResourceAsStream("payload/get-product.json"),
                                        defaultCharset()
                                )
                        )
                )
        );

        mockServer.stubFor(WireMock.get(WireMock.urlEqualTo("/orders"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(
                                copyToString(
                                        OrderDTO.class.getClassLoader().getResourceAsStream("payload/get-orders.json"),
                                        defaultCharset()
                                )
                        )
                )
        );

        mockServer.stubFor(WireMock.get(WireMock.urlPathMatching("/orders/.*"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(
                                copyToString(
                                        OrderDTO.class.getClassLoader().getResourceAsStream("payload/get-order.json"),
                                        defaultCharset()
                                )
                        )
                )
        );


    }
}
