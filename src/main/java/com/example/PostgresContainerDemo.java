package com.example;

import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresContainerDemo {

    private static final String IMAGE = "postgres:11.6-alpine";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "postgres";

    public static void main(String[] args) throws InterruptedException {
        try (var container = new PostgreSQLContainer<>(IMAGE)
                .withUsername(USERNAME)
                .withPassword(PASSWORD)) {
            container.start();
            String jdbcUrl = container.getJdbcUrl();
            System.out.println(jdbcUrl);

            System.out.println();
            System.out.println();
            System.out.println();


            try (Connection connection = DriverManager.getConnection(jdbcUrl, USERNAME, PASSWORD)) {
                System.out.println(">>>> Successfully first time");
            } catch (SQLException firstException) {
                System.err.println(">>>> First exception: " + firstException);

                // sleep and retry
                Thread.sleep(2000L);
                try (Connection connection = DriverManager.getConnection(jdbcUrl, USERNAME, PASSWORD)) {
                    System.out.println(">>>> Successfully second time");
                } catch (SQLException secondException) {
                    System.err.println(">>>> Second exception: " + secondException);
                }
            }
        }
    }
}
