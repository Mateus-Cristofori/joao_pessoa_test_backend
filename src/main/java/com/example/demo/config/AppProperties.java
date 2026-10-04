package com.example.demo.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private Jwt jwt;
    private Cors cors;

    @Getter
    @Setter
    public static class Jwt {

        private String secret;
        private long accessExpirationMs;
        private long refreshExpirationMs;
    }

    @Getter
    @Setter
    public static class Cors {

        private String allowedOrigins;
    }
}