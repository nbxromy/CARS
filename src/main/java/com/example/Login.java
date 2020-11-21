package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
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

@Route(value="Login")
@CssImport("./styles/styles.css")
public class Login extends VerticalLayout {
    // Database credentials
    private String jdbcURL = "jdbc:postgresql://localhost:5432/ProjectC";
    private String jdbcUsername = "postgres";
    private String jdbcPassword = "asdf";

    public Login() {
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setSizeFull();
        addClassName("login");
        addHeader();

        // Front-end login form 
        H2 pageName = new H2("Log in");
        pageName.getElement().getThemeList();

        TextField usernameField = new TextField("User name");
        PasswordField passwordField = new PasswordField("Password");
        Span errorMessage = new Span();

        Button loginButton = new Button("Log in");
        loginButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        FormLayout layout = new FormLayout(pageName, usernameField, passwordField, errorMessage, loginButton);
        layout.setMaxWidth("300px");
        layout.getStyle().set("margin", "0 auto");
        layout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP), new FormLayout.ResponsiveStep("490px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP));
        layout.setColspan(pageName, 2);
        layout.setColspan(errorMessage, 2);
        layout.setColspan(loginButton, 2);

        errorMessage.getStyle().set("color", "var(--lumo-error-text-color)");
        errorMessage.getStyle().set("padding", "15px 0");
        add(layout);
        loginButton.addClickListener(event -> authenticate(errorMessage, usernameField.getValue(), passwordField.getValue()));
    }   

    // Check given credentials
    private void authenticate(Span error, String username, String password) {
        boolean correctUsername = false;
        boolean correctPassword = false;

        if (username == "" || password == "") {
            error.setText("Fill in all fields!");
        } else {
            correctUsername = checkUsername(username);
            if (!correctUsername) {
                error.setText("Username does not exist");
            } else {
                correctPassword = checkPassword(username, password);
                if (!correctPassword) {
                    error.setText("Password is not correct");
                } else {
                    // To do: CREATE SESSION HERE **** 
                    UI.getCurrent().navigate("");
                }
            }
        }
    }

    // Connect to database and check is username exists
    private boolean checkUsername(String username) {
        boolean usernameExists = false;
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
            String sql = "SELECT * FROM customers WHERE username='"+username+"'";
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery(sql);
            if (resultset.next()) {
                usernameExists = true;
            } else {
                usernameExists = false;
            }
            connection.close();
        } catch(SQLException e) {
            System.out.println("Error in connecting postgres");
            e.printStackTrace();
        }
        return usernameExists;
    }

    // Connect to database and check if combination of the given username and password is correct
    private boolean checkPassword(String username, String password) {
        boolean correctPassword = false;
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
            String sql = "SELECT * FROM customers WHERE username='"+username+"' AND password='"+password+"'";
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery(sql);
            if (resultset.next()) {
                correctPassword = true;
            } else {
                correctPassword = false;
            }
            connection.close();
        } catch(SQLException e) {
            System.out.println("Error in connecting postgres");
            e.printStackTrace();
        }
        return correctPassword;
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
