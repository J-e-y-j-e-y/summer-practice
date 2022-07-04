package com.practice.office.requests;

import com.practice.office.MainUI;
import com.practice.office.clients.Client;
import com.practice.office.clients.ClientController;
import com.practice.office.clients.ClientForm;
import com.practice.office.realties.Realty;
import com.practice.office.realties.RealtyController;
import com.practice.office.realties.RealtyForm;
import com.practice.office.utils.IdGenerator;
import com.practice.office.utils.Purpose;
import com.practice.office.utils.TimestampToDateConverter;
import com.vaadin.data.Binder;
import com.vaadin.data.ValueProvider;
import com.vaadin.event.selection.SingleSelectionEvent;
import com.vaadin.event.selection.SingleSelectionListener;
import com.vaadin.server.Setter;
import com.vaadin.ui.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import static com.practice.office.utils.Constants.DASH;

public class RequestForm extends FormLayout {
    private MainUI ui;
    private RequestController controller;
    private final Request EMPTY = new Request();

    private ComboBox<String> purpose = new ComboBox<>("purpose");

    private ComboBox<String> client = new ComboBox<>("client");

    private Label showClient = new Label("Client Info");
    private ClientForm showClientForm = new ClientForm(ui, null);
    VerticalLayout showClientLayout = new VerticalLayout(showClient, showClientForm);

    private ComboBox<String> realty = new ComboBox<>("realty");

    private Label showRealty = new Label("Realty Info");
    private RealtyForm showRealtyForm = new RealtyForm(ui, null);
    VerticalLayout showRealtyLayout = new VerticalLayout(showRealty, showRealtyForm);

    private DateField dm = new DateField("dm");

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

        ArrayList<String> purposes = new ArrayList<>();
        Arrays.stream(Purpose.values()).forEach(purpose -> purposes.add(purpose.name()));
        purpose.setItems(purposes);

        FormLayout formLayout = new FormLayout();
        formLayout.addComponents(purpose, client, realty, dm, components);
        HorizontalLayout main = new HorizontalLayout(formLayout, showClientLayout, showRealtyLayout);
        this.addComponents(main);

        binder.bind(purpose, Request::getStrPurpose, Request::setStrPurpose);

        binder.forField(dm).withConverter(new TimestampToDateConverter())
                .bind(Request::getDm, Request::setDm);

        client.addSelectionListener(new SingleSelectionListener<String>() {
            @Override
            public void selectionChange(SingleSelectionEvent<String> singleSelectionEvent) {
                String id = singleSelectionEvent.getSelectedItem().get();
                System.out.println("id.indexOf(\"id=\") = " + id.indexOf("id="));
                System.out.println("id.indexOf(\",\") = " + id.indexOf(","));
                System.out.println("id = " + id);
                //id = id.substring(id.indexOf("id=") + 3, id.indexOf(","));
                System.out.println("id = " + id);
                UUID clientId = UUID.fromString(id);
                showClientForm.setClient(clientController.getEntityById(clientId));
            }
        });

        realty.addSelectionListener(new SingleSelectionListener<String>() {
            @Override
            public void selectionChange(SingleSelectionEvent<String> singleSelectionEvent) {
                String id = singleSelectionEvent.getSelectedItem().get();
                System.out.println("id.indexOf(\"id=\") = " + id.indexOf("id="));
                System.out.println("id.indexOf(\",\") = " + id.indexOf(","));
                System.out.println("id = " + id);
                //id = id.substring(id.indexOf("id=") + 3, id.indexOf(","));
                System.out.println("id = " + id);
                UUID realtyId = UUID.fromString(id);
                showRealtyForm.setRealty(realtyController.getEntityById(realtyId));
            }
        });

        binder.bind(client, new ValueProvider<Request, String>() {
            @Override
            public String apply(Request request) {
                Client client = request.getClient();
                return client != null ? client.toString() : DASH;
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
                Realty realty = request.getRealty();
                return realty != null ? realty.toString() : DASH;
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
        showClientForm.setClient(null);
        showRealtyForm.setRealty(null);
        add.setVisible(true);
        update.setVisible(false);
        delete.setVisible(false);
        setVisible(true);
    }

    public void add(){
        Request request = binder.getBean();
        UUID id = IdGenerator.generateId();
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
