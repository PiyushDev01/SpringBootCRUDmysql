package com.example.java_sql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JavaSqlApplication {

	public static void main(String[] args) {
		// Parse Railway's MYSQL_URL if provided
		String mysqlUrl = System.getenv("MYSQL_URL");
		if (mysqlUrl != null && mysqlUrl.startsWith("mysql://")) {
			// Convert mysql://user:pass@host:port/db to jdbc:mysql://host:port/db
			try {
				String jdbcUrl = mysqlUrl.replace("mysql://", "jdbc:mysql://");
				// Add required parameters
				if (!jdbcUrl.contains("?")) {
					jdbcUrl += "?useSSL=false&allowPublicKeyRetrieval=true";
				}
				System.setProperty("spring.datasource.url", jdbcUrl);
				System.out.println("Using Railway MYSQL_URL for database connection");
			} catch (Exception e) {
				System.err.println("Error parsing MYSQL_URL: " + e.getMessage());
			}
		}
		
		SpringApplication.run(JavaSqlApplication.class, args);
	}

}
