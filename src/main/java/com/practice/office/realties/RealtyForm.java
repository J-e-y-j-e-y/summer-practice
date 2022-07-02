package com.practice.office.realties;

import com.practice.office.MainUI;

import com.practice.office.utils.IdGenerator;
import com.vaadin.data.Binder;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;

public class RealtyForm extends FormLayout {
    private MainUI ui;
    private RealtyController controller;
    private final Realty EMPTY = new Realty();

    private TextField neighbourhood = new TextField("neighbourhood");
    private TextField address = new TextField("address");
    private TextField square = new TextField("square");
    private TextField roomNumber = new TextField("roomNumber");
    private TextField price = new TextField("price");
    private TextField cadastralNumber = new TextField("cadastralNumber");

    private Button add = new Button("Add");
    private Button update = new Button("Update");
    private Button delete = new Button("Delete");

    private Binder<Realty> binder = new Binder<>(Realty.class);

    public RealtyForm(MainUI ui, RealtyController controller){
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

        this.addComponents(neighbourhood, address, cadastralNumber, components);
        binder.bind(square, sq -> String.valueOf(sq), null);
        binder.bind(roomNumber, sq -> String.valueOf(sq), null);
        binder.bind(price, sq -> String.valueOf(sq), null);
        binder.bindInstanceFields(this);
    }
    public void setRealty(Realty realty) {
        binder.setBean(realty);
        if (realty == null) {
            setVisible(false);
        } else {
            add.setVisible(false);
            update.setVisible(true);
            delete.setVisible(true);
            setVisible(true);
            neighbourhood.focus();
        }
    }

    public void addButton(){
        setRealty(EMPTY);
        add.setVisible(true);
        update.setVisible(false);
        delete.setVisible(false);
        setVisible(true);
    }

    public void add(){
        Realty Realty = binder.getBean();
        int id = IdGenerator.generateId();
        Realty.setId(id);
        controller.create(Realty);

        ui.updateRealties();
        setRealty(null);
    }

    public void update(){
        Realty Realty = binder.getBean();
        controller.update(Realty);

        ui.updateRealties();
        setRealty(null);
    }
    public void delete(){
        Realty Realty = binder.getBean();
        controller.delete(Realty);

        ui.updateRealties();
        setRealty(null);
    }
}
