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
import java.util.UUID;

import static com.practice.office.utils.Constants.*;

public class DealController extends AbstractController {
    @Getter
    private static HashMap<UUID, Deal> deals = new HashMap<>();
    private final String tableName = "deals";
    private ClientController clientController = null;
    private RealtyController realtyController = null;

    public DealController(ClientController clientController, RealtyController realtyController) {
        this.clientController = clientController;
        this.realtyController = realtyController;
    }

    @SneakyThrows
    @Override
    public HashMap<UUID, Deal> getAll() {
        String query = FileUtils.readFileToString(FileUtils.getFile(SELECT_SQL_QUERY_PATH), Charset.defaultCharset());
        query = query.replace("?", tableName);
        PreparedStatement ps = getPrepareStatement(query);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UUID id = UUID.fromString(rs.getString(1));
                UUID sellerId =  UUID.fromString(rs.getString(2));
                Client seller = clientController.getEntityById(sellerId);
                UUID buyerId =  UUID.fromString(rs.getString(3));
                Client buyer = clientController.getEntityById(buyerId);
                UUID realtyId = UUID.fromString(rs.getString(4));
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
        Deal updatedDeal = (Deal) entity;
        UUID dealId = updatedDeal.getId();

        Deal deal = deals.get(dealId);
        deal.setSeller(updatedDeal.getSeller());
        deal.setBuyer(updatedDeal.getBuyer());
        deal.setRealty(updatedDeal.getRealty());
        deal.setDm(updatedDeal.getDm());

        String query = UPDATE_DEALS_SQL;
        PreparedStatement ps = getPrepareStatement(query);
        int rows = 0;
        try {
            ps.setObject(1, deal.getSeller().getId());
            ps.setObject(2, deal.getBuyer().getId());
            ps.setObject(3, deal.getRealty().getId());
            ps.setTimestamp(4, deal.getDm());
            ps.setObject(5, dealId);
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
    public Deal getEntityById(Object id) {
        UUID dealId = (UUID) id;
        return deals.get(dealId);
    }

    @Override
    public boolean delete(Object id) {
        Deal deal = (Deal) id;

        UUID dealId = deal.getId();
        deals.remove(dealId);
        int rows = 0;

        String query = DELETE_FROM_SQL;
        query = query.replace(TABLE, tableName);
        PreparedStatement ps = getPrepareStatement(query);
        try {
            ps.setObject(1, dealId);
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
        Deal deal = (Deal) entity;

        String query = INSERT_INTO_DEALS_SQL;
        PreparedStatement ps = getPrepareStatement(query);
        int rows = 0;
        try {
            ps.setObject(1, deal.getId());
            ps.setObject(2, deal.getSeller().getId());
            ps.setObject(3, deal.getBuyer().getId());
            ps.setObject(4, deal.getRealty().getId());
            ps.setTimestamp(5, deal.getDm());

            rows = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePrepareStatement(ps);
        }
        return rows == 1;
    }
}
