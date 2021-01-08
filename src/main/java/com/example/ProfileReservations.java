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
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@Route(value="ProfileReservations")
@PageTitle("User reservations")
@CssImport("./styles/styles.css")
public class ProfileReservations extends VerticalLayout {
    // Database credentials
    private String jdbcURL = "jdbc:postgresql://localhost:5432/ProjectC";
    private String jdbcUsername = "postgres";
    private String jdbcPassword = "asdf";

    private LocalDate today = LocalDate.now();

    // Profile of the logged in user 
    public ProfileReservations() {
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
                getReservations();
            }
        } else {
            UI.getCurrent().navigate("Login");
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
                reservationList.add(new Reservation(resultset.getString("orderid"), resultset.getString("carname"), resultset.getString("licenceplate"), resultset.getString("username"), resultset.getString("location"), LocalDate.parse(resultset.getString("pickupdate")), LocalTime.parse(resultset.getString("pickuptime")), LocalDate.parse(resultset.getString("dropoffdate")), LocalTime.parse(resultset.getString("dropofftime")), "€"+resultset.getString("price")+",00", resultset.getString("ispaid"), resultset.getString("gps"), resultset.getString("insurance"), resultset.getString("wintertires"), resultset.getString("extradriver"), resultset.getString("childseat")));
            } 
            connection.close();
        } catch(SQLException e) {
            System.out.println("Error in connecting postgres");
            e.printStackTrace();
        }  

        // ** Layout here
        Grid<Reservation> reservationGrid = new Grid<>(Reservation.class);
        reservationGrid.setItems(reservationList);
        reservationGrid.removeColumnByKey("orderID");
        reservationGrid.setColumns("carName", "price", "isPaid", "location", "pickupDate", "pickupTime", "dropoffDate", "dropoffTime");
        reservationGrid.setSelectionMode(SelectionMode.SINGLE);
        add(reservationGrid);

        Button cancelButton = new Button("Cancel reservation");
        //cancelButton.cancelVerification();
        reservationGrid.addSelectionListener(e -> add(cancelButton));;
    
    }

    private void cancelVerification(String orderID, LocalDate pickupDate) {
        if (today.isBefore(pickupDate.minusDays(2))) {
            cancelReservation(orderID);
        }
    }

    private void cancelReservation(String orderID) {
        System.out.println("Success!");
        // Database code here
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
            MenuItem menuItemRegister = subMenuLogin.addItem("Logout");
            menuItemRegister.addClickListener(e -> SessionAttributes.logout());
            menuItemRegister.addClickListener(e -> menuItemRegister.getUI().ifPresent(ui -> ui.navigate("Login")));
        }
        add(header, menuBar);
    }
}
