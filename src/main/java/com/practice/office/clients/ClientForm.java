package com.practice.office.clients;

import com.practice.office.MainUI;

import com.practice.office.utils.IdGenerator;
import com.vaadin.data.Binder;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;

import java.util.UUID;

public class ClientForm extends FormLayout {
    private MainUI ui;
    private ClientController controller;
    private final Client EMPTY = new Client();

    private TextField name = new TextField("name");
    private TextField surname = new TextField("surname");
    private TextField fathername = new TextField("fathername");
    private TextField phone = new TextField("phone");
    private TextField email = new TextField("email");

    private Button add = new Button("Add");
    private Button update = new Button("Update");
    private Button delete = new Button("Delete");

    private Binder<Client> binder = new Binder<>(Client.class);
    //private ComboBox<CustomerStatus> status = new ComboBox<>("Status");
    //private DatePicker birthDate = new DatePicker("Birthdate");

    public ClientForm(MainUI ui, ClientController controller){
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

        this.addComponents(name, surname, fathername,  phone, email, components);
        binder.bindInstanceFields(this);
    }
    public void setClient(Client Client) {
        binder.setBean(Client);
        if (Client == null) {
            setVisible(false);
        } else {
            add.setVisible(false);
            update.setVisible(true);
            delete.setVisible(true);
            setVisible(true);
            name.focus();
        }
    }

    public void addButton(){
        setClient(EMPTY);
        add.setVisible(true);
        update.setVisible(false);
        delete.setVisible(false);
        setVisible(true);
    }

    public void add(){
        Client Client = binder.getBean();
        UUID id = IdGenerator.generateId();
        Client.setId(id);
        controller.create(Client);

        ui.updateClients();
        setClient(null);
    }

    public void update(){
        Client Client = binder.getBean();
        controller.update(Client);

        ui.updateClients();
        setClient(null);
    }
    public void delete(){
        Client Client = binder.getBean();
        controller.delete(Client);

        ui.updateClients();
        setClient(null);
    }
}
