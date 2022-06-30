package com.practice.office;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

public abstract class AbstractController <E, K>{
    private static Connection connection = null;

    public abstract HashMap<K, E> getAll();
    public abstract E update(E entity);
    public abstract E getEntityById(K id);
    public abstract boolean delete(K id);
    public abstract boolean create(E entity);


    // Получение экземпляра PrepareStatement
    public static PreparedStatement getPrepareStatement(String sql) {
        PreparedStatement ps = null;
        try {
            System.out.println("!Preparing statement\n" + sql);
            System.out.println(connection);
            ps = connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ps;
    }

    // Закрытие PrepareStatement
    public static void closePrepareStatement(PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean setConnection(){
        try {
            //connection = DriverManager.getConnection("jdbc:hsqldb:mem:mydb", "SA", "");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/office", "postgres", "postgres");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection != null;
    }

    public static boolean createTables(){
        String query = null;
        try {
            query = readFileToString("sql/createTables.sql");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] queries = query.split(";");
        int rows = 0;
        for(int i = 0; i < queries.length; i++){
            String creationQuery = queries[i];
            if(i != queries.length - 1)
                creationQuery += ";";
            PreparedStatement ps = getPrepareStatement(creationQuery);
            try {
                rows += ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                closePrepareStatement(ps);
            }
        }
        return rows == queries.length;
    }

    public static boolean insertValues(){
        String query = null;
        try {
            query = readFileToString("sql/insertValues.sql");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] queries = query.split("\n");
        int rows = 0;
        for(int i = 0; i < queries.length; i++) {
            PreparedStatement ps = getPrepareStatement(queries[i]);
            try {
                rows += ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                closePrepareStatement(ps);
            }
        }
        return rows == queries.length;
    }


    private static String readFileToString(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }
}
