package com.practice.office.deals;

import com.practice.office.MainUI;
import com.practice.office.clients.Client;
import com.practice.office.clients.ClientController;
import com.practice.office.clients.ClientForm;
import com.practice.office.realties.Realty;
import com.practice.office.realties.RealtyController;
import com.practice.office.realties.RealtyForm;
import com.practice.office.requests.Request;
import com.practice.office.utils.IdGenerator;
import com.practice.office.utils.TimestampToDateConverter;
import com.vaadin.data.Binder;
import com.vaadin.data.ValueProvider;
import com.vaadin.event.selection.SingleSelectionEvent;
import com.vaadin.event.selection.SingleSelectionListener;
import com.vaadin.server.Setter;
import com.vaadin.ui.*;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static com.practice.office.utils.Constants.DASH;

public class DealForm extends FormLayout {
    private MainUI ui;
    private DealController controller;
    private final Deal EMPTY = new Deal();

    private ComboBox<String> seller = new ComboBox<>("seller");

    private Label showSeller = new Label("Seller Info");
    private ClientForm showSellerForm = new ClientForm(ui, null);
    VerticalLayout showSellerLayout = new VerticalLayout(showSeller, showSellerForm);

    private ComboBox<String> buyer = new ComboBox<>("buyer");

    private Label showBuyer = new Label("Buyer Info");
    private ClientForm showBuyerForm = new ClientForm(ui, null);
    VerticalLayout showBuyerLayout = new VerticalLayout(showBuyer, showBuyerForm);

    private ComboBox<String> realty = new ComboBox<>("realty");

    private Label showRealty = new Label("Realty Info");
    private RealtyForm showRealtyForm = new RealtyForm(ui, null);
    VerticalLayout showRealtyLayout = new VerticalLayout(showRealty, showRealtyForm);

    private DateField dm = new DateField("dm");

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

        FormLayout formLayout = new FormLayout();
        formLayout.addComponents(seller, buyer, realty, dm, components);
        VerticalLayout showClientLayout = new VerticalLayout(showSellerLayout, showBuyerLayout);
        HorizontalLayout main = new HorizontalLayout(formLayout, showClientLayout, showRealtyLayout);
        this.addComponents(main);

        seller.addSelectionListener(new SingleSelectionListener<String>() {
            @Override
            public void selectionChange(SingleSelectionEvent<String> singleSelectionEvent) {
                Optional<String> item = singleSelectionEvent.getSelectedItem();
                if(!item.isPresent()) {
                    showSellerForm.setClient(showSellerForm.getEMPTY());
                    return;
                }
                String id = item.get();
                System.out.println("id.indexOf(\"id=\") = " + id.indexOf("id="));
                System.out.println("id.indexOf(\",\") = " + id.indexOf(","));
                System.out.println("id = " + id);
                //id = id.substring(id.indexOf("id=") + 3, id.indexOf(","));
                System.out.println("id = " + id);
                if(DASH.equals(id)) {
                    showSellerForm.setClient(showSellerForm.getEMPTY());
                    return;
                }
                UUID clientId = UUID.fromString(id);
                showSellerForm.setClient(clientController.getEntityById(clientId));
            }
        });

        buyer.addSelectionListener(new SingleSelectionListener<String>() {
            @Override
            public void selectionChange(SingleSelectionEvent<String> singleSelectionEvent) {
                Optional<String> item = singleSelectionEvent.getSelectedItem();
                if(!item.isPresent()) {
                    showBuyerForm.setClient(showBuyerForm.getEMPTY());
                    return;
                }
                String id = item.get();
                System.out.println("id.indexOf(\"id=\") = " + id.indexOf("id="));
                System.out.println("id.indexOf(\",\") = " + id.indexOf(","));
                System.out.println("id = " + id);
                //id = id.substring(id.indexOf("id=") + 3, id.indexOf(","));
                System.out.println("id = " + id);
                if(DASH.equals(id)) {
                    showBuyerForm.setClient(showBuyerForm.getEMPTY());
                    return;
                }
                UUID clientId = UUID.fromString(id);
                showBuyerForm.setClient(clientController.getEntityById(clientId));
            }
        });

        realty.addSelectionListener(new SingleSelectionListener<String>() {
            @Override
            public void selectionChange(SingleSelectionEvent<String> singleSelectionEvent) {
                Optional<String> item = singleSelectionEvent.getSelectedItem();
                if(!item.isPresent()) {
                    showRealtyForm.setRealty(showRealtyForm.getEMPTY());
                    return;
                }
                String id = item.get();
                System.out.println("id.indexOf(\"id=\") = " + id.indexOf("id="));
                System.out.println("id.indexOf(\",\") = " + id.indexOf(","));
                System.out.println("id = " + id);
                //id = id.substring(id.indexOf("id=") + 3, id.indexOf(","));
                System.out.println("id = " + id);
                if(DASH.equals(id)) {
                    showRealtyForm.setRealty(showRealtyForm.getEMPTY());
                    return;
                }
                UUID realtyId = UUID.fromString(id);
                showRealtyForm.setRealty(realtyController.getEntityById(realtyId));
            }
        });

        binder.forField(dm).withConverter(new TimestampToDateConverter())
                .bind(Deal::getDm, Deal::setDm);

        binder.bind(seller, new ValueProvider<Deal, String>() {
            @Override
            public String apply(Deal deal) {
                Client seller = deal.getSeller();
                return seller != null ? seller.toString() : DASH;
            }

        }, new Setter<Deal, String>() {
            @Override
            public void accept(Deal deal, String s) {
                UUID clientId = UUID.fromString(s);
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
                UUID clientId = UUID.fromString(s);
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
                UUID realtyId = UUID.fromString(s);
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
        showSellerForm.setClient(showSellerForm.getEMPTY());
        showBuyerForm.setClient(showBuyerForm.getEMPTY());
        showRealtyForm.setRealty(showRealtyForm.getEMPTY());
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
