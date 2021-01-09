package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value="Profile")
@PageTitle("User profile")
@CssImport("./styles/styles.css")
public class Profile extends VerticalLayout {

    private static final long serialVersionUID = 1L;
    
    // Database credentials
    private String jdbcURL = "jdbc:postgresql://localhost:5432/ProjectC";
    private String jdbcUsername = "postgres";
    private String jdbcPassword = "asdf";

    // Date of today
    private LocalDate today = LocalDate.now();

    // Information fields
    private TextField firstnameField = new TextField("First name");
    private TextField lastnameField = new TextField("Last name");
    private DatePicker birthdateField = new DatePicker("Birth date");

    private TextField addressField = new TextField("Address");
    private TextField zipField = new TextField("ZIP");
    private TextField cityField = new TextField("City");
    private TextField countrycodeField = new TextField("Country code");

    private TextField phonenumberField = new TextField("Phone number");
    private TextField emailField = new TextField("E-mail");

    private TextField bsnField = new TextField("BSN");
    private TextField documentnumberField = new TextField("Document number");

    private Icon personalIcon = new Icon(VaadinIcon.USER_STAR); 
    private Icon addressIcon = new Icon(VaadinIcon.HOME);
    private Icon contactIcon = new Icon(VaadinIcon.PHONE);
    private Icon licenceIcon = new Icon(VaadinIcon.USER_CARD);

    private Span errorMessage = new Span();
    private Span successMessage = new Span();

    // Profile of the logged in user 
    public Profile() {
        if (SessionAttributes.getLoggedIn() != null && SessionAttributes.getLoggedIn() == "true") {
            setDefaultHorizontalComponentAlignment(Alignment.CENTER);
            setSizeFull();
            addClassName("profile");
            addHeader();
            H2 pageName = new H2("Welcome " + SessionAttributes.getLoggedUser() + "!");
            add(pageName);

            // Front-end for showing personal details about logged in user
            Accordion information = new Accordion();
        
            // Set input fields as required
            firstnameField.setRequired(true);
            lastnameField.setRequired(true);
            birthdateField.setRequired(true);
            addressField.setRequired(true);
            zipField.setRequired(true);
            cityField.setRequired(true);
            countrycodeField.setRequired(true);
            phonenumberField.setRequired(true);
            emailField.setRequired(true);
            bsnField.setRequired(true);
            documentnumberField.setRequired(true);

            // Icons
            personalIcon.setSize("35px");
            personalIcon.setColor("lightblue");
            addressIcon.setSize("35px");
            addressIcon.setColor("lightblue");
            contactIcon.setSize("35px");
            contactIcon.setColor("lightblue");
            licenceIcon.setSize("35px");
            licenceIcon.setColor("lightblue");

            // Personal information section title
            H3 personalSectionName = new H3("Personal Information");
            
            // Layout personal information
            HorizontalLayout personalLayout = new HorizontalLayout();
            Icon personalIcon = new Icon(VaadinIcon.USER_STAR);
            personalIcon.setSize("35px");
            personalIcon.setColor("lightblue");
            personalLayout.add(personalIcon, firstnameField, lastnameField, birthdateField);
            information.add("Personal Information", personalLayout).addThemeVariants(DetailsVariant.FILLED);

            // Layout address information
            HorizontalLayout addressLayout = new HorizontalLayout();
            Icon addressIcon = new Icon(VaadinIcon.HOME);
            addressIcon.setSize("35px");
            addressIcon.setColor("lightblue");
            addressLayout.add(addressIcon, addressField, zipField, cityField, countrycodeField);
            information.add("Address Information", addressLayout).addThemeVariants(DetailsVariant.FILLED);

            // Layout contact information
            HorizontalLayout contactLayout = new HorizontalLayout();
            Icon contactIcon = new Icon(VaadinIcon.PHONE);
            contactIcon.setSize("35px");
            contactIcon.setColor("lightblue");
            contactLayout.add(contactIcon, phonenumberField, emailField);
            information.add("Contact Information", contactLayout).addThemeVariants(DetailsVariant.FILLED);

            // Layout drivers licence
            HorizontalLayout licenceLayout = new HorizontalLayout();
            Icon licenceIcon = new Icon(VaadinIcon.USER_CARD);
            licenceIcon.setSize("35px");
            licenceIcon.setColor("lightblue");
            licenceLayout.add(licenceIcon, bsnField, documentnumberField);
            information.add("Drivers Licence", licenceLayout).addThemeVariants(DetailsVariant.FILLED);
    
            fillPersonalDetails(SessionAttributes.getLoggedUser());
            
            // Buttons to update or reset personal details with database
            HorizontalLayout buttonLayout = new HorizontalLayout();
            
            Button updateButton = new Button("Update details");
            updateButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            updateButton.addClickListener(event -> updateProfileDetails(SessionAttributes.getLoggedUser()));
            Button resetButton = new Button("Reset details");
            resetButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            resetButton.addClickListener(event -> fillPersonalDetails(SessionAttributes.getLoggedUser()));

            buttonLayout.add(updateButton,resetButton);
            
            // Error message 
            errorMessage.getStyle().set("color", "var(--lumo-error-text-color)");
            errorMessage.getStyle().set("padding", "15px 0");

            // Correct message
            successMessage.getStyle().set("color", "var(--lumo-success-text-color)");
            successMessage.getStyle().set("padding", "15px 0");

            //add(personalSectionName, information, buttonLayout, errorMessage, successMessage);
            add(personalSectionName, information, buttonLayout, errorMessage, successMessage);
        } else {
            UI.getCurrent().navigate("Login");
            UI.getCurrent().getPage().reload();
        }
    }

