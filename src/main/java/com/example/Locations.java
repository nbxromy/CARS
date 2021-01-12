package com.example;

import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value="Locations")
@PageTitle("Locations")
@CssImport("./styles/styles.css")
public class Locations extends VerticalLayout {
    
    private Grid<Locations> grid;

    @Id
    String  countryCode;
    @Id
    String  City;
    @Id
    String  Address;

    @Id
    private Button search;

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String jdbcURL = "jdbc:postgresql://localhost:5432/Test_Project";
    private String jdbcUsername = "postgres";
    private String jdbcPassword = "asd";
    public List<Locations> locationlist = new ArrayList<>();

    public Locations() {
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setSizeFull();
        addHeader();

        H2 pageName = new H2("Locations");

        // Form layout to search locations
        FormLayout searchLayout = new FormLayout();
        TextField searchField = new TextField("Search on country code"); 

        // Button to search on locations
        Button searchButton = new Button("Search");
        searchButton.addClickListener(e -> {
            SessionAttributes.setSearchLocation(searchField.getValue().toUpperCase());
            UI.getCurrent().getPage().reload(); 
        });

        // Button to see all locations
        Button resetButton = new Button("Get all results");
        resetButton.addClickListener(e -> {
            SessionAttributes.setSearchLocation("");
            UI.getCurrent().getPage().reload(); 
        });
        
        // Check if a location is searched or not
        if (SessionAttributes.getSearchLocation() == null || SessionAttributes.getSearchLocation() == "") {
            // Show all locations
            getLocations();
        } else {
            // Show filtered locations
            getFilteredLocations(SessionAttributes.getSearchLocation());
            pageName = new H2("Locations - " + SessionAttributes.getSearchLocation());
        }

        // Add everything to page
        searchLayout.add(searchField, searchButton, resetButton);
        searchLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP), 
            new FormLayout.ResponsiveStep("490px", 3, FormLayout.ResponsiveStep.LabelsPosition.TOP));
        add(pageName, searchLayout);

        // Table (grid) for the 3 values
        grid = new Grid<>(Locations.class, false);
        grid.setItems(locationlist);
        grid.removeAllColumns();
        grid.addColumn(Locations::getCountryCode).setHeader("Address");
        grid.addColumn(Locations::getCity).setHeader("City");
        grid.addColumn(Locations::getAddress).setHeader("Country code");
        grid.setHeightFull();
        add(grid);
    }

    public Locations(String countrycode, String city, String address){
        super();
        countryCode = countrycode;
        City = city;
        Address = address;
    }

    // Function to get all locations
    public void getLocations(){
        try{
            Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
            String sql = "SELECT * FROM locations";
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery(sql);
            while(resultset.next()){
                String address = resultset.getString(5);
                String city= resultset.getString(4);
                String countrycode = resultset.getString(3);
                
                locationlist.add(new Locations(address,city,countrycode));
            }
            connection.close();
        }
        catch(Exception e){
            
        }
    }
    
    // Function to get all filtered locations
    public void getFilteredLocations(String checkcode){
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
            String sql = "SELECT * FROM locations WHERE countrycode='"+checkcode+"'";
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery(sql);
            if (resultset.next()) {
                while(resultset.next()){
                    String address = resultset.getString(5);
                    String city= resultset.getString(4);
                    String countrycode = resultset.getString(3);
                
                    locationlist.add(new Locations(address,city,countrycode));
                }
            } else {
                Notification.show("No results found");
            }
            connection.close();
        } catch(SQLException e) {
   
        } 
    }

    public String getCountryCode() {
        return countryCode;
    }
    public String getCity() {
        return City;
    }
    public String getAddress() {
        return Address;
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
