package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;

@Route("Reviews")
@CssImport("./styles/styles.css")
public class Reviews extends VerticalLayout {

    private static final long serialVersionUID = 1L;
    
    private String[][] arrayReviews;
    static String jdbcURL = "jdbc:postgresql://localhost:5432/Test_Project";
    static String jdbcusername = "postgres";
    static String jdbcpassword = "asd";
    public boolean loggedin= false;

    public Reviews() {
        H2 title = new H2("Click on a star rating to read");
        setSizeFull();
        addClassName("reviews");
        addHeader();
        if(SessionAttributes.getLoggedIn()== null || SessionAttributes.getLoggedIn() =="false"){
            H2 text = new H2("Please log in to write a review");
            add(text);
        }
        else{
            askReview();
        }
        retrieveReviews();
        add(title);
        viewReviews();
    }

    // Form for writing a review
    public void askReview() {

        FormLayout reviewLayout = new FormLayout();
        reviewLayout.addClassName("reviewLayout");
        H2 formHeader = new H2("Write a review here"); 
        reviewLayout.add(formHeader);
        reviewLayout.setMaxWidth("30em");

        TextArea reviewField = new TextArea("Give us a review!");
        reviewField.setPlaceholder("Write here ...");
        reviewField.setMaxWidth("30em");

        TextArea star = new TextArea();
        star.setLabel("Star rating 1-5");
        star.setMaxWidth("12em");
        

        Button sendReview = new Button("Send review");
        sendReview.setMaxWidth("20em");
        
        
        sendReview.addClickListener(click->{
            if(!((Integer.parseInt(star.getValue())>=1)&(Integer.parseInt(star.getValue())<=5))){
                Notification.show("Please use a number 1-5 for star rating", 10000, Position.TOP_CENTER);
            }
            else if(checkReview(SessionAttributes.getLoggedUser())){
                Notification.show("You are only aloud to place one review", 10000, Position.TOP_CENTER);
            }
            
            else if(checkUser(SessionAttributes.getLoggedUser())){
                insertReviewTable(SessionAttributes.getLoggedUser().toString(), reviewField.getValue(),star.getValue());
                Notification.show("Review sent!", 6000, Position.TOP_CENTER);
            }
            else{
                Notification.show("User credentials are wrong, please try again", 10000, Position.TOP_CENTER);
            }

        });

        reviewLayout.add(reviewField,star ,sendReview);
        reviewLayout.setColspan(reviewField, 2);
        add(reviewLayout);
        
    }

    // Checks if the user already has placed a review

    public boolean checkReview(String usn){
        boolean userReview = false;
        try{
            Connection conn = DriverManager.getConnection(jdbcURL, jdbcusername, jdbcpassword);
            String query = "select * from reviewtable where username='"+usn+"'";
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            if(rs.next()){
                userReview = true;
            }
            else{
                userReview = false;
            }
            conn.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
        return userReview;
    }

    // Function that'll check if the username and password are valid in DB
    public boolean checkUser(String username){
        boolean userCred= false;

        try{
            Connection conn = DriverManager.getConnection(jdbcURL, jdbcusername, jdbcpassword);
            String query = "select * from customers where username='"+username+"'";
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            if(rs.next()){
                userCred = true;
            }
            else{
                userCred= false;
            }
            conn.close();
        }

        catch(Exception e){
            System.out.println(e);
        }
        return userCred;
    }

    
    // Function below inserts customer review to the review table.
    public  void insertReviewTable(String usn,String review, String star){
        if(Integer.parseInt(star)==1){
            star = "*";
        }
        else if (Integer.parseInt(star)==2){
            star = "* *";
        }
        else if(Integer.parseInt(star)==3){
            star = "* * *";
        }
        else if(Integer.parseInt(star)==4){
            star = "* * * *";
        }
        else if(Integer.parseInt(star)==5){
            star= "* * * * *";
        }

        try{
            Connection conn = DriverManager.getConnection(jdbcURL, jdbcusername, jdbcpassword);
            PreparedStatement insert = conn.prepareStatement("INSERT INTO reviewtable (username, review, star) VALUES ('"+usn+"', '"+review +"','"+ star+"')");
            insert.executeUpdate();
            System.out.println("Inserted");
        }
        catch(Exception e){
            System.out.println(e);
            
        }
    }
    // This function will load the reviews in a accordion for view
    public void viewReviews(){
        
        Accordion accordion = new Accordion();
        
        for(int i=0 ; i<arrayReviews.length;i++){
            accordion.add(arrayReviews[i][2], new Span(arrayReviews[i][0]+": "+arrayReviews[i][1])).addThemeVariants(DetailsVariant.FILLED);
        }
        
        add(accordion);
       
    }
    
    public void retrieveReviews(){

        try {
            int index=0;
            Connection connection = DriverManager.getConnection(jdbcURL, jdbcusername, jdbcpassword);
            Statement statement = connection.createStatement();
            
            // Amount of rows
            String query = "select count(*) from reviewtable";
            ResultSet result = statement.executeQuery(query);
            result.next();
            int count = result.getInt(1);
            arrayReviews = new String[count][3];
            
            // Data to array
            ResultSet rs = statement.executeQuery("select * from reviewtable");
            while(rs.next()){
                String usn = rs.getString("username");
                String review = rs.getString("review");
                String star = rs.getString("star");
                arrayReviews[index][0]= usn;
                arrayReviews[index][1]= review;
                arrayReviews[index][2]= star;
                index++;
            } 
            connection.close(); 
        }

        catch(Exception e){
            System.out.println(e);
        }
    }

    // Header, menu bar
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
        MenuItem menuItemRentInformation = subMenuRent.addItem("Information");
        menuItemRentInformation.addClickListener(e -> menuItemRentInformation.getUI().ifPresent(ui -> ui.navigate("Information")));
        
        SubMenu subMenuLogin = menuItemLogin.getSubMenu();
        MenuItem menuItemRegister = subMenuLogin.addItem("Register");
        menuItemRegister.addClickListener(e -> menuItemRegister.getUI().ifPresent(ui -> ui.navigate("Register")));

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
