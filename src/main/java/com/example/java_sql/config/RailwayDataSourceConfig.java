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
        // 1. Try MYSQL_URL (Private/Internal URL usually)
        String railwayUrl = System.getenv("MYSQL_URL");
        if (railwayUrl != null && !railwayUrl.isEmpty()) {
            try {
                URI uri = new URI(railwayUrl);
                String userInfo = uri.getUserInfo();
                String username = userInfo.split(":")[0];
                String password = userInfo.split(":")[1];
                String dbUrl = "jdbc:mysql://" + uri.getHost() + ":" + uri.getPort() + uri.getPath() + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
                
                System.out.println("Configuring Railway DataSource from MYSQL_URL");
                return buildDataSource(dbUrl, username, password);
            } catch (Exception e) {
                System.err.println("Failed to parse MYSQL_URL: " + e.getMessage());
            }
        }

        // 2. Try Individual Variables (MYSQLHOST, MYSQLUSER, etc.)
        String host = System.getenv("MYSQLHOST");
        if (host != null && !host.isEmpty()) {
            try {
                String port = System.getenv("MYSQLPORT");
                String database = System.getenv("MYSQLDATABASE");
                String user = System.getenv("MYSQLUSER");
                String password = System.getenv("MYSQLPASSWORD");
                
                String dbUrl = "jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
                
                System.out.println("Configuring Railway DataSource from individual variables (MYSQLHOST)");
                return buildDataSource(dbUrl, user, password);
            } catch (Exception e) {
                System.err.println("Failed to configure from individual variables: " + e.getMessage());
            }
        }
        
        // 3. Fallback for local development
        System.out.println("Configuring Local DataSource");
        return buildDataSource(
            "jdbc:mysql://localhost:3306/crud_app?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true",
            "root",
            "paytm786"
        );
    }

    private DataSource buildDataSource(String url, String username, String password) {
        return DataSourceBuilder.create()
                .url(url)
                .username(username)
                .password(password)
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();
    }
}
