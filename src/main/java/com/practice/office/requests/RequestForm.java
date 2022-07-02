package com.practice.office.requests;

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

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class RequestForm extends FormLayout {
    private MainUI ui;
    private RequestController controller;
    private final Request EMPTY = new Request();

    private TextField purpose = new TextField("purpose");
    private ComboBox<String> client = new ComboBox<>("client");
    private ComboBox<String> realty = new ComboBox<>("realty");
    private TextField dm = new TextField("dm");

    private Button add = new Button("Add");
    private Button update = new Button("Update");
    private Button delete = new Button("Delete");

    private Binder<Request> binder = new Binder<>(Request.class);

    public RequestForm(MainUI ui, ClientController clientController, RealtyController realtyController,
                       RequestController controller){
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
        client.setItems(clientsList);

        ArrayList<String> realtiesList = new ArrayList<>();
        realtyController.getAll().values().forEach(realty -> realtiesList.add(realty.toString()));
        realty.setItems(realtiesList);

        this.addComponents(components);
        binder.bind(purpose, sq -> sq.toString(), null);
        binder.bind(dm, dm -> new SimpleDateFormat("yyyy MM dd").format(dm), null);

        binder.bind(client, new ValueProvider<Request, String>() {
            @Override
            public String apply(Request request) {
                return request.getClient().toString();
            }

            }, new Setter<Request, String>() {
            @Override
            public void accept(Request request, String s) {
                int clientId = Integer.parseInt(s.split(". ")[0]);
                Client client = (Client) clientController.getEntityById(clientId);
                request.setClient(client);
            }
        });

        binder.bind(realty, new ValueProvider<Request, String>() {
            @Override
            public String apply(Request request) {
                return request.getRealty().toString();
            }

        }, new Setter<Request, String>() {
            @Override
            public void accept(Request request, String s) {
                int realtyId = Integer.parseInt(s.split(". ")[0]);
                Realty realty = (Realty) realtyController.getEntityById(realtyId);
                request.setRealty(realty);
            }
        });

        binder.bindInstanceFields(this);
    }
    public void setRequest(Request request) {
        binder.setBean(request);
        if (request == null) {
            setVisible(false);
        } else {
            add.setVisible(false);
            update.setVisible(true);
            delete.setVisible(true);
            setVisible(true);
            client.focus();
        }
    }

    public void addButton(){
        setRequest(EMPTY);
        add.setVisible(true);
        update.setVisible(false);
        delete.setVisible(false);
        setVisible(true);
    }

    public void add(){
        Request request = binder.getBean();
        int id = IdGenerator.generateId();
        request.setId(id);
        controller.create(request);

        ui.updateRequests();
        setRequest(null);
    }

    public void update(){
        Request request = binder.getBean();
        controller.update(request);

        ui.updateRequests();
        setRequest(null);
    }
    public void delete(){
        Request request = binder.getBean();
        controller.delete(request);

        ui.updateRequests();
        setRequest(null);
    }
}
