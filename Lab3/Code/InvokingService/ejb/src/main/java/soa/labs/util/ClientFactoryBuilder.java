package soa.labs.util;


import lombok.SneakyThrows;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.io.FileInputStream;
import java.security.KeyStore;

public class ClientFactoryBuilder {
    private static Client client;
    private static final String baseUrl = "https://localhost:8762/workerapi/workers";

    @SneakyThrows
    public static Client getClient() {
        if (client != null) return client;

        FileInputStream is = new FileInputStream("/home/olex/ITMO/SOA/Lab3/localhost.jks");
        KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
        keystore.load(is, "12345678".toCharArray());
        client = ClientBuilder.newBuilder().trustStore(keystore).build();
        return client;
    }

    public static String getBaseUrl() {
        return baseUrl;
    }
}
