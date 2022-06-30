package com.practice.office;

import com.practice.office.clients.Client;
import com.practice.office.clients.ClientController;
import com.practice.office.clients.ClientForm;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
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

    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setMargin(true);

        layout.addComponent(new Label("Main UI"));

        setContent(layout);
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
}