package com.practice.office.deals;

import com.practice.office.MainUI;
import com.practice.office.clients.Client;
import com.practice.office.clients.ClientController;
import com.practice.office.realties.Realty;
import com.practice.office.realties.RealtyController;
import com.practice.office.utils.IdGenerator;
import com.vaadin.data.Binder;
import com.vaadin.data.ValueProvider;
import com.vaadin.server.Setter;
import com.vaadin.ui.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.UUID;

import static com.practice.office.utils.Constants.DASH;

public class DealForm extends FormLayout {
    private MainUI ui;
    private DealController controller;
    private final Deal EMPTY = new Deal();

    private ComboBox<String> seller = new ComboBox<>("seller");
    private ComboBox<String> buyer = new ComboBox<>("buyer");
    private ComboBox<String> realty = new ComboBox<>("realty");
    private TextField dm = new TextField("dm");

    private Button add = new Button("Add");
    private Button update = new Button("Update");
    private Button delete = new Button("Delete");

    private Binder<Deal> binder = new Binder<>(Deal.class);

    public DealForm(MainUI ui, ClientController clientController, RealtyController realtyController,
                       DealController controller){
        this.ui = ui;
        this.controller = controller;

        add.addClickListener(e -> add());
        add.setVisible(false);
        update.addClickListener(e -> update());
        delete.addClickListener(e -> delete());
        HorizontalLayout components = new HorizontalLayout();
        components.addComponent((Component) add);
        components.addComponent((Component) update);
        components.addComponent((Component) delete);

        ArrayList<String> clientsList = new ArrayList<>();
        clientController.getAll().values().forEach(client -> clientsList.add(client.toString()));
        seller.setItems(clientsList);
        buyer.setItems(clientsList);

        ArrayList<String> realtiesList = new ArrayList<>();
        realtyController.getAll().values().forEach(realty -> realtiesList.add(realty.toString()));
        realty.setItems(realtiesList);

        this.addComponents(seller, buyer, realty, dm, components);
        binder.bind(dm, Deal::getStrDm, Deal::setStrDm);

        binder.bind(seller, new ValueProvider<Deal, String>() {
            @Override
            public String apply(Deal deal) {
                Client seller = deal.getSeller();
                return seller != null ? seller.toString() : DASH;
            }

        }, new Setter<Deal, String>() {
            @Override
            public void accept(Deal deal, String s) {
                int clientId = Integer.parseInt(s.split(". ")[0]);
                Client client = (Client) clientController.getEntityById(clientId);
                deal.setSeller(client);
            }
        });

        binder.bind(buyer, new ValueProvider<Deal, String>() {
            @Override
            public String apply(Deal deal) {
                Client buyer = deal.getBuyer();
                return buyer != null ? buyer.toString() : DASH;
            }

        }, new Setter<Deal, String>() {
            @Override
            public void accept(Deal deal, String s) {
                int clientId = Integer.parseInt(s.split(". ")[0]);
                Client client = (Client) clientController.getEntityById(clientId);
                deal.setBuyer(client);
            }
        });

        binder.bind(realty, new ValueProvider<Deal, String>() {
            @Override
            public String apply(Deal deal) {
                Realty realty = deal.getRealty();
                return realty != null ? realty.toString() : DASH;
            }

        }, new Setter<Deal, String>() {
            @Override
            public void accept(Deal deal, String s) {
                int realtyId = Integer.parseInt(s.split(". ")[0]);
                Realty realty = (Realty) realtyController.getEntityById(realtyId);
                deal.setRealty(realty);
            }
        });

        binder.bindInstanceFields(this);
    }
    public void setDeal(Deal deal) {
        binder.setBean(deal);
        if (deal == null) {
            setVisible(false);
        } else {
            add.setVisible(false);
            update.setVisible(true);
            delete.setVisible(true);
            setVisible(true);
            seller.focus();
        }
    }

    public void addButton(){
        setDeal(EMPTY);
        add.setVisible(true);
        update.setVisible(false);
        delete.setVisible(false);
        setVisible(true);
    }

    public void add(){
        Deal deal = binder.getBean();
        UUID id = IdGenerator.generateId();
        deal.setId(id);
        controller.create(deal);

        ui.updateDeals();
        setDeal(null);
    }

    public void update(){
        Deal deal = binder.getBean();
        controller.update(deal);

        ui.updateDeals();
        setDeal(null);
    }
    public void delete(){
        Deal deal = binder.getBean();
        controller.delete(deal);

        ui.updateDeals();
        setDeal(null);
    }
}
