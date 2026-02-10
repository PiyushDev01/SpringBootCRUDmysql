package com.example.java_sql.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.jdbc.DataSourceBuilder;
import javax.sql.DataSource;
import java.net.URI;

@Configuration
public class RailwayDataSourceConfig {

    @Bean
    public DataSource dataSource() {
        String railwayUrl = System.getenv("MYSQL_URL");
        
        if (railwayUrl != null && !railwayUrl.isEmpty()) {
            try {
                URI uri = new URI(railwayUrl);
                String userInfo = uri.getUserInfo();
                String username = userInfo.split(":")[0];
                String password = userInfo.split(":")[1];
                String dbUrl = "jdbc:mysql://" + uri.getHost() + ":" + uri.getPort() + uri.getPath() + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
                
                System.out.println("Configuring Railway DataSource from MYSQL_URL");

                return DataSourceBuilder.create()
                        .url(dbUrl)
                        .username(username)
                        .password(password)
                        .driverClassName("com.mysql.cj.jdbc.Driver")
                        .build();
            } catch (Exception e) {
                System.err.println("Failed to configure Railway DataSource: " + e.getMessage());
            }
        }
        
        // Fallback for local development
        System.out.println("Configuring Local DataSource");
        return DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:3306/crud_app?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true")
                .username("root")
                .password("paytm786")
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();
    }
}
