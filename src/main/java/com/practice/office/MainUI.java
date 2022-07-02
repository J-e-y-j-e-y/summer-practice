package com.practice.office;

import com.practice.office.clients.Client;
import com.practice.office.clients.ClientController;
import com.practice.office.clients.ClientForm;
import com.practice.office.realties.Realty;
import com.practice.office.realties.RealtyController;
import com.practice.office.realties.RealtyForm;
import com.practice.office.requests.Request;
import com.practice.office.requests.RequestController;
import com.practice.office.requests.RequestForm;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {

    private ClientController clientController = new ClientController();
    private Grid<Client> clientsGrid = new Grid<>(Client.class);
    private ClientForm clientForm = new ClientForm(this, clientController);
    private TextField clientfilterText = new TextField();
    HorizontalLayout clientContent = new HorizontalLayout(clientsGrid, clientForm);
    Button addClient = new Button("Add new client");
    HorizontalLayout clientTool = new HorizontalLayout(clientfilterText, addClient);

    private RealtyController realtyController = new RealtyController();
    private Grid<Realty> realtiesGrid = new Grid<>(Realty.class);
    private RealtyForm realtyForm = new RealtyForm(this, realtyController);
    private TextField realtyfilterText = new TextField();
    HorizontalLayout realtyContent = new HorizontalLayout(realtiesGrid, realtyForm);
    Button addRealty = new Button("Add new realty");
    HorizontalLayout realtyTool = new HorizontalLayout(realtyfilterText, addRealty);

    private RequestController requestController = new RequestController(clientController, realtyController);
    private Grid<Request> requestGrid = new Grid<>(Request.class);
    private RequestForm requestForm = new RequestForm(this, clientController, realtyController, requestController);
    private TextField requestfilterText = new TextField();
    HorizontalLayout requestContent = new HorizontalLayout(requestGrid, requestForm);
    Button addRequest = new Button("Add new request");
    HorizontalLayout requestTool = new HorizontalLayout(requestfilterText, addRequest);

    static{
        boolean con = AbstractController.setConnection();
        System.out.println("CONNECTION : " + con);
        //AbstractController.createTables();
        //AbstractController.insertValues();
    }

    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout vertLayout = new VerticalLayout();


        addClient.addClickListener(e -> {
            clientsGrid.asSingleSelect().clear();
            clientForm.addButton();
        });
        addRealty.addClickListener(e -> {
            realtiesGrid.asSingleSelect().clear();
            realtyForm.addButton();
        });
        addRequest.addClickListener(e -> {
            requestGrid.asSingleSelect().clear();
            requestForm.addButton();
        });

        clientsGrid.asSingleSelect().addValueChangeListener(event ->
                clientForm.setClient(clientsGrid.asSingleSelect().getValue()));
        realtiesGrid.asSingleSelect().addValueChangeListener(event ->
                realtyForm.setRealty(realtiesGrid.asSingleSelect().getValue()));
        requestGrid.asSingleSelect().addValueChangeListener(event ->
                requestForm.setRequest(requestGrid.asSingleSelect().getValue()));

        MenuBar bar = new MenuBar();

        Button clientsButton = new Button("Clients");
        clientsButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                clientForm.setClient(null);
                clientsGrid.setColumns("name", "surname", "fathername", "phone", "email");
                updateClients();
                clientfilterText.setPlaceholder("Filter by name...");
                clientfilterText.setValueChangeMode(ValueChangeMode.EAGER);
                clientfilterText.addValueChangeListener(e -> updateClients());

                clientContent.setVisible(true);
                clientTool.setVisible(true);
            }
        });

        Button realtiesButton = new Button("Realties");
        realtiesButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                realtyForm.setRealty(null);
                realtiesGrid.setColumns("neighbourhood", "address", "square", "roomNumber", "price", "cadastralNumber");
                updateRealties();
                realtyfilterText.setPlaceholder("Filter by neighbourhood...");
                realtyfilterText.setValueChangeMode(ValueChangeMode.EAGER);
                realtyfilterText.addValueChangeListener(e -> updateClients());

                realtyContent.setVisible(true);
                realtyTool.setVisible(true);
            }
        });

        Button requestsButton = new Button("Requests");
        requestsButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                requestForm.setRequest(null);
                requestGrid.setColumns("purpose", "client", "realty", "dm");
                updateRequests();
                requestfilterText.setPlaceholder("Filter by purpose...");
                requestfilterText.setValueChangeMode(ValueChangeMode.EAGER);
                requestfilterText.addValueChangeListener(e -> updateClients());

                requestContent.setVisible(true);
                requestTool.setVisible(true);
            }
        });

        bar.addItem(clientsButton.getCaption(), new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                clientForm.setClient(null);
                clientContent.setVisible(false);
                clientTool.setVisible(false);

                realtyForm.setRealty(null);
                realtyContent.setVisible(false);
                realtyTool.setVisible(false);

                requestForm.setRequest(null);
                requestContent.setVisible(false);
                requestTool.setVisible(false);

                clientsButton.click();
            }
        });

        bar.addItem(realtiesButton.getCaption(), new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                clientForm.setClient(null);
                clientContent.setVisible(false);
                clientTool.setVisible(false);

                realtyForm.setRealty(null);
                realtyContent.setVisible(false);
                realtyTool.setVisible(false);

                requestForm.setRequest(null);
                requestContent.setVisible(false);
                requestTool.setVisible(false);

                realtiesButton.click();
            }
        });

        bar.addItem(requestsButton.getCaption(), new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                clientForm.setClient(null);
                clientContent.setVisible(false);
                clientTool.setVisible(false);

                realtyForm.setRealty(null);
                realtyContent.setVisible(false);
                realtyTool.setVisible(false);

                requestForm.setRequest(null);
                requestContent.setVisible(false);
                requestTool.setVisible(false);

                requestsButton.click();
            }
        });

        vertLayout.setWidthUndefined(); // Default, can be omitted
        vertLayout.setSpacing(false); // Compact layout
        vertLayout.addComponent(bar);


        clientContent.setVisible(false);
        clientTool.setVisible(false);
        vertLayout.addComponents(clientTool, clientContent);

        realtyContent.setVisible(false);
        realtyTool.setVisible(false);
        vertLayout.addComponents(realtyTool, realtyContent);

        requestContent.setVisible(false);
        requestTool.setVisible(false);
        vertLayout.addComponents(requestTool, requestContent);

        setContent(vertLayout);
    }

    public void updateClients() {
        HashMap<Integer, Client> clients = clientController.getAll();
        String filter = clientfilterText.getValue();
        if(filter.equals(""))
            clientsGrid.setItems(new ArrayList<>(clients.values()));
        else {
            ArrayList<Client> filtered = new ArrayList<>();
            for (Map.Entry<Integer, Client> entry : clients.entrySet()) {
                Client c = entry.getValue();
                if (c.getName().startsWith(filter))
                    filtered.add(c);
            }
            clientsGrid.setItems(filtered);
        }
    }

    public void updateRealties(){
        HashMap<Integer, Realty> realties = realtyController.getAll();
        String filter = realtyfilterText.getValue();
        if(filter.equals(""))
            realtiesGrid.setItems(new ArrayList<>(realties.values()));
        else {
            ArrayList<Realty> filtered = new ArrayList<>();
            for (Map.Entry<Integer, Realty> entry : realties.entrySet()) {
                Realty c = entry.getValue();
                if (c.getNeighbourhood().startsWith(filter))
                    filtered.add(c);
            }
            realtiesGrid.setItems(filtered);
        }
    }

    public void updateRequests(){
        HashMap<Integer, Request> requests = requestController.getAll();
        String filter = requestfilterText.getValue();
        if(filter.equals(""))
            requestGrid.setItems(new ArrayList<>(requests.values()));
        else {
            ArrayList<Request> filtered = new ArrayList<>();
            for (Map.Entry<Integer, Request> entry : requests.entrySet()) {
                Request c = entry.getValue();
                if (c.getPurpose().toString().startsWith(filter))
                    filtered.add(c);
            }
            requestGrid.setItems(filtered);
        }
    }
}