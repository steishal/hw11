package org.example.discoverystarter;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;
import java.util.Objects;

@Configuration
@EnableConfigurationProperties(DiscoveryProperties.class)
public class DiscoveryAutoConfiguration {

    @Bean
    public DiscoveryClient discoveryClient(DiscoveryProperties props, WebClient.Builder builder) {
        String serverUrl = Objects.requireNonNullElse(props.getServerUrl(), "http://localhost:8080");
        String serviceName = Objects.requireNonNullElse(props.getServiceName(), "unknown-service");
        Integer servicePort = Objects.requireNonNullElse(props.getServicePort(), 8080);

        WebClient webClient = builder.baseUrl(serverUrl).build();

        webClient.post()
                .uri("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(Map.of(
                        "serviceName", serviceName,
                        "port", servicePort
                ))
                .retrieve()
                .bodyToMono(Void.class)
                .block();

        return new DiscoveryClient(webClient);
    }

}
