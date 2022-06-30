package com.practice.office.clients;

import com.practice.office.AbstractController;
import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;

import java.nio.charset.Charset;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import static com.practice.office.utils.Constants.SELECT_SQL_QUERY_PATH;

public class ClientController extends AbstractController {
    @Getter
    private static HashMap<Integer, Client> clients = new HashMap<>();
    private final String tableName = "clients";

    @SneakyThrows
    @Override
    public HashMap<Integer, Client> getAll() {
        String query = FileUtils.readFileToString(FileUtils.getFile(SELECT_SQL_QUERY_PATH), Charset.defaultCharset());
        PreparedStatement ps = getPrepareStatement(query);
        ps.setString(1, tableName);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String surname = rs.getString(3);
                String fathername = rs.getString(4);
                String phone = rs.getString(5);
                String email = rs.getString(6);
                Client client = new Client(id, name, surname, fathername, phone, email);
                clients.put(id, client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePrepareStatement(ps);
        }
        return clients;
    }

    @Override
    public Object update(Object entity) {
        return null;
    }

    @Override
    public Client getEntityById(Object id) {
        int clientId = (int) id;
        return clients.get(clientId);
    }

    @Override
    public boolean delete(Object id) {
        return false;
    }

    @Override
    public boolean create(Object entity) {
        return false;
    }
}
