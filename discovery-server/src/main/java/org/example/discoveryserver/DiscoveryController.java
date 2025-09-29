package org.example.discoveryserver;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class DiscoveryController {
    private final ServiceRegistry registry;

    public DiscoveryController(ServiceRegistry registry) {
        this.registry = registry;
    }

    @PostMapping("/register")
    public Map<String, String> register(@RequestBody Map<String, Object> request, HttpServletRequest servletRequest) {
        String serviceName = (String) request.get("serviceName");
        int port = (int) request.get("port");
        String host = servletRequest.getRemoteAddr();

        registry.register(serviceName, new ServiceInstance(host, port));
        return Map.of("status", "registered");
    }

    @GetMapping("/discover/{serviceName}")
    public ResponseEntity<ServiceInstance> discover(@PathVariable String serviceName) {
        ServiceInstance instance = registry.getNextInstance(serviceName);
        if (instance == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(instance);
    }
}
