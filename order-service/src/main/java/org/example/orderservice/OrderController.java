package org.example.orderservice;

import org.example.discoverystarter.DiscoveryClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final DiscoveryClient discoveryClient;
    private final WebClient webClient = WebClient.create();

    @GetMapping("/orders")
    public String getOrders() {
        URI userServiceUri = discoveryClient.getInstance("user-service");

        String user = webClient.get()
                .uri(userServiceUri + "/users/1")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return "Order for user: " + user;
    }
}

