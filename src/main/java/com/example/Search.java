package com.example;

import com.example.Search;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("Search")
@CssImport("./styles/styles.css")
public class Search extends VerticalLayout {

    private static final long serialVersionUID = 1L;

    public Search() {
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setSizeFull();
        addClassName("home");
        addHeader();

        Button mainButton = new Button("Go back to homepage");
        mainButton.addClickListener(e -> UI.getCurrent().navigate("")); //button that returns to the homepage
        add(mainButton);

        //switch cases for each keyword, redirects the user to the cars that match with their searched term
        switch(SessionAttributes.getSearchValue().toUpperCase()) { //toUpperCase is necessary so that the search values aren't blocked by case sensitivity
            case "AUTOMATIC":
            TypeA();
            break;
            case "MANUAL":
            TypeM();
            break;
            case "AIRCONDITIONING":
            AC();
            break;
            case "NO AIRCONDITIONING":
            NoAC();
            break;
            case "OPEL AGILA":
            case "AGILA":
            OpelAB();
            break;
            case "OPEL CORSA":
            case "CORSA":
            OpelC();
            break;
            case "OPEL MERIVA":
            case "MERIVA":
            OpelM();
            break;
            case "OPEL ASTRA":
            case "ASTRA":
            OpelAS();
            break;
            case "OPEL INSIGNIA":
            case "INSIGNIA":
            OpelICT();
            break;
            case "OPEL ZAFIRA":
            case "ZAFIRA":
            OpelZ();
            break;
            case "OPEL MOKKA":
            case "MOKKA":
            OpelMO();
            break;
            default: //default case that returns a notification if none of the above cases are met
                Notification.show("Search not found!");
            break;
        }
    }

    public void TypeA(){ //page for automatic car types
        Span ZCInf = new Span("Fuel: Benzine, Passengers: 5, Doors: 5, Suitcases: 3, " +
        "Transmission: Automatic, Airconditioning: Yes");
        Span MSInf = new Span("Fuel: Benzine, Passengers: 5, Doors: 5, Suitcases: 3, " +
        "Transmission: Automatic, Airconditioning: Yes");

        Image OpelZC = new Image("https://i.ebayimg.com/00/s/NzY4WDEwMjQ=/z/ILAAAOSw~kFflBTq/$_85.JPG", "?");
        OpelZC.setHeight("480px");
        OpelZC.setMaxWidth("700px");
        Button b6 = new Button("Rent this car");
        b6.addClickListener(e -> rentCar("Opel Zafira", "MEK-AE2-224","Rotterdam"));

        Image OpelMS = new Image("https://media.autoweek.nl/m/m1ny1lrbl1fl_480.jpg", "?");
        OpelMS.setHeight("480px");
        OpelMS.setMaxWidth("700px");
        Button b7 = new Button("Rent this car");
        b7.addClickListener(e -> rentCar("Opel Mokka SUV", "434-223-EEE","Rotterdam"));

        FormLayout img = new FormLayout(OpelZC, OpelMS);
        FormLayout Inf = new FormLayout(ZCInf, MSInf);
        FormLayout buttons = new FormLayout(b6, b7);

        add(img, Inf, buttons);
    }

