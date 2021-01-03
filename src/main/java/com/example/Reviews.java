package com.example;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;



import com.example.Application;
import com.fasterxml.jackson.core.exc.StreamReadException;
 

@Route("Reviews")
@CssImport("./styles/styles.css")
public class Reviews extends VerticalLayout {

    /**
     *
     */ 
    private static final long serialVersionUID = 1L;
    private String[][] arrayReviews;
    static String jdbcURL = "jdbc:postgresql://localhost:5432/Test_Project";
    static String jdbcusername = "postgres";
    static String jdbcpassword = "asd";
    public boolean loggedin= false;

    public Reviews() {
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setSizeFull();
        addClassName("reviews");
        addHeader();
        retrieveReviews();
        H2 pageName = new H2("You can write a review on the left and read reviews on the right");
        pageName.getElement().getThemeList();
        add(pageName);
        askReview();
        viewReviews();
        
    }
 
    // Form for writing a review
    public void askReview() {

        // Todo: If user is logged in (need credentials for DB table) ->
        //       different form, without usn/pass

        FormLayout reviewLayout = new FormLayout();
        reviewLayout.addClassName("reviewLayout");
        H2 formHeader = new H2("Write a review here"); 
        reviewLayout.add(formHeader);
        reviewLayout.setMaxWidth("30em");

        TextField usernameField = new TextField();
        usernameField.setLabel("Username");
        usernameField.setMaxWidth("16em");

        PasswordField passwordField = new PasswordField();
        passwordField.setLabel("Password");
        passwordField.setMaxWidth("16em");

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
                Notification.show("Please use a number 1-5 for star rating", 6000, Position.TOP_CENTER);
            }
            else if(checkReview(usernameField.getValue(), passwordField.getValue())){
                Notification.show("You are only aloud to place one review", 6000, Position.TOP_CENTER);
            }
            
            else if(checkUser(usernameField.getValue(), passwordField.getValue())){
                sendReview(usernameField.getValue(), passwordField.getValue(), reviewField.getValue(),star.getValue());
                Notification.show("Review sent!", 6000, Position.TOP_CENTER);
            }
            else{
                Notification.show("User credentials are wrong, please try again", 6000, Position.TOP_CENTER);
                
            }

        });

        reviewLayout.add(usernameField, passwordField, reviewField,star ,sendReview);
        reviewLayout.setColspan(reviewField, 2);
        add(reviewLayout);
        
    }

    // Checks if the user already has placed a review

    public boolean checkReview(String usn, String passw){
        boolean userReview = false;
        try{
            Connection conn = DriverManager.getConnection(jdbcURL, jdbcusername, jdbcpassword);
            String query = "select * from reviewtable where username='"+usn+"' and password='"+passw+"'";
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
    public boolean checkUser(String username, String password){
        boolean userCred= false;

        try{
            Connection conn = DriverManager.getConnection(jdbcURL, jdbcusername, jdbcpassword);
            String query = "select * from customer where username='"+username+"' and password='"+password+"'";
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

    // Function that'll send the review to the DB when the button is pressed.
    public void sendReview(String usn, String password, String review, String star) {

        try {
            Application.insertReviewTable(usn, password, review, star);
        } 
        catch (Exception e) {
           System.out.println(e);
        }
        
    }
    
    // This function will load the reviews in a accordion for view
    public void viewReviews(){
        
        Accordion accordion = new Accordion();
        
        for(int i=0 ; i<arrayReviews.length;i++){
            accordion.add(arrayReviews[i][0]+"  Stars:"+arrayReviews[i][2], new Span(arrayReviews[i][1])).addThemeVariants(DetailsVariant.FILLED);
        }
        
        add(accordion);
       
    }

    // This function will retrieve the amount of rows in the DB reviewtable
    // and put the data inside arrayReviews.
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
        add(header, menuBar);
    }    
}
