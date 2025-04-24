package jeu.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionDB {
    private static final String HOST     = System.getenv().getOrDefault("DB_HOST", "localhost");
    private static final String PORT     = System.getenv().getOrDefault("DB_PORT", "3308");
    private static final String DB_NAME  = System.getenv().getOrDefault("DB_NAME", "alcatraz_db");
    private static final String USER     = System.getenv().getOrDefault("DB_USER", "user");
    private static final String PASS     = System.getenv().getOrDefault("DB_PASS", "pass");

    private static final String URL = String.format(
        "jdbc:mysql://%s:%s/%s?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",
        HOST, PORT, DB_NAME
    );

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver JDBC introuvable", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}