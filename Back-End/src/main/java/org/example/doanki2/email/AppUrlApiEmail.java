package org.example.doanki2.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppUrlApiEmail {
    @Value("${app.base.url}")
    private String baseUrl;

    public String getBaseUrl() {
        return baseUrl;
    }
}
