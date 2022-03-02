package soa.labs.util;


import lombok.SneakyThrows;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

public class ClientFactoryBuilder {
    private static Client client;
    private static final String baseUrl = "http://localhost:8081/api/workers";

    @SneakyThrows
    public static Client getClient() {
        if (client != null) return client;
        client = ClientBuilder.newBuilder().build();
        return client;
    }

    public static String getBaseUrl() {
        return baseUrl;
    }
}
