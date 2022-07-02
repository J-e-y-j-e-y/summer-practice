package com.practice.office.deals;

import com.practice.office.AbstractController;
import com.practice.office.clients.Client;
import com.practice.office.clients.ClientController;
import com.practice.office.realties.Realty;
import com.practice.office.realties.RealtyController;

import com.practice.office.requests.Request;
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

public class DealController extends AbstractController {
    @Getter
    private static HashMap<Integer, Deal> deals = new HashMap<>();
    private final String tableName = "deals";
    private ClientController clientController = null;
    private RealtyController realtyController = null;

    public DealController(ClientController clientController, RealtyController realtyController) {
        this.clientController = clientController;
        this.realtyController = realtyController;
    }

    @SneakyThrows
    @Override
    public HashMap<Integer, Deal> getAll() {
        String query = FileUtils.readFileToString(FileUtils.getFile(SELECT_SQL_QUERY_PATH), Charset.defaultCharset());
        query = query.replace("?", tableName);
        PreparedStatement ps = getPrepareStatement(query);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                int sellerId =  rs.getInt(2);
                Client seller = clientController.getEntityById(sellerId);
                int buyerId =  rs.getInt(3);
                Client buyer = clientController.getEntityById(buyerId);
                int realtyId = rs.getInt(4);
                Realty realty = realtyController.getEntityById(realtyId);
                Timestamp dm = rs.getTimestamp(5);
                Deal deal = new Deal(id, seller, buyer, realty, dm);
                deals.put(id, deal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePrepareStatement(ps);
        }
        return deals;
    }

    @Override
    public Object update(Object entity) {
        return null;
    }

    @Override
    public Deal getEntityById(Object id) {
        int dealId = (int) id;
        return deals.get(dealId);
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
