package org.example.discoverystarter;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;
import java.net.URI;
import java.util.Map;

public class DiscoveryClient {
    private final WebClient webClient;

    public DiscoveryClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public URI getInstance(String serviceName) {
        Map<String, Object> instance = webClient.get()
                .uri("/discover/{name}", serviceName)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();

        if (instance == null) {
            throw new RuntimeException("No instance found for " + serviceName);
        }

        return URI.create("http://" + instance.get("host") + ":" + instance.get("port"));
    }
}