    public void TypeM(){ //page for manual car types
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

        Image OpelAC = new Image("https://media.autoweek.nl/m/m1by14abtkkn_480.jpg", "?");
        OpelAC.setHeight("480px");
        OpelAC.setMaxWidth("700px");
        Button b1 = new Button("Rent this car");
        b1.addClickListener(e -> rentCar("Opel Agila", "232-ABD-435", "Rotterdam"));

        Image OpelCB = new Image("https://www.autozine.nl/cache/nieuws/336/13032_1.jpg", "?");
        OpelCB.setHeight("480px");
        OpelCB.setMaxWidth("700px");
        Button b2 = new Button("Rent this car");
        b2.addClickListener(e -> rentCar("Opel Corsa", "ASD-468-JSJ", "Rotterdam"));

        Image OpelMC = new Image("https://cdn.autowereld.nl/I477815795/640x0/opel-meriva-1-4-turbo-ecotec-120pk-aut-cosmo-ecc-pdc-lmv.jpg", "?");
        OpelMC.setHeight("480px");
        OpelMC.setMaxWidth("700px");
        Button b3 = new Button("Rent this car");
        b3.addClickListener(e -> rentCar("Opel Meriva", "JDD-DEW-323", "Rotterdam"));

        Image OpelAS = new Image("https://dealerimages.dealereprocess.com/image/upload/2090049.jpg", "?");
        OpelAS.setHeight("480px");
        OpelAS.setMaxWidth("700px");
        Button b4 = new Button("Rent this car");
        b4.addClickListener(e -> rentCar("Opel Astra", "111-AAA-222","Rotterdam"));

        Image OpelICT = new Image("https://www.passion4wheels.nl/wp-content/uploads/2013/07/Opel-Insignia-Country-Tourer-286820.jpg", "?");
        OpelICT.setHeight("480px");
        OpelICT.setMaxWidth("700px");
        Button b5 = new Button("Rent this car");
        b5.addClickListener(e -> rentCar("Opel Insignia Country Tourer","FWE-433-3DW","Rotterdam"));

        FormLayout carimg = new FormLayout(OpelAC, OpelCB);
        FormLayout Inf1 = new FormLayout(ACInf, CBInf);
        FormLayout buttons = new FormLayout(b1, b2);

        FormLayout carimg2 = new FormLayout(OpelMC, OpelAS);
        FormLayout Inf2 = new FormLayout(MCInf, ASInf);
        FormLayout buttons2 = new FormLayout(b3, b4);

        FormLayout carimg3 = new FormLayout(OpelICT);
        FormLayout Inf3 = new FormLayout(ICTInf);
        FormLayout buttons3 = new FormLayout(b5);
        
        add(carimg, Inf1, buttons, carimg2, Inf2, buttons2, carimg3, Inf3, buttons3);

    }

    public void AC(){ //page for cars with airconditioning
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

        Image OpelMC = new Image("https://cdn.autowereld.nl/I477815795/640x0/opel-meriva-1-4-turbo-ecotec-120pk-aut-cosmo-ecc-pdc-lmv.jpg", "?");
        OpelMC.setHeight("480px");
        OpelMC.setMaxWidth("700px");
        Button b3 = new Button("Rent this car");
        b3.addClickListener(e -> rentCar("Opel Meriva", "JDD-DEW-323", "Rotterdam"));

        Image OpelAS = new Image("https://dealerimages.dealereprocess.com/image/upload/2090049.jpg", "?");
        OpelAS.setHeight("480px");
        OpelAS.setMaxWidth("700px");
        Button b4 = new Button("Rent this car");
        b4.addClickListener(e -> rentCar("Opel Astra", "111-AAA-222","Rotterdam"));

        Image OpelICT = new Image("https://www.passion4wheels.nl/wp-content/uploads/2013/07/Opel-Insignia-Country-Tourer-286820.jpg", "?");
        OpelICT.setHeight("480px");
        OpelICT.setMaxWidth("700px");
        Button b5 = new Button("Rent this car");
        b5.addClickListener(e -> rentCar("Opel Insignia Country Tourer","FWE-433-3DW","Rotterdam"));

        Image OpelZC = new Image("https://i.ebayimg.com/00/s/NzY4WDEwMjQ=/z/ILAAAOSw~kFflBTq/$_85.JPG", "?");
        OpelZC.setHeight("480px");
        OpelZC.setMaxWidth("700px");
        Button b6 = new Button("Rent this car");
        b6.addClickListener(e -> rentCar("Opel Zafira", "MEK-AE2-224","Rotterdam"));

        Image OpelMS = new Image("https://media.autoweek.nl/m/m1ny1lrbl1fl_480.jpg", "?");
        OpelMS.setHeight("480px");
        OpelMS.setMaxWidth("700px");
        Button b7 = new Button("Rent this car");
        b7.addClickListener(e -> rentCar("Opel Mokka SUV", "434-223-EEE","Rotterdam"));

        FormLayout carimg2 = new FormLayout(OpelMC, OpelAS);
        FormLayout Inf2 = new FormLayout(MCInf, ASInf);
        FormLayout buttons2 = new FormLayout(b3, b4);

        FormLayout carimg3 = new FormLayout(OpelICT, OpelZC);
        FormLayout Inf3 = new FormLayout(ICTInf, ZCInf);
        FormLayout buttons3 = new FormLayout(b5, b6);

        FormLayout carimg4 = new FormLayout(OpelMS);
        FormLayout Inf4 = new FormLayout(MSInf);
        FormLayout buttons4 = new FormLayout(b7);

        add(carimg2, Inf2, buttons2, carimg3, Inf3, buttons3, carimg4, Inf4, buttons4);
    }