    // Fill in the personal details layout 
    private void fillPersonalDetails(String username) {
        errorMessage.setText("");
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
            String sql = "SELECT * FROM customers WHERE username='"+username+"'";
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery(sql);
            if (resultset.next()) {
                firstnameField.setValue(resultset.getString("givenname"));
                lastnameField.setValue(resultset.getString("familyname"));
                LocalDate birthdate = LocalDate.parse(resultset.getString("birthdate"));
                if (resultset.getString("birthdate") != null) { birthdateField.setValue(birthdate); }
                addressField.setValue(resultset.getString("address"));
                zipField.setValue(resultset.getString("zip"));
                cityField.setValue(resultset.getString("city"));
                countrycodeField.setValue(resultset.getString("countrycode"));

                phonenumberField.setValue(resultset.getString("phonenumber"));
                emailField.setValue(resultset.getString("emailaddress"));

                if (resultset.getString("bsn") != null) { bsnField.setValue(resultset.getString("bsn")); }
                else { bsnField.setValue(""); }
                if (resultset.getString("documentnumber") != null) { documentnumberField.setValue(resultset.getString("documentnumber")); }
                else { documentnumberField.setValue(""); }
            } 
            connection.close();
        } catch(SQLException e) {
            System.out.println("Error in connecting postgres");
            e.printStackTrace();
        }  
    }

    // Update personal details in database
    private void updateProfileDetails(String username) {
        // Check if fields are not empty
        if (firstnameField.isEmpty()|| lastnameField.isEmpty() || birthdateField.isEmpty() || bsnField.isEmpty() || documentnumberField.isEmpty() || addressField.isEmpty() || zipField.isEmpty() || cityField.isEmpty() || countrycodeField.isEmpty() || phonenumberField.isEmpty() || emailField.isEmpty()) {
            errorMessage.setText("Fill in all fields");
        } else {
            if (specialFieldCheck()) {
                try {
                    Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
                    String sql = "UPDATE customers SET givenname='"+firstnameField.getValue()+"', familyname='"+lastnameField.getValue()+"', birthdate='"+birthdateField.getValue()+"', address='"+addressField.getValue()+"', zip='"+zipField.getValue()+"', city='"+cityField.getValue()+"', countrycode='"+countrycodeField.getValue()+"', phonenumber='"+phonenumberField.getValue()+"', emailaddress='"+emailField.getValue()+"', bsn='"+bsnField.getValue()+"', documentnumber='"+documentnumberField.getValue()+"' WHERE username='"+username+"'";
                    Statement statement = connection.createStatement();
                    statement.executeUpdate(sql);
                    errorMessage.setText("");
                    successMessage.setText("Successfully updated personal details");
                    connection.close();
                } catch(SQLException e) {
                    System.out.println("Error in connecting postgres");
                    e.printStackTrace();
                }   
            }
        }
    }

    // Validate email and phonenumber inputs
    private boolean specialFieldCheck() {
        boolean validAge=false;
        boolean validPhonenumber=false;
        boolean validEmail=false;
        LocalDate birthdate = LocalDate.parse("" + birthdateField.getValue());

        if (birthdate.isAfter(today)) {
            errorMessage.setText("Enter a valid age");
            validAge = false;
        } else if (birthdate.plusYears(21).isAfter(today)) {
            errorMessage.setText("You need to be at least 21 years old to rent a car");
            validAge = false;
        } else {
            validAge = true;
        }

        if (phonenumberField.getValue().matches("^[0-9]{10}$")) {
            validPhonenumber=true;
        } else {
            validPhonenumber=false;
            errorMessage.setText("Enter a valid phonenumber");
        }

        if (emailField.getValue().matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}")) {
            validEmail=true;
        } else {
            validEmail=false;
            errorMessage.setText("Enter a valid email");
        } 

        if (validAge && validPhonenumber && validEmail) {
            return true;
        } else {
            return false;
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
