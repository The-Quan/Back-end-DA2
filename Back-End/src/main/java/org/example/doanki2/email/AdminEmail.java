package org.example.doanki2.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdminEmail {
    @Value("${admin.email}")
    private String adminEmail;

    public String getAdminEmail() {
        return adminEmail;
    }
}
