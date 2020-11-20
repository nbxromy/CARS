package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("Register")
@CssImport("./styles/styles.css")
public class Register extends VerticalLayout {
    private static final long serialVersionUID = 1L;
    private LocalDate today = LocalDate.now();
    // Database credentials
    private String jdbcURL = "jdbc:postgresql://localhost:5432/ProjectC";
    private String jdbcUsername = "postgres";
    private String jdbcPassword = "asdf";

    public Register() {
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setSizeFull();
        addClassName("register");
        addHeader();

        H2 pageName = new H2("Register");
        pageName.getElement().getThemeList();

        // Front-end register form
        TextField firstnameField = new TextField("First name");
        TextField lastnameField = new TextField("Last name");
        TextField addressField = new TextField("Address");
        DatePicker birthdateField = new DatePicker("Birth date");
        birthdateField.setClearButtonVisible(true);
        TextField zipField = new TextField("ZIP");
        TextField cityField = new TextField("City");
        TextField countrycodeField = new TextField("Country code");
        TextField emailField = new TextField("E-mail");
        TextField phonenumberField = new TextField("Phone number");
        
        TextField usernameField = new TextField("User name");
        PasswordField passwordField1 = new PasswordField("Password");
        PasswordField passwordField2 = new PasswordField("Password check");

        Span errorMessage = new Span();

        Button registerButton = new Button("Register");
        registerButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        
        FormLayout layout = new FormLayout(pageName, firstnameField, lastnameField, birthdateField, addressField, zipField, cityField, countrycodeField, phonenumberField, emailField, usernameField, passwordField1, passwordField2, errorMessage, registerButton);
        layout.setMaxWidth("500px");
        layout.getStyle().set("margin","0 auto");
        layout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP), new FormLayout.ResponsiveStep("490px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP));
        layout.setColspan(pageName, 2);

        layout.setColspan(errorMessage, 2);
        layout.setColspan(registerButton, 2);

        errorMessage.getStyle().set("color", "var(--lumo-error-text-color)");
        errorMessage.getStyle().set("padding", "15px 0");
        add(layout);
        registerButton.addClickListener(event -> checkCredentials(errorMessage, firstnameField.getValue(), lastnameField.getValue(), addressField.getValue(), birthdateField.getValue(), zipField.getValue(), cityField.getValue(), countrycodeField.getValue(), emailField.getValue(), phonenumberField.getValue(), usernameField.getValue(), passwordField1.getValue(), passwordField2.getValue()));
    }

    // Check given credentials
    private void checkCredentials(Span error, String firstname, String lastname, String address, LocalDate birthdate, String zip, String city, String countrycode, String email, String phonenumber, String username, String password1, String password2) {
        boolean validAge = false;
        boolean validUsername = false;
        boolean validPasswords = false;
        boolean validPhonenumber = false;
        boolean validEmail = false;

        // Check if all fields are filled in
        if (firstname == "" || lastname == "" || address == "" || birthdate == null || zip == "" || city == "" || countrycode == "" || email == "" || phonenumber == "" || username == "" || password1 == "" || password2 == "") {
            error.setText("Fill in all fields!");
        } else {
            // Check if user is not younger than today and at least 21 years old
            if (birthdate.isAfter(today)) {
                error.setText("Invalid age");
                validAge = false;
            } else if (birthdate.plusYears(21).isAfter(today)) {
                error.setText("You need to be at least 21 years old to sign up");
                validAge = false;
            } else {
                validAge = true;
            }

            // Check if username already exists
            validUsername = checkUsername(username);
            if (!validUsername) {
                error.setText("Username already exists");
            }

            // Check if both passwords match
            if (password1.equals(password2)) {
                validPasswords = true;
            } else {
                validPasswords = false;
                error.setText("Passwords do not match");
            }  
            // Check if email is valid
            if (email.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}")) {
                validEmail = true;
            } else {
                validEmail = false;
                error.setText("Invalid email");
            }
            // Check if phonenumber is valid
            if (phonenumber.matches("^[0-9]{10}$")) {
                validPhonenumber = true;
            } else {
                validPhonenumber = false;
                error.setText("Invalid phone number");
            }
            
            // If all credentials are valid, register user
            if (validAge && validUsername && validPasswords && validPhonenumber && validEmail) {
                error.setText("HOORAY!");
                register(firstname, lastname, address, birthdate, zip, city, countrycode, email, phonenumber, username, password1);
            } 
        }    
    }

    // Connect to database and check if username already exists
    private boolean checkUsername(String username) {
        boolean userNotExists = false;
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
            String sql = "SELECT * FROM customers WHERE username='"+username+"'";
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery(sql);
            if (resultset.next()) {
                userNotExists = false;
            } else {
                userNotExists = true;
            }
            connection.close();
        } catch(SQLException e) {
            System.out.println("Error in connecting postgres");
            e.printStackTrace();
        }
        return userNotExists;
    }

    // Connect to database and create new user
    private void register(String firstname, String lastname, String address, LocalDate birthdate, String zip, String city, String countrycode, String email, String phonenumber, String username, String password1) {
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
            String sql = "INSERT INTO customers (username, password, givenname, familyname, countrycode, city, address, zip, phonenumber, emailaddress, birthdate) VALUES ('"+username+"', '"+password1+"', '"+firstname+"', '"+lastname+"', '"+countrycode+"', '"+city+"', '"+address+"', '"+zip+"', '"+phonenumber+"', '"+email+"', '"+birthdate+"')";
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            int rows = statement.executeUpdate(sql);
            if (rows>0) {
                System.out.println("New user is registered");
            }
            connection.close();
        } catch(SQLException e) {
            System.out.println("Error in connecting postgres");
            e.printStackTrace();
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