    public void NoAC(){ //page for cars with no air conditioning
        Span ACInf = new Span("Fuel: Benzine, Passengers: 5, Doors: 5, Suitcases: 1, " +
        "Transmission: Manual, Airconditioning: No");
        Span CBInf = new Span("Fuel: Benzine, Passengers: 5, Doors: 5, Suitcases: 1, " +
        "Transmission: Manual, Airconditioning: No");

        Image OpelAC = new Image("https://media.autoweek.nl/m/m1by14abtkkn_480.jpg", "?");
        OpelAC.setHeight("480px");
        OpelAC.setMaxWidth("700px");
        Button b1 = new Button("Rent this car");
        b1.addClickListener(e -> rentCar("Opel Agila", "232-ABD-435", "Rotterdam"));

        Image OpelCB = new Image("https://www.autozine.nl/cache/nieuws/336/13032_1.jpg", "?");
        OpelCB.setHeight("480px");
        OpelCB.setMaxWidth("700px");
        Button b2 = new Button("Rent this car");
        b2.addClickListener(e -> rentCar("Opel Corsa", "ASD-468-JSJ", "Rotterdam"));

        FormLayout carimg = new FormLayout(OpelAC, OpelCB);
        FormLayout Inf1 = new FormLayout(ACInf, CBInf);
        FormLayout buttons = new FormLayout(b1, b2);

        add(carimg, Inf1, buttons);
    }

    //The following functions are for specifically searched cars
    public void OpelAB(){
        Span ACInf = new Span("Opel Agila Berlin; Fuel: Benzine, Passengers: 5, Doors: 5, Suitcases: 1, " +
        "Transmission: Manual, Airconditioning: No");

        Image OpelAC = new Image("https://media.autoweek.nl/m/m1by14abtkkn_480.jpg", "?");
        OpelAC.setHeight("480px");
        OpelAC.setMaxWidth("700px");
        Button b1 = new Button("Rent this car");
        b1.addClickListener(e -> rentCar("Opel Agila", "232-ABD-435", "Rotterdam"));

        add(OpelAC, ACInf, b1);
    }

    public void OpelC(){
        Span CBInf = new Span("Opel Corsa BlitZ; Fuel: Benzine, Passengers: 5, Doors: 5, Suitcases: 1, " +
        "Transmission: Manual, Airconditioning: No");

        Image OpelCB = new Image("https://www.autozine.nl/cache/nieuws/336/13032_1.jpg", "?");
        OpelCB.setHeight("480px");
        OpelCB.setMaxWidth("700px");
        Button b2 = new Button("Rent this car");
        b2.addClickListener(e -> rentCar("Opel Corsa", "ASD-468-JSJ", "Rotterdam"));

        add(OpelCB, CBInf, b2);
    }

    public void OpelM(){
        Span MCInf = new Span("Opel Meriva Cosmo; Fuel: Benzine, Passengers: 5, Doors: 5, Suitcases: 1, " +
        "Transmission: Manual, Airconditioning: Yes");

        Image OpelMC = new Image("https://cdn.autowereld.nl/I477815795/640x0/opel-meriva-1-4-turbo-ecotec-120pk-aut-cosmo-ecc-pdc-lmv.jpg", "?");
        OpelMC.setHeight("480px");
        OpelMC.setMaxWidth("700px");
        Button b3 = new Button("Rent this car");
        b3.addClickListener(e -> rentCar("Opel Meriva", "JDD-DEW-323", "Rotterdam"));

        add(OpelMC, MCInf, b3);
    }

    public void OpelAS(){
        Span ASInf = new Span("Opel Astra Sport; Fuel: Benzine, Passengers: 5, Doors: 3, Suitcases: 2, " +
        "Transmission: Manual, Airconditioning: Yes");

        Image OpelAS = new Image("https://dealerimages.dealereprocess.com/image/upload/2090049.jpg", "?");
        OpelAS.setHeight("480px");
        OpelAS.setMaxWidth("700px");
        Button b4 = new Button("Rent this car");
        b4.addClickListener(e -> rentCar("Opel Astra", "111-AAA-222","Rotterdam"));

        add(OpelAS, ASInf,b4);
    }

