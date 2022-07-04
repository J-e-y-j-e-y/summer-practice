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
import java.util.UUID;

import static com.practice.office.utils.Constants.*;

public class ClientController extends AbstractController {
    @Getter
    private static HashMap<UUID, Client> clients = new HashMap<>();
    private final String tableName = "clients";

    @SneakyThrows
    @Override
    public HashMap<UUID, Client> getAll() {
        String query = FileUtils.readFileToString(FileUtils.getFile(SELECT_SQL_QUERY_PATH), Charset.defaultCharset());
        query = query.replace(QUESTION, tableName);
        System.out.println(query);
        PreparedStatement ps = getPrepareStatement(query);
        System.out.println(ps);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UUID id = UUID.fromString(rs.getString(1));
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
        Client updatedClient = (Client) entity;
        UUID client_id = updatedClient.getId();

        Client client = clients.get(client_id);
        client.setName(updatedClient.getName());
        client.setSurname(updatedClient.getSurname());
        client.setFathername(updatedClient.getFathername());
        client.setPhone(updatedClient.getPhone());
        client.setEmail(updatedClient.getEmail());

        String query = UPDATE_CLIENTS_SQL;
        PreparedStatement ps = getPrepareStatement(query);
        int rows = 0;
        try {
            ps.setString(1, client.getName());
            ps.setString(2, client.getSurname());
            ps.setString(3, client.getFathername());
            ps.setString(4, client.getPhone());
            ps.setString(5, client.getEmail());
            ps.setObject(6, client.getId());
            System.out.println(ps);
            rows = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePrepareStatement(ps);
        }
        if(rows != 1)
            return -1;
        return rows;
    }

    @Override
    public Client getEntityById(Object id) {
        UUID clientId = (UUID) id;
        return clients.get(clientId);
    }

    @Override
    public boolean delete(Object id) {
        Client client = (Client) id;
        // delete client

        UUID client_id = client.getId();
        clients.remove(client_id);
        int rows = 0;

        String query = DELETE_FROM_SQL;
        query = query.replace(TABLE, tableName);
        PreparedStatement ps = getPrepareStatement(query);
        try {
            ps.setObject(1, client.getId());
            rows = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePrepareStatement(ps);
        }

        return rows == 1;
    }

    @Override
    public boolean create(Object entity) {
        Client client = (Client) entity;

        String query = INSERT_INTO_CLIENTS_SQL;
        PreparedStatement ps = getPrepareStatement(query);
        int rows = 0;
        try {
            ps.setObject(1, client.getId());
            ps.setString(2, client.getName());
            ps.setString(3, client.getSurname());
            ps.setString(4, client.getFathername());
            ps.setString(5, client.getPhone());
            ps.setString(6, client.getEmail());

            rows = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePrepareStatement(ps);
        }
        return rows == 1;
    }
}
