package com.practice.office.realties;

import com.practice.office.AbstractController;
import com.practice.office.clients.Client;
import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;

import java.nio.charset.Charset;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import static com.practice.office.utils.Constants.*;

public class RealtyController extends AbstractController {
    @Getter
    private static HashMap<Integer, Realty> realties = new HashMap<>();
    private final String tableName = "realties";

    @SneakyThrows
    @Override
    public HashMap<Integer, Realty> getAll() {
        String query = FileUtils.readFileToString(FileUtils.getFile(SELECT_SQL_QUERY_PATH), Charset.defaultCharset());
        query = query.replace(QUESTION, tableName);
        PreparedStatement ps = getPrepareStatement(query);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String neighbourhood = rs.getString(2);
                String address = rs.getString(3);
                double square = rs.getDouble(4);
                int roomNumber = rs.getInt(5);
                double price = rs.getDouble(6);
                String cadastralNumber = rs.getString(7);
                Realty realty = new Realty(id, neighbourhood, address, square, roomNumber, price, cadastralNumber);
                realties.put(id, realty);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePrepareStatement(ps);
        }
        return realties;
    }

    @Override
    public Object update(Object entity) {
        Realty updatedRealty = (Realty) entity;
        int realtyId = updatedRealty.getId();

        Realty realty = realties.get(realtyId);
        realty.setNeighbourhood(updatedRealty.getNeighbourhood());
        realty.setAddress(updatedRealty.getAddress());
        realty.setSquare(updatedRealty.getSquare());
        realty.setRoomNumber(updatedRealty.getRoomNumber());
        realty.setPrice(updatedRealty.getPrice());
        realty.setCadastralNumber(updatedRealty.getCadastralNumber());

        String query = UPDATE_REALTIES_SQL;
        PreparedStatement ps = getPrepareStatement(query);
        int rows = 0;
        try {
            ps.setString(1, realty.getNeighbourhood());
            ps.setString(2, realty.getAddress());
            ps.setDouble(3, realty.getSquare());
            ps.setInt(4, realty.getRoomNumber());
            ps.setDouble(5, realty.getPrice());
            ps.setString(6, realty.getCadastralNumber());
            ps.setInt(7, realtyId);
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
    public Realty getEntityById(Object id) {
        int realtyId = (int) id;
        return realties.get(realtyId);
    }

    @Override
    public boolean delete(Object id) {
        Realty realty = (Realty) id;

        int realtyId = realty.getId();
        realties.remove(realtyId);
        int rows = 0;

        String query = DELETE_FROM_SQL;
        query = query.replace(TABLE, tableName);
        PreparedStatement ps = getPrepareStatement(query);
        try {
            ps.setInt(1, realtyId);
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
        Realty realty = (Realty) entity;

        String query = INSERT_INTO_REALTIES_SQL;
        PreparedStatement ps = getPrepareStatement(query);
        int rows = 0;
        try {
            ps.setInt(1, realty.getId());
            ps.setString(2, realty.getNeighbourhood());
            ps.setString(3, realty.getAddress());
            ps.setDouble(4, realty.getSquare());
            ps.setInt(5, realty.getRoomNumber());
            ps.setDouble(6, realty.getPrice());
            ps.setString(7, realty.getCadastralNumber());

            rows = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePrepareStatement(ps);
        }
        return rows == 1;
    }
}
