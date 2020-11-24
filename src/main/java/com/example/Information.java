package com.example;

import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value="Information")
@CssImport("./styles/styles.css")
public class Information extends VerticalLayout {
    private static final long serialVersionUID = 1L;

    public Information() {
        setSizeFull();
        addHeader();

        H2 pageName = new H2("Info Page");
        pageName.getElement().getThemeList();
        
        H3 header = new H3("Welcome to our rental information page!");
        Span label = new Span("This page is meant to help new users make their first order on our website." + 
        " The 'Rent a car' page shows all of our current available cars, as well as their status and availability.");
        Span span = new Span("Our 'Extra options' page is a seperate tab where you can add possible 'extras' to your choice" +
        ", such as child seats, GPS and/or radio.");
        H3 newH = new H3("QARS is a company meant to help you");
        Span sl = new Span("Our dev team at QARS wants to create an application that can be as user-friendly as it should be" + 
        ", which also means we strive to keep the user happy and make sure their experience using the website/app is one they can be satisfied with." +
        " Because of that, we believe it to be a good idea to register (by clicking the 'Register' tab under 'Log in') so that the process of choosing, " +
        "adding and renting one of our vehicles can proceed as smoothly as possible.");

        Image img = new Image("https://www.extremetech.com/wp-content/uploads/2019/12/SONATA-hero-option1-764A5360-edit.jpg", "?");
        img.setHeight("500px");
        img.setWidth("700px");
        Image img2 = new Image("https://www.extremetech.com/wp-content/uploads/2019/12/SONATA-hero-option1-764A5360-edit.jpg", "?");
        img2.setHeight("200px");
        img2.setWidth("200px");

        FormLayout layout = new FormLayout(pageName);
        //layout.add(pageName);
        FormLayout l2 = new FormLayout(header);
        //l2.add(header);
        FormLayout l3 = new FormLayout(label);  
        //l3.add(label);
        FormLayout l4 = new FormLayout(span);
        //l4.add(span);
        FormLayout l5 = new FormLayout(sl);
        //l5.add(sl);
        FormLayout image = new FormLayout(img, img2);

        

        add(layout, l2, l3, l4, image);
        add(newH, l5);
        }

    // HEADER
    public void addHeader() {
        // Header
        H1 header = new H1("QARS");
        header.getElement().getThemeList();
        
        // Menu bar 
        MenuBar menuBar = new MenuBar(); 
        menuBar.addClassName("menuBar");
        menuBar.addThemeVariants(MenuBarVariant.LUMO_PRIMARY);
        menuBar.setOpenOnHover(true);

        // Menu bar - Main
        MenuItem menuItemHome = menuBar.addItem("Home");
        menuItemHome.addClickListener(e -> menuItemHome.getUI().ifPresent(ui -> ui.navigate("")));
        MenuItem menuItemRent = menuBar.addItem("Rent");
        MenuItem menuItemLocations = menuBar.addItem("Locations");
        menuItemLocations.addClickListener(e -> menuItemLocations.getUI().ifPresent(ui -> ui.navigate("Locations")));
        MenuItem menuItemFaq = menuBar.addItem("FAQ");
        menuItemFaq.addClickListener(e -> menuItemFaq.getUI().ifPresent(ui -> ui.navigate("FAQ")));
        MenuItem menuItemCorona = menuBar.addItem("COVID-19");
        menuItemCorona.addClickListener(e -> menuItemCorona.getUI().ifPresent(ui -> ui.navigate("Corona")));
        MenuItem menuItemLogin = menuBar.addItem("Login");
        menuItemLogin.addComponentAsFirst(new Icon(VaadinIcon.USER));
        menuItemLogin.addClickListener(e -> menuItemLogin.getUI().ifPresent(ui -> ui.navigate("Login")));

        // Menu bar - Sub menu's 
        SubMenu subMenuRent = menuItemRent.getSubMenu();
        MenuItem menuItemRentACar = subMenuRent.addItem("Rent a car");
        menuItemRentACar.addClickListener(e -> menuItemRentACar.getUI().ifPresent(ui -> ui.navigate("Rent")));
        MenuItem menuItemRentInformation = subMenuRent.addItem("Information");
        menuItemRentInformation.addClickListener(e -> menuItemRentInformation.getUI().ifPresent(ui -> ui.navigate("Information")));
        MenuItem menuItemExtras = subMenuRent.addItem("Extra options");
        menuItemExtras.addClickListener(e -> menuItemExtras.getUI().ifPresent(ui -> ui.navigate("Extras")));
        
        SubMenu subMenuLogin = menuItemLogin.getSubMenu();
        MenuItem menuItemRegister = subMenuLogin.addItem("Register");
        menuItemRegister.addClickListener(e -> menuItemRegister.getUI().ifPresent(ui -> ui.navigate("Register")));
        add(header, menuBar);
    }
}
