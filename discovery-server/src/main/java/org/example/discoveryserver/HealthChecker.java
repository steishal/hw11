package org.example.discoveryserver;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Component
public class HealthChecker {
    private final ServiceRegistry registry;
    private final WebClient webClient = WebClient.create();

    public HealthChecker(ServiceRegistry registry) {
        this.registry = registry;
    }

    @Scheduled(fixedRate = 10000)
    public void checkHealth() {
        for (var entry : registry.getAll().entrySet()) {
            var instances = entry.getValue();
            instances.removeIf(instance -> !isAlive(instance));
        }
    }

    private boolean isAlive(ServiceInstance instance) {
        try {
            webClient.get()
                    .uri(instance.getUrl() + "/actuator/health")
                    .retrieve()
                    .toBodilessEntity()
                    .block(Duration.ofSeconds(2));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