    public void OpelICT(){
        Span ICTInf = new Span("Opel Insignia Country Tourer; Fuel: Benzine, Passengers: 5, Doors: 5, Suitcases: 2, " +
        "Transmission: Manual, Airconditioning: Yes");

        Image OpelICT = new Image("https://www.passion4wheels.nl/wp-content/uploads/2013/07/Opel-Insignia-Country-Tourer-286820.jpg", "?");
        OpelICT.setHeight("480px");
        OpelICT.setMaxWidth("700px");
        Button b5 = new Button("Rent this car");
        b5.addClickListener(e -> rentCar("Opel Insignia Country Tourer","FWE-433-3DW","Rotterdam"));

        add(OpelICT, ICTInf, b5);
    }

    public void OpelZ(){
        Span ZCInf = new Span("Opel Zafira Cosmo; Fuel: Benzine, Passengers: 5, Doors: 5, Suitcases: 3, " +
        "Transmission: Automatic, Airconditioning: Yes");

        Image OpelZC = new Image("https://i.ebayimg.com/00/s/NzY4WDEwMjQ=/z/ILAAAOSw~kFflBTq/$_85.JPG", "?");
        OpelZC.setHeight("480px");
        OpelZC.setMaxWidth("700px");
        Button b6 = new Button("Rent this car");
        b6.addClickListener(e -> rentCar("Opel Zafira", "MEK-AE2-224","Rotterdam"));

        add(OpelZC, ZCInf, b6);
    }

    public void OpelMO(){
        Span MSInf = new Span("Opel Mokka Selection; Fuel: Benzine, Passengers: 5, Doors: 5, Suitcases: 3, " +
        "Transmission: Automatic, Airconditioning: Yes");

        Image OpelMS = new Image("https://media.autoweek.nl/m/m1ny1lrbl1fl_480.jpg", "?");
        OpelMS.setHeight("480px");
        OpelMS.setMaxWidth("700px");
        Button b7 = new Button("Rent this car");
        b7.addClickListener(e -> rentCar("Opel Mokka SUV", "434-223-EEE","Rotterdam"));

        add(OpelMS, MSInf, b7);
    }

    private void rentCar(String car, String licencePlate, String location) {
        SessionAttributes.selectCar(car, licencePlate, location);
        UI.getCurrent().navigate("Rent");
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
        MenuItem menuItemReview = menuBar.addItem("Reviews");
        menuItemReview.addClickListener(e -> menuItemReview.getUI().ifPresent(ui -> ui.navigate("Reviews")));
        MenuItem menuItemLogin;
        if (SessionAttributes.getLoggedIn() == null || SessionAttributes.getLoggedIn() == "false") {
            menuItemLogin = menuBar.addItem("Login");
            menuItemLogin.addComponentAsFirst(new Icon(VaadinIcon.USER));
            menuItemLogin.addClickListener(e -> menuItemLogin.getUI().ifPresent(ui -> ui.navigate("Login")));
        } else {
            menuItemLogin = menuBar.addItem("Profile");
            menuItemLogin.addComponentAsFirst(new Icon(VaadinIcon.USER));
            menuItemLogin.addClickListener(e -> menuItemLogin.getUI().ifPresent(ui -> ui.navigate("Profile")));
        }

        // Menu bar - Sub menu's 
        SubMenu subMenuRent = menuItemRent.getSubMenu();
        MenuItem menuItemRentInformation = subMenuRent.addItem("Information");
        menuItemRentInformation.addClickListener(e -> menuItemRentInformation.getUI().ifPresent(ui -> ui.navigate("Information")));
        
        SubMenu subMenuLogin = menuItemLogin.getSubMenu();
        if (SessionAttributes.getLoggedIn() == null || SessionAttributes.getLoggedIn() == "false") {
            MenuItem menuItemRegister = subMenuLogin.addItem("Register");
            menuItemRegister.addClickListener(e -> menuItemRegister.getUI().ifPresent(ui -> ui.navigate("Register")));
        } else {
            MenuItem menuItemReservations = subMenuLogin.addItem("Reservations");
            menuItemReservations.addClickListener(e -> menuItemReservations.getUI().ifPresent(ui -> ui.navigate("ProfileReservations")));
            MenuItem menuItemLogout = subMenuLogin.addItem("Logout");
            menuItemLogout.addClickListener(e -> SessionAttributes.logout());
            menuItemLogout.addClickListener(e -> menuItemLogout.getUI().ifPresent(ui -> ui.navigate("Login")));
        }
        add(header, menuBar);
    }
}
