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

import static com.practice.office.utils.Constants.DELETE_FROM_SQL;
import static com.practice.office.utils.Constants.INSERT_INTO_REQUESTS_SQL;
import static com.practice.office.utils.Constants.QUESTION;
import static com.practice.office.utils.Constants.SELECT_SQL_QUERY_PATH;
import static com.practice.office.utils.Constants.TABLE;
import static com.practice.office.utils.Constants.UPDATE_REQUESTS_SQL;


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
        query = query.replace(QUESTION, tableName);
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
        Request updatedRequest = (Request) entity;
        int requestId = updatedRequest.getId();

        Request request = requests.get(requestId);
        request.setPurpose(updatedRequest.getPurpose());
        request.setClient(updatedRequest.getClient());
        request.setRealty(updatedRequest.getRealty());
        request.setDm(updatedRequest.getDm());

        String query = UPDATE_REQUESTS_SQL;
        PreparedStatement ps = getPrepareStatement(query);
        int rows = 0;
        try {
            ps.setString(1, request.getPurpose().name());
            ps.setInt(2, request.getClient().getId());
            ps.setInt(3, request.getRealty().getId());
            ps.setTimestamp(4, request.getDm());
            ps.setInt(5, requestId);
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
    public Request getEntityById(Object id) {
        int requestId = (int) id;
        return requests.get(requestId);
    }

    @Override
    public boolean delete(Object id) {
        Request request = (Request) id;

        int requestId = request.getId();
        requests.remove(requestId);
        int rows = 0;

        String query = DELETE_FROM_SQL;
        query = query.replace(TABLE, tableName);
        PreparedStatement ps = getPrepareStatement(query);
        try {
            ps.setInt(1, requestId);
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
        Request request = (Request) entity;

        String query = INSERT_INTO_REQUESTS_SQL;
        PreparedStatement ps = getPrepareStatement(query);
        int rows = 0;
        try {
            ps.setInt(1, request.getId());
            ps.setString(2, request.getPurpose().name());
            ps.setInt(3, request.getClient().getId());
            ps.setInt(4, request.getRealty().getId());
            ps.setTimestamp(5, request.getDm());

            rows = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePrepareStatement(ps);
        }
        return rows == 1;
    }
}
