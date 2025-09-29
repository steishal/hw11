package org.example.discoveryserver;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceInstance {
    private String host;
    private int port;
    private long lastHeartbeat = System.currentTimeMillis();

    public String getUrl() {
        return "http://" + host + ":" + port;
    }
    public ServiceInstance(String host, int port) {
        this.host = host;
        this.port = port;
        this.lastHeartbeat = System.currentTimeMillis();
    }
}

