package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
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

@Route("employeeLogin")
public class employeeLogin extends VerticalLayout{
 
    private static final long serialVersionUID = 1L;
    String jdbcURL = "jdbc:postgresql://localhost:5432/Test_Project";
    String jdbcusername = "postgres";
    String jdbcpassword = "asd";
    public boolean adminLoggedin;
    public boolean employeeLoggedin;
    public int EmployeeOrAdmin;

    public employeeLogin(){
        if (SessionAttributes.getLoggedIn() == null || SessionAttributes.getLoggedIn() == "false") {
            setDefaultHorizontalComponentAlignment(Alignment.CENTER);
            setSizeFull();
            addClassName("employeelogin");
            addHeader();

            // Front-end login form 
            H2 pageName = new H2("Log in for employees!");
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
            loginButton.addClickListener(event -> 
            {
                if(checkFields(errorMessage,usernameField,passwordField)){
                    if(checkCredentials(usernameField, passwordField)){
                        checkWorker(usernameField, passwordField);
                    }
                    else{
                        errorMessage.setText("Username or password incorrect");
                    }
                    
                }
            });
        } else {
            UI.getCurrent().navigate("");
            UI.getCurrent().getPage().reload();
        }
    }
    public void checkWorker(TextField usernameField, PasswordField passwordField){
        try{
            Connection conn = DriverManager.getConnection(jdbcURL,jdbcusername,jdbcpassword);
            String query = "select * from workers where username='"+usernameField.getValue()+"' and password='"+passwordField.getValue()+"'";
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            if(rs.next()){
                EmployeeOrAdmin = rs.getInt(3);
                if (EmployeeOrAdmin==0){
                    Admin.changeIsAdmin(true);
                    SessionAttributes.adminLogin(usernameField.getValue());
                    UI.getCurrent().navigate("Admin");
                    
                }
                if(EmployeeOrAdmin==1){
                    Employee.changeIsEmployee(true);
                    SessionAttributes.employeeLogin(usernameField.getValue());
                    UI.getCurrent().navigate("Employee");
                }
            }
        }
        catch(Exception e){
            System.out.println("Conn failed after check:"+e);
        }
    }
    public boolean checkFields(Span error, TextField usernamefield, PasswordField passwordfield){
        
        boolean FieldsFilledIn = false;
        if(usernamefield.isEmpty() || passwordfield.isEmpty()){
            error.setText("Fill in all fields");
            return FieldsFilledIn;
        }
        else{
            FieldsFilledIn = true;
            return FieldsFilledIn;
        }
    }

    public boolean checkCredentials(TextField usernamefield, PasswordField passwordfield){
        String username = usernamefield.getValue();
        String password = passwordfield.getValue();
        System.out.println(username+password);
        boolean correctCredentials= false;
        try{
            Connection conn = DriverManager.getConnection(jdbcURL, jdbcusername, jdbcpassword);
            String query = "select * from workers where username='"+username+"' and password='"+password+"'";
            
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            if(rs.next()){
                correctCredentials = true;
            }
            else{
                correctCredentials = false;
            }
            conn.close();
        }
        catch(Exception e){
            System.out.println("CheckCredentials: "+e);
            return correctCredentials;
        }
        return correctCredentials;
    }
    public int getEmployeeOrAdmin(){
        return EmployeeOrAdmin;
    }
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
        // If neither admin or employee is logged in, show employee login menu.
        if((SessionAttributes.getEmployeeLogin()=="false" || SessionAttributes.getEmployeeLogin() == null)&& (SessionAttributes.getAdminLogin()=="false" || SessionAttributes.getAdminLogin()== null)){
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

