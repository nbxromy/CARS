package com.example;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
//import com.vaadin.flow.component.charts.model.Label;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
//import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value="Rent")
@CssImport("./styles/styles.css")
public class Rent extends VerticalLayout {
    private static final long serialVersionUID = 1L;

    public Rent() {
        setSizeFull();
        addHeader();

        H2 title = new H2("WELCOME!");
        Image logo = new Image("https://i.pinimg.com/564x/29/a8/b5/29a8b5bafb290cc02b1e652e694ac328.jpg", "?");
        logo.setHeight("480px");
        logo.setMaxWidth("480px");

        Span dir = new Span("Please click on one of the following cars to make an order");
        Span ACInf = new Span("Fuel: Benzine, Passengers: 5, Doors: 5, Suitcases: 1, " +
        "Transmission: Manual, Airconditioning: No");
        Span CBInf = new Span("Fuel: Benzine, Passengers: 5, Doors: 5, Suitcases: 1, " +
        "Transmission: Manual, Airconditioning: No");
        Span MCInf = new Span("Fuel: Benzine, Passengers: 5, Doors: 5, Suitcases: 1, " +
        "Transmission: Manual, Airconditioning: Yes");
        Span ASInf = new Span("Fuel: Benzine, Passengers: 5, Doors: 3, Suitcases: 2, " +
        "Transmission: Manual, Airconditioning: Yes");
        Span ICTInf = new Span("Fuel: Benzine, Passengers: 5, Doors: 5, Suitcases: 2, " +
        "Transmission: Manual, Airconditioning: Yes");
        Span ZCInf = new Span("Fuel: Benzine, Passengers: 5, Doors: 5, Suitcases: 3, " +
        "Transmission: Automatic, Airconditioning: Yes");
        Span MSInf = new Span("Fuel: Benzine, Passengers: 5, Doors: 5, Suitcases: 3, " +
        "Transmission: Automatic, Airconditioning: Yes");
        

        Image OpelAC = new Image("https://media.autoweek.nl/m/m1by14abtkkn_480.jpg", "?");
        OpelAC.setHeight("480px");
        OpelAC.setMaxWidth("700px");
        Button b1 = new Button("Rent this car");
        b1.addClickListener(e -> UI.getCurrent().navigate("Extras"));

        Image OpelCB = new Image("https://www.autozine.nl/cache/nieuws/336/13032_1.jpg", "?");
        OpelCB.setHeight("480px");
        OpelCB.setMaxWidth("700px");
        Button b2 = new Button("Rent this car");
        b2.addClickListener(e -> UI.getCurrent().navigate("Extras"));

        Image OpelMC = new Image("https://cdn.autowereld.nl/I477815795/640x0/opel-meriva-1-4-turbo-ecotec-120pk-aut-cosmo-ecc-pdc-lmv.jpg", "?");
        OpelMC.setHeight("480px");
        OpelMC.setMaxWidth("700px");
        Button b3 = new Button("Rent this car");
        b3.addClickListener(e -> UI.getCurrent().navigate("Extras"));

        Image OpelAS = new Image("https://www.broekhuis.nl/images/vehicleimages/15624633/600_900/image_1.jpg", "?");
        OpelAS.setHeight("480px");
        OpelAS.setMaxWidth("700px");
        Button b4 = new Button("Rent this car");
        b4.addClickListener(e -> UI.getCurrent().navigate("Extras"));

        Image OpelICT = new Image("https://www.passion4wheels.nl/wp-content/uploads/2013/07/Opel-Insignia-Country-Tourer-286820.jpg", "?");
        OpelICT.setHeight("480px");
        OpelICT.setMaxWidth("700px");
        Button b5 = new Button("Rent this car");
        b5.addClickListener(e -> UI.getCurrent().navigate("Extras"));

        Image OpelZC = new Image("https://i.ebayimg.com/00/s/NzY4WDEwMjQ=/z/ILAAAOSw~kFflBTq/$_85.JPG", "?");
        OpelZC.setHeight("480px");
        OpelZC.setMaxWidth("700px");
        Button b6 = new Button("Rent this car");
        b6.addClickListener(e -> UI.getCurrent().navigate("Extras"));

        Image OpelMS = new Image("https://media.autoweek.nl/m/m1ny1lrbl1fl_480.jpg", "?");
        OpelMS.setHeight("480px");
        OpelMS.setMaxWidth("700px");
        Button b7 = new Button("Rent this car");
        b7.addClickListener(e -> UI.getCurrent().navigate("Extras"));

        FormLayout lay = new FormLayout(title);
        FormLayout carimg = new FormLayout(OpelAC, OpelCB);
        FormLayout buttons = new FormLayout(b1, b2);
        FormLayout Inf1 = new FormLayout(ACInf, CBInf);

        FormLayout carimg2 = new FormLayout(OpelMC, OpelAS);
        FormLayout buttons2 = new FormLayout(b3, b4);
        FormLayout Inf2 = new FormLayout(MCInf, ASInf);

        FormLayout carimg3 = new FormLayout(OpelICT, OpelZC);
        FormLayout buttons3 = new FormLayout(b5, b6);
        FormLayout Inf3 = new FormLayout(ICTInf, ZCInf);

        FormLayout carimg4 = new FormLayout(OpelMS, logo);
        FormLayout buttons4 = new FormLayout(b7);
        FormLayout Inf4 = new FormLayout(MSInf);


        add(lay, dir, carimg, Inf1, buttons, 
        carimg2, Inf2, buttons2, carimg3, Inf3, buttons3, carimg4, Inf4, buttons4);
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