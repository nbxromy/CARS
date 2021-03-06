package com.example;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
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
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

@Route
@PageTitle("Homepage")
@PWA(name = "Test Application", shortName = "Test App", description = "This is a test website for Qars.", enableInstallPrompt = false)
@CssImport("./styles/styles.css")
public class MainView extends VerticalLayout {

    private static final long serialVersionUID = 1L;

    private TextField test = new TextField("Enter search term");

    //Search function for the homepage, checks for certain keywords and then redirects to the search page if they've been met
    public void Search(){
        test.setSizeFull();
        Button button = new Button("Search");
        button.addClickListener(e -> { 
            switch(test.getValue().toUpperCase()){
                case "AUTOMATIC":
                SessionAttributes.setSearchValue(test.getValue());
                UI.getCurrent().navigate("Search");
                break;
                case "MANUAL":
                SessionAttributes.setSearchValue(test.getValue());
                UI.getCurrent().navigate("Search");
                break;
                case "AIRCONDITIONING":
                SessionAttributes.setSearchValue(test.getValue());
                UI.getCurrent().navigate("Search");
                break;
                case "NO AIRCONDITIONING":
                SessionAttributes.setSearchValue(test.getValue());
                UI.getCurrent().navigate("Search");
                break;
                case "OPEL AGILA":
                case "AGILA":
                SessionAttributes.setSearchValue(test.getValue());
                UI.getCurrent().navigate("Search");
                break;
                case "OPEL CORSA":
                case "CORSA":
                SessionAttributes.setSearchValue(test.getValue());
                UI.getCurrent().navigate("Search");
                break;
                case "OPEL MERIVA":
                case "MERIVA":
                SessionAttributes.setSearchValue(test.getValue());
                UI.getCurrent().navigate("Search");
                break;
                case "OPEL ASTRA":
                case "ASTRA":
                SessionAttributes.setSearchValue(test.getValue());
                UI.getCurrent().navigate("Search");
                break;
                case "OPEL INSIGNIA":
                case "INSIGNIA":
                SessionAttributes.setSearchValue(test.getValue());
                UI.getCurrent().navigate("Search");
                break;
                case "OPEL ZAFIRA":
                case "ZAFIRA":
                SessionAttributes.setSearchValue(test.getValue());
                UI.getCurrent().navigate("Search");
                break;
                case "OPEL MOKKA":
                case "MOKKA":
                SessionAttributes.setSearchValue(test.getValue());
                UI.getCurrent().navigate("Search");
                break;
                default:
                    Notification.show("Search not found!");
                break;
            }
        });
        FormLayout searchBox = new FormLayout(test, button);
        add(searchBox);
    }

    //The actual home page with the different car types, attributes and rent buttons
    public MainView() {
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setSizeFull();
        addClassName("home");
        addHeader(); //For the menu bar
        Search(); //Activates the search box

        H2 title = new H2("WELCOME!");
        Image logo = new Image("https://i.pinimg.com/564x/29/a8/b5/29a8b5bafb290cc02b1e652e694ac328.jpg", "?");
        logo.setHeight("480px");
        logo.setMaxWidth("480px");
        H3 header = new H3("To the QARS rental service!");

        //Following section has information on all the cars
        Span dir = new Span("Please click on one of the following cars to make an order");
        Span ACInf = new Span("Opel Agila Berlin; Fuel: Benzine, Passengers: 5, Doors: 5, Suitcases: 1, " +
        "Transmission: Manual, Airconditioning: No");
        Span CBInf = new Span("Opel Corsa BlitZ; Fuel: Benzine, Passengers: 5, Doors: 5, Suitcases: 1, " +
        "Transmission: Manual, Airconditioning: No");
        Span MCInf = new Span("Opel Meriva Cosmo; Fuel: Benzine, Passengers: 5, Doors: 5, Suitcases: 1, " +
        "Transmission: Manual, Airconditioning: Yes");
        Span ASInf = new Span("Opel Astra Sport; Fuel: Benzine, Passengers: 5, Doors: 3, Suitcases: 2, " +
        "Transmission: Manual, Airconditioning: Yes");
        Span ICTInf = new Span("Opel Insignia Country Tourer; Fuel: Benzine, Passengers: 5, Doors: 5, Suitcases: 2, " +
        "Transmission: Manual, Airconditioning: Yes");
        Span ZCInf = new Span("Opel Zafira Cosmo; Fuel: Benzine, Passengers: 5, Doors: 5, Suitcases: 3, " +
        "Transmission: Automatic, Airconditioning: Yes");
        Span MSInf = new Span("Opel Mokka Selection; Fuel: Benzine, Passengers: 5, Doors: 5, Suitcases: 3, " +
        "Transmission: Automatic, Airconditioning: Yes");
        
        //Following section has pictures of every car, as well as the buttons needed to redirect to the rent pages
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

        //Following section puts all of the above code into individual formlayouts and then adds them to the page so that it looks nice
        //and even instead of messy
        FormLayout lay = new FormLayout(title);
        FormLayout lay2 = new FormLayout(header);
        FormLayout carimg = new FormLayout(OpelAC, OpelCB);
        FormLayout Inf1 = new FormLayout(ACInf, CBInf);
        FormLayout buttons = new FormLayout(b1, b2);

        FormLayout carimg2 = new FormLayout(OpelMC, OpelAS);
        FormLayout Inf2 = new FormLayout(MCInf, ASInf);
        FormLayout buttons2 = new FormLayout(b3, b4);

        FormLayout carimg3 = new FormLayout(OpelICT, OpelZC);
        FormLayout Inf3 = new FormLayout(ICTInf, ZCInf);
        FormLayout buttons3 = new FormLayout(b5, b6);

        FormLayout carimg4 = new FormLayout(OpelMS, logo);
        FormLayout Inf4 = new FormLayout(MSInf);
        FormLayout buttons4 = new FormLayout(b7);

        
        add(lay, lay2, dir, carimg, Inf1, buttons, carimg2, Inf2, buttons2, carimg3, Inf3, buttons3, carimg4, Inf4, buttons4);
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

        // If neither admin or employee or another user is logged in, show employee login menu.
        if((SessionAttributes.getEmployeeLogin()=="false" || SessionAttributes.getEmployeeLogin() == null)&& (SessionAttributes.getAdminLogin()=="false" || SessionAttributes.getAdminLogin()== null) && (SessionAttributes.getLoggedIn()=="false") || SessionAttributes.getLoggedIn()==null){
            MenuItem menuItemEmployeeLogin = subMenuLogin.addItem("Employee login");
            menuItemEmployeeLogin.addClickListener(e -> menuItemEmployeeLogin.getUI().ifPresent(ui -> ui.navigate("employeeLogin")));
        }
        // If employee is logged in, show employee menu
        MenuItem menuItemEmployee;
        if(SessionAttributes.getEmployeeLogin() =="true"){
            menuItemEmployee = menuBar.addItem("Employee");
            menuItemEmployee.addClickListener(e -> menuItemEmployee.getUI().ifPresent(ui -> ui.navigate("Employee")));
            MenuItem menuItemEmployeeLogin = subMenuLogin.addItem("Employee log out");
            menuItemEmployeeLogin.addClickListener(e -> SessionAttributes.employeeLogout());
            menuItemEmployeeLogin.addClickListener(e -> menuItemEmployeeLogin.getUI().ifPresent(ui -> ui.navigate("FAQ")));
        }
        // If admin is logged in, show admin menu
        MenuItem menuItemAdmin;
        if(SessionAttributes.getAdminLogin()=="true"){
            menuItemAdmin = menuBar.addItem("Admin");
            menuItemAdmin.addClickListener(e -> menuItemAdmin.getUI().ifPresent(ui -> ui.navigate("Admin")));
            
            SubMenu subMenuAdmin = menuItemAdmin.getSubMenu();
            MenuItem menuItemActiveBookings = subMenuAdmin.addItem("Active bookings");
            menuItemActiveBookings.addClickListener(e -> menuItemAdmin.getUI().ifPresent(ui -> ui.navigate("activeBookings")));
            MenuItem menuItemFinishedBookings = subMenuAdmin.addItem("Finished bookings");
            menuItemFinishedBookings.addClickListener(e -> menuItemAdmin.getUI().ifPresent(ui -> ui.navigate("finishedBookings")));
            MenuItem menuItemAdminLogin = subMenuLogin.addItem("Admin logout");
            menuItemAdminLogin.addClickListener(e -> SessionAttributes.adminLogout());
            menuItemAdminLogin.addClickListener(e -> menuItemAdminLogin.getUI().ifPresent(ui -> ui.navigate("FAQ")));
        }
        add(header, menuBar);
    }
}
