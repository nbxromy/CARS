package com.example;

import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

@Route
@PWA(name = "Test Application", shortName = "Test App", description = "This is a test website for Qars.", enableInstallPrompt = false)
@CssImport("./styles/styles.css")
public class MainView extends VerticalLayout {

    public MainView() {
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setSizeFull();
        addClassName("main-view");
        
        H1 header = new H1("QARS");
        header.getElement().getThemeList();
        
        // Menu bar 
        MenuBar menuBar = new MenuBar(); 
        menuBar.addClassName("menuBar");
        menuBar.setOpenOnHover(true);

        // Menu bar - Main
        MenuItem miHome = menuBar.addItem("Home");
        MenuItem miRent = menuBar.addItem("Rent");
        MenuItem miLocations = menuBar.addItem("Locations");
        MenuItem miFaq = menuBar.addItem("FAQ");
        MenuItem miCorona = menuBar.addItem("COVID-19");
        MenuItem miLogin = menuBar.addItem("Login");

        // Menu bar - Sub menu's 
        SubMenu smRent = miRent.getSubMenu();
        MenuItem miRentACar = smRent.addItem("Rent a car");
        MenuItem miRentInformation = smRent.addItem("Information");
        MenuItem miExtras = smRent.addItem("Extra options");
        
        /*SubMenu smLocations = miLocations.getSubMenu();
        MenuItem miAmsterdam = smLocations.addItem("Amsterdam");
        MenuItem miRotterdam = smLocations.addItem("Rotterdam");
        MenuItem miUtrecht = smLocations.addItem("Utrecht");*/

        SubMenu smLogin = miLogin.getSubMenu();
        MenuItem miRegister = smLogin.addItem("Register");

        add(header);
        add(menuBar);
    }
}
