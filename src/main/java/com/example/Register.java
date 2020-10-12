package com.example;

import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("Register")
@CssImport("./styles/styles.css")
public class Register extends VerticalLayout {
    public Register() {
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setSizeFull();
        addClassName("register");
        
        // Header
        H1 header = new H1("QARS");
        header.getElement().getThemeList();
        
        // Menu bar 
        MenuBar menuBar = new MenuBar(); 
        menuBar.addClassName("menuBar");
        menuBar.setOpenOnHover(true);

        // Menu bar - Main
        MenuItem miHome = menuBar.addItem("Home");
        miHome.addClickListener(e -> miHome.getUI().ifPresent(ui -> ui.navigate("")));
        MenuItem miRent = menuBar.addItem("Rent");
        MenuItem miLocations = menuBar.addItem("Locations");
        //miLocations.addClickListener(e -> miLocations.getUI().ifPresent(ui -> ui.navigate("Locations")));
        MenuItem miFaq = menuBar.addItem("FAQ");
        //miFaq.addClickListener(e -> miFaq.getUI().ifPresent(ui -> ui.navigate("FAQ")));
        MenuItem miCorona = menuBar.addItem("COVID-19");
        //miCorona.addClickListener(e -> miCorona.getUI().ifPresent(ui -> ui.navigate("Corona")));
        MenuItem miLogin = menuBar.addItem("Login");
        //miLogin.addClickListener(e -> miLogin.getUI().ifPresent(ui -> ui.navigate("Login")));

        // Menu bar - Sub menu's 
        SubMenu smRent = miRent.getSubMenu();
        MenuItem miRentACar = smRent.addItem("Rent a car");
        //miRentACar.addClickListener(e -> miRentACar.getUI().ifPresent(ui -> ui.navigate("Rent")));
        MenuItem miRentInformation = smRent.addItem("Information");
        //miRentInformation.addClickListener(e -> miRentInformation.getUI().ifPresent(ui -> ui.navigate("Information")));
        MenuItem miExtras = smRent.addItem("Extra options");
        //miExtras.addClickListener(e -> miExtras.getUI().ifPresent(ui -> ui.navigate("Extras")));
        
        SubMenu smLogin = miLogin.getSubMenu();
        MenuItem miRegister = smLogin.addItem("Register");
        miRegister.addClickListener(e -> miRegister.getUI().ifPresent(ui -> ui.navigate("Register")));
        add(header);
        add(menuBar);

        H2 pageName = new H2("Register");
        pageName.getElement().getThemeList();
        add(pageName);
    }
}
