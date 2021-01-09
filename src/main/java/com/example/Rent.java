package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value="Rent")
@PageTitle("Rent a car")
@CssImport("./styles/styles.css")
public class Rent extends VerticalLayout {
    private static final long serialVersionUID = 1L;
    public LocalDate today = LocalDate.now(); 

    // Database credentials
    private String jdbcURL = "jdbc:postgresql://localhost:5432/ProjectC";
    private String jdbcUsername = "postgres";
    private String jdbcPassword = "asdf";

    // Variables for calculating and showing total price
    private int price = 0;
    private Span priceMessage = new Span();
    
    // Rental form layout
    private H2 title = new H2("Rent a car");
    private TextField carField = new TextField("Car");
    private TextField licencePlateField = new TextField("Licence plate");
    private TextField pickupLocationField = new TextField("Pick up location");
    private DatePicker pickupDatePicker = new DatePicker();
    private TimePicker pickupTimePicker = new TimePicker();
    private DatePicker dropoffDatePicker = new DatePicker();
    private TimePicker dropoffTimePicker = new TimePicker();
    private CheckboxGroup<String> checkboxExtras = new CheckboxGroup<>();

    public Rent() {
        if (SessionAttributes.getLoggedIn() != null || SessionAttributes.getLoggedIn() == "true") {            
            setDefaultHorizontalComponentAlignment(Alignment.CENTER);
            setSizeFull();
            addHeader();
            
            // Retal form layout
            carField.setValue(SessionAttributes.getSelectedCar());
            carField.setReadOnly(true);

            licencePlateField.setValue(SessionAttributes.getSelectedLicencePlate());
            licencePlateField.setReadOnly(true);

            pickupLocationField.setValue(SessionAttributes.getSelectedLocation());
            pickupLocationField.setReadOnly(true);

            pickupDatePicker.setPlaceholder("Pick up date");
            pickupDatePicker.setRequired(true);
            pickupTimePicker.setPlaceholder("Pick up time");
            pickupTimePicker.setRequired(true);

            dropoffDatePicker.setPlaceholder("Drop off date");
            dropoffDatePicker.setRequired(true);
            dropoffTimePicker.setPlaceholder("Drop off time");
            dropoffTimePicker.setRequired(true);

            checkboxExtras.setLabel("Extra");
            checkboxExtras.setItems("GPS", "Insurance", "Winter tires", "Extra driver", "Childseat");
            checkboxExtras.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);
            
            // Show total price layout
            priceMessage.getStyle().set("color", "var(--lumo-primary-text-color)");
            priceMessage.getStyle().set("padding", "15px 0");
            priceMessage.setText("Total price: €"+price+",00");

            // Show error layout
            Span errorMessage = new Span();
            errorMessage.getStyle().set("color", "var(--lumo-error-text-color)");
            errorMessage.getStyle().set("padding", "15px 0");

            // Calculate total price button
            Button calculateButton = new Button("Calculate total price");
            calculateButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
            calculateButton.addClickListener(e -> calculatePrice());

            // Confirm button which redirects to Payment
            Button confirmButton = new Button("Confirm choice and proceed to pay with credit card");
            confirmButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            confirmButton.addClickListener(e -> checkDatesAndTimes(pickupDatePicker.getValue(), pickupTimePicker.getValue(), dropoffDatePicker.getValue(), dropoffTimePicker.getValue(), errorMessage, true));

            // Confirm button which redirects to Profile Reservations
            Button confirmButton2 = new Button("Confirm choice and proceed to pay on location");
            confirmButton2.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
            confirmButton2.addClickListener(e -> checkDatesAndTimes(pickupDatePicker.getValue(), pickupTimePicker.getValue(), dropoffDatePicker.getValue(), dropoffTimePicker.getValue(), errorMessage, false));

            // Cancel button to cancel reservation and redirect to Homepage
            Button cancelButton = new Button("Cancel");
            cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            cancelButton.addClickListener(e -> UI.getCurrent().navigate(""));

            add(title, carField, licencePlateField, pickupLocationField, pickupDatePicker, pickupTimePicker, dropoffDatePicker, dropoffTimePicker, checkboxExtras, calculateButton, priceMessage, errorMessage, confirmButton, confirmButton2, cancelButton);
        } else {
            UI.getCurrent().navigate("Login");
            UI.getCurrent().getPage().reload();
        }
    }

    // Calculate total rental price
    public void calculatePrice() {
        price = 0;
        // Calculate total hours difference between chosen dates and times
        LocalDateTime pickupDateTime = LocalDateTime.of(pickupDatePicker.getValue(), pickupTimePicker.getValue());
        LocalDateTime dropoffDateTime = LocalDateTime.of(dropoffDatePicker.getValue(), dropoffTimePicker.getValue());
        long hoursBetween = ChronoUnit.HOURS.between(pickupDateTime, dropoffDateTime);
        int hours = (int)hoursBetween;
        
        // Car rental costs: €10 per hour
        price+=(hours*10);

        // Check checkbox on values
        Set<String> extras = checkboxExtras.getValue();
        if (extras.contains("GPS")) {
            // GPS costs: €5 total
            price+=5;
        } 
        if (extras.contains("Insurance")) {
            // Insurance costs: €2 per hour
            price+=(hours*2);
        } 
        if (extras.contains("Winter tires")) {
            // Winter tires costs: €10 total
            price+=10;
        }
        if (extras.contains("Extra driver")) {
            // Extra driver costs: €50 total
            price+=50;
        }
        if (extras.contains("Childseat")) {
            // Child seat costs: €5 total
            price+=5;
        }

        // Update message
        priceMessage.setText("Total price: €"+price+",00");
    }

    // Function to check if user entered valid dates and times
    public void checkDatesAndTimes(LocalDate pickupDate, LocalTime pickupTime, LocalDate dropoffDate, LocalTime dropoffTime, Span errorMessage, boolean payed) {  
        if (pickupDate == null || pickupTime == null) {
            errorMessage.setText("Please enter the pick up date and time");
        } else if (dropoffDate == null || dropoffTime == null) {
            errorMessage.setText("Please enter the drop off date and time");
        } else if (pickupDate.isBefore(today)) {
            errorMessage.setText("Please enter a valid pickup date");
        } else if (pickupDate.isAfter(today.plusYears(1))) {
            errorMessage.setText("You can't rent a car for the next year");
        } else if (dropoffDate.isBefore(pickupDate) || dropoffDate.isBefore(today)) {
            errorMessage.setText("Please enter a valid dropoff date");
        } else if (dropoffDate.isAfter(pickupDate.plusMonths(1))) {
            errorMessage.setText("You can rent a car for a maximum of one month in a row");
        } else if (pickupTime.isBefore(LocalTime.parse("08:00:00")) || pickupTime.isAfter(LocalTime.parse("22:00:00"))) {
            errorMessage.setText("Please enter a pick up time between working hours (08:00-22:00)");
        } else if (dropoffTime.isBefore(LocalTime.parse("08:00:00")) || dropoffTime.isAfter(LocalTime.parse("22:00:00"))) {
            errorMessage.setText("Please enter a drop off time between working hours (08:00-22:00)");
        } else {
            calculatePrice();
            createReservation(carField.getValue(), licencePlateField.getValue(), pickupLocationField.getValue(), pickupDate, pickupTime, dropoffDate, dropoffTime, price, payed);
        }
    }

    // Function to create a reservation and store in database
    public void createReservation(String car, String licencePlate, String pickupLocation, LocalDate pickupDate, LocalTime pickupTime, LocalDate dropoffDate, LocalTime dropoffTime, int totalPrice, boolean payed) {
        Set<String> extras = checkboxExtras.getValue();
        boolean hasGPS = false;
        boolean hasInsurance = false;
        boolean hasWinterTires = false;
        boolean hasExtraDriver = false;
        boolean hasChildSeat = false;

        // Check checkbox on values
        if (extras.contains("GPS")) {
            hasGPS = true;
        } 
        if (extras.contains("Insurance")) {
            hasInsurance = true;
        } 
        if (extras.contains("Winter tires")) {
            hasWinterTires = true;
        }
        if (extras.contains("Extra driver")) {
            hasExtraDriver = true;
        }
        if (extras.contains("Childseat")) {
            hasChildSeat = true;
        }

        // Connect to database and create reservation
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
            String sql = "INSERT INTO reservations (carname, licenceplate, username, location, pickupdate, pickuptime, dropoffdate, dropofftime, price, ispaid, gps, insurance, wintertires, extradriver, childseat) VALUES ('"+car+"', '"+licencePlate+"', '"+SessionAttributes.getLoggedUser()+"', '"+pickupLocation+"', '"+pickupDate+"', '"+pickupTime+"', '"+dropoffDate+"', '"+dropoffTime+"', '"+price+"', '"+payed+"', '"+hasGPS+"', '"+hasInsurance+"', '"+hasWinterTires+"', '"+hasExtraDriver+"', '"+hasChildSeat+"')";
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            connection.close();
        } catch(SQLException e) {
            System.out.println("Errosr in connecting postgres");
            e.printStackTrace();
        }
        
        // Redirect to next page
        if (payed==true) {
            UI.getCurrent().navigate("Payment");
        } else {
            UI.getCurrent().navigate("ProfileReservations");
        }
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
