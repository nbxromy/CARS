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
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    // Database credentials
    private String jdbcURL = "jdbc:postgresql://localhost:5432/ProjectC";
    private String jdbcUsername = "postgres";
    private String jdbcPassword = "asdf";

    public Login() {
        if (SessionAttributes.getLoggedIn() == null || SessionAttributes.getLoggedIn() == "false") {
            setDefaultHorizontalComponentAlignment(Alignment.CENTER);
            setSizeFull();
            addClassName("login");
            addHeader();

            // Front-end login form 
            H2 pageName = new H2("Log in");
            pageName.getElement().getThemeList();

            TextField usernameField = new TextField("User name");
            usernameField.setRequired(true);
            PasswordField passwordField = new PasswordField("Password");
            passwordField.setRequired(true);
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
            loginButton.addClickListener(event -> authenticate(errorMessage, usernameField, passwordField));
        } else {
            UI.getCurrent().navigate("");
        }
    }

    // Check given credentials
    private void authenticate(Span error, TextField usernameField, PasswordField passwordField) {
        boolean correctUsername = false;
        boolean correctPassword = false;

        String username = usernameField.getValue();
        String password = passwordField.getValue();

        if (usernameField.isEmpty() || passwordField.isEmpty()) {
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
                    SessionAttributes.login(username);
                    UI.getCurrent().navigate("Profile");
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
            MenuItem menuItemRegister = subMenuLogin.addItem("Logout");
            menuItemRegister.addClickListener(e -> SessionAttributes.logout());
            menuItemRegister.addClickListener(e -> menuItemRegister.getUI().ifPresent(ui -> ui.navigate("Login")));
        }
        add(header, menuBar);
    }
}

