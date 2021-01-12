package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@Route(value="ProfileReservations")
@PageTitle("User reservations")
@CssImport("./styles/styles.css")
public class ProfileReservations extends VerticalLayout {
    private static final long serialVersionUID = 1L;
    
    // Database credentials
    private String jdbcURL = "jdbc:postgresql://localhost:5432/ProjectC";
    private String jdbcUsername = "postgres";
    private String jdbcPassword = "asdf";

    private LocalDateTime today = LocalDateTime.now();

    // Profile of the logged in user 
    public ProfileReservations() {
        // Check if the user is logged in
        if (SessionAttributes.getLoggedIn() != null && SessionAttributes.getLoggedIn() == "true") {
            setDefaultHorizontalComponentAlignment(Alignment.CENTER);
            setSizeFull();
            addClassName("profile");
            addHeader();
            H2 pageName = new H2("Welcome " + SessionAttributes.getLoggedUser() + "!");
            add(pageName);

            // Reservation Section
            boolean hasAnyReservations = checkReservations();
            H3 reservationSectionName = new H3("Your reservations");
            add(reservationSectionName);

            // Check if reservation exists
            if (!hasAnyReservations) {
                Span message = new Span();
                message.getStyle().set("color", "var(--lumo-primary-text-color)");
                message.getStyle().set("padding", "15px 0");
                message.setText("No reservations found!");

                Button redirectButton = new Button("Click here to make a reservation");
                redirectButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
                redirectButton.addClickListener(event -> UI.getCurrent().navigate(""));

                add(message, redirectButton);
            } else {
                // Show grid with reservations
                getReservations();
            }
        } else {
            UI.getCurrent().navigate("Login");
            UI.getCurrent().getPage().reload();
        }
    }

    // Check if user has any reservations
    private boolean checkReservations() {
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
            String sql = "SELECT * FROM reservations WHERE username='"+SessionAttributes.getLoggedUser()+"'";
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery(sql);
            if (resultset.next()) {
                return true;
            } 
            connection.close();
            return false;
        } catch(SQLException e) {
            System.out.println("Error in connecting postgres");
            e.printStackTrace();
            return false;
        }  
    } 

    // Get reservations from database
    private void getReservations() {
        List<Reservation> reservationList = new ArrayList<>(); 
        // Show all reservations of logged in user
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
            String sql = "SELECT * FROM reservations WHERE username='"+SessionAttributes.getLoggedUser()+"'";
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery(sql);
            while (resultset.next()) {
                reservationList.add(new Reservation(resultset.getString("orderid"), resultset.getString("carname"),
                    resultset.getString("licenceplate"), resultset.getString("username"), resultset.getString("location"), 
                    LocalDate.parse(resultset.getString("pickupdate")), LocalTime.parse(resultset.getString("pickuptime")), 
                    LocalDate.parse(resultset.getString("dropoffdate")), LocalTime.parse(resultset.getString("dropofftime")), 
                    "€"+resultset.getString("price")+",00", resultset.getString("ispaid"), resultset.getString("gps"), 
                    resultset.getString("insurance"), resultset.getString("wintertires"), resultset.getString("extradriver"), 
                    resultset.getString("childseat")));
            } 
            connection.close();
        } catch(SQLException e) {
            System.out.println("Error in connecting postgres");
            e.printStackTrace();
        }  

        // Reservation layout
        Grid<Reservation> reservationGrid = new Grid<>(Reservation.class);
        reservationGrid.setItems(reservationList);
        reservationGrid.setMultiSort(false);
        reservationGrid.setColumns("orderID", "carName", "licencePlate", "price", "isPaid", "location", "pickupDate",
            "pickupTime", "dropoffDate", "dropoffTime");

        reservationGrid.setSelectionMode(SelectionMode.SINGLE);
        add(reservationGrid);   

        // Error message
        Span errorMessage = new Span();
        errorMessage.getStyle().set("color", "var(--lumo-error-text-color)");
        errorMessage.getStyle().set("padding", "15px 0");

        // Text field for the reservation you want to cancel
        TextField orderIdField = new TextField("Enter your order ID which you want to delete");
        orderIdField.getStyle().set("width", "500px");
        Button cancelButton = new Button("Cancel reservation");

        // Cancel reservation checks
        cancelButton.addClickListener(e -> {
            boolean checkExists = checkReservation(orderIdField.getValue());
            // Check if reservation exists
            if (!checkExists) {
                errorMessage.setText("No reservation found");
            } else {
                boolean checkDate = verificateReservation(orderIdField.getValue());
                // Check if pick up date is not in 48 hours from now on
                if (!checkDate) {
                    errorMessage.setText("You can't cancel a reservation in 48 hours before pickup, please contact"+
                        " your local Qars location");
                } else {
                    cancelReservation(orderIdField.getValue());
                }
            }
        });
        add(errorMessage, orderIdField, cancelButton);
    }

    // Check if given value exists in database
    private boolean checkReservation(String orderID) {
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
            String sql = "SELECT * FROM reservations WHERE username='"+SessionAttributes.getLoggedUser()+
                "' AND orderid='"+orderID+"'";
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery(sql);
            if (resultset.next()) {
                return true;
            } 
            connection.close();
            return false;
        } catch(SQLException e) {
            System.out.println("Error in connecting postgres");
            e.printStackTrace();
            return false;
        }  
    }

    // Check if pickup time is not in 48 hours
    private boolean verificateReservation(String orderID) {
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
            String sql = "SELECT * FROM reservations WHERE username='"+SessionAttributes.getLoggedUser()+
                "' AND orderid='"+orderID+"'";
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery(sql);
            if (resultset.next()) {
                LocalDateTime pickupDateTime = LocalDateTime.of(LocalDate.parse(resultset.getString("pickupdate")),
                    LocalTime.parse(resultset.getString("pickuptime")));

                if (pickupDateTime.isBefore(today.plusHours(48))) {
                    return false;
                } else {
                    return true;
                }
            } 
            connection.close();
            return false;
        } catch(SQLException e) {
            System.out.println("Error in connecting postgres");
            e.printStackTrace();
            return false;
        } 
    }

    // Cancel reservation and delete from database
    private void cancelReservation(String orderID) {
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
            String sql = "DELETE FROM reservations WHERE username='"+SessionAttributes.getLoggedUser()+
                "' AND orderid='"+orderID+"'";
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            connection.close();
        } catch(SQLException e) {
            System.out.println("Error in connecting postgres");
            e.printStackTrace();
        }  

        UI.getCurrent().getPage().reload();
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
