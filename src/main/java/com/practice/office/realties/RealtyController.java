package com.practice.office.realties;

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

public class RealtyController extends AbstractController {
    @Getter
    private static HashMap<Integer, Realty> realties = new HashMap<>();
    private final String tableName = "realties";

    @SneakyThrows
    @Override
    public HashMap<Integer, Realty> getAll() {
        String query = FileUtils.readFileToString(FileUtils.getFile(SELECT_SQL_QUERY_PATH), Charset.defaultCharset());
        query = query.replace("?", tableName);
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
        return null;
    }

    @Override
    public Object getEntityById(Object id) {
        int realtyId = (int) id;
        return realties.get(realtyId);
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
