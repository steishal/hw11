package org.example.discoverystarter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "discovery")
@Data
public class DiscoveryProperties {
    private String serverUrl;
    private String serviceName;
    private int servicePort;
}

