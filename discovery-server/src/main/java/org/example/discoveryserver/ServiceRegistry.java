package org.example.discoveryserver;

import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ServiceRegistry {
    private final Map<String, List<ServiceInstance>> services = new HashMap<>();
    private final Map<String, Integer> counters = new HashMap<>();

    public synchronized void register(String serviceName, ServiceInstance instance) {
        services.computeIfAbsent(serviceName, k -> new ArrayList<>()).add(instance);
        counters.putIfAbsent(serviceName, 0);
    }

    public synchronized ServiceInstance getNextInstance(String serviceName) {
        List<ServiceInstance> instances = services.get(serviceName);
        if (instances == null || instances.isEmpty()) {
            return null;
        }
        int counter = counters.get(serviceName);
        int index = counter % instances.size();
        counters.put(serviceName, counter + 1);
        return instances.get(index);
    }

    public synchronized void removeInstance(String serviceName, ServiceInstance instance) {
        List<ServiceInstance> instances = services.get(serviceName);
        if (instances != null) {
            instances.remove(instance);
        }
    }

    public Map<String, List<ServiceInstance>> getAll() {
        return services;
    }
}

