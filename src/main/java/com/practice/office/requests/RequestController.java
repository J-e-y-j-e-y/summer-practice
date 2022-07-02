package com.practice.office.requests;

import com.practice.office.AbstractController;
import com.practice.office.clients.Client;
import com.practice.office.clients.ClientController;
import com.practice.office.realties.Realty;
import com.practice.office.realties.RealtyController;
import com.practice.office.utils.Purpose;
import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;

import java.nio.charset.Charset;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;

import static com.practice.office.utils.Constants.SELECT_SQL_QUERY_PATH;


public class RequestController extends AbstractController {
    @Getter
    private static HashMap<Integer, Request> requests = new HashMap<>();
    private final String tableName = "requests";
    private ClientController clientController = null;
    private RealtyController realtyController = null;

    public RequestController(ClientController clientController, RealtyController realtyController) {
        this.clientController = clientController;
        this.realtyController = realtyController;
    }

    @SneakyThrows
    @Override
    public HashMap<Integer, Request> getAll() {
        String query = FileUtils.readFileToString(FileUtils.getFile(SELECT_SQL_QUERY_PATH), Charset.defaultCharset());
        query = query.replace("?", tableName);
        PreparedStatement ps = getPrepareStatement(query);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                Purpose purpose = Purpose.valueOf(rs.getString(2));
                int clientId =  rs.getInt(3);
                Client client = clientController.getEntityById(clientId);
                int realtyId = rs.getInt(4);
                Realty realty = realtyController.getEntityById(realtyId);
                Timestamp dm = rs.getTimestamp(5);
                Request request = new Request(id, purpose, client, realty, dm);
                requests.put(id, request);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePrepareStatement(ps);
        }
        return requests;
    }

    @Override
    public Object update(Object entity) {
        return null;
    }

    @Override
    public Request getEntityById(Object id) {
        int requestId = (int) id;
        return requests.get(requestId);
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
