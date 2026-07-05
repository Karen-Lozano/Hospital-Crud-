package org.example.hospitalcrud;

import java.sql.Connection;
import java.sql.DriverManager;


public class Conexion {

    private static final String URL = "jdbc:postgresql://localhost:5432/hospital";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1234";

    public static Connection conectar() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            System.out.println("Error al conectar");
            e.printStackTrace();
            return null;
        }
    }
}