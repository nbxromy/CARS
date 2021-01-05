package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.*;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.joda.time.Days;


@Route("Employee")
@CssImport("./styles/styles.css")
public class Employee extends VerticalLayout{

    private static final long serialVersionUID = 1L;
    public static boolean isEmployee;
    public UI ui;
    public Employee(){
        ui = this.getUI().isPresent() ? this.getUI().get() : UI.getCurrent();
        isEmployee= true;
        addHeader();
        employeeForm();
        
    }



    public void employeeForm(){
        FormLayout form = new FormLayout();
        form.setMaxWidth("22em");
        form.addClassName("empForm");
        H2 title= new H2("Check car and fill in the form to finish the order.");
        TextField licenseplate = new TextField("Car licenseplate");
        TextField tank = new TextField("If tank is full insert 'yes' if not 'no'");
        TextField carClean = new TextField("If car is clean insert 'yes' if not 'no'");
        TextField carDamages = new TextField("Visible damages to the car insert 'yes' if not 'no'");
        TextField mileage = new TextField("Insert new mileage like '555887086'");
        Button finish = new Button("Finish");
        

        finish.addClickListener(click-> {
            //if
            if(checkForumFields(licenseplate.getValue(), tank.getValue(), carClean.getValue(), carDamages.getValue(), mileage.getValue())){
                String price =calculatePrice(tank.getValue(), carClean.getValue(), carDamages.getValue(), licenseplate.getValue());
                if(getBookingAndUpdate(licenseplate.getValue(),Integer.parseInt(mileage.getValue()))){
                    ui.access(()->Notification.show(price, 10000, Position.TOP_CENTER));
                }
                else{
                    Notification.show("Booking not found, check customer username and booking delivery date", 10000, Position.TOP_CENTER);
                }
                
            }
        });
        
        form.add(title,licenseplate, tank, carClean, carDamages, mileage, finish);
        add(form);
    }

    // Checks all forum fields
    public boolean checkForumFields(String license, String carTank, String carClean, String carDamages, String newMileage){
        
        boolean checkMileage=false;
        int inputmileage=0;

        if(license.equals("") || carTank.equals("")||carClean.equals("")||carDamages.equals("")||newMileage.equals("")){
            Notification.show("Please fill in the form", 10000, Position.TOP_CENTER);
            return false;
        }
        else if(!((carTank.equals("no")||carTank.equals("yes"))&& (carClean.equals("yes")||carClean.equals("no"))&&(carDamages.equals("yes")||carDamages.equals("no")))){
            Notification.show("Form inputs are wrong, please fill in 'yes' or 'no'", 10000, Position.TOP_CENTER);
            return false;
        }
        else{

            try{
                inputmileage= Integer.parseInt(newMileage);
                checkMileage=true;
            }
            catch(Exception e){
                System.out.println(e);
                checkMileage=false;
            }

            
            if(checkMileage==false){
                Notification.show("Mileage input is wrong", 8000, Position.TOP_CENTER);
                return false;
            }
            return true;
        }
    }

    
    // Gets the needed booking data to move the booking to (finished) bookings.
    // Also updates the car table with the new mileage 
    public boolean getBookingAndUpdate(String license, int newMileage){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");  
        Date currentdate = new Date();  
    
        try{
            Connection conn = DriverManager.getConnection(Application.jdbcURL,Application.username,Application.password);
            PreparedStatement getbooking = conn.prepareStatement("SELECT * FROM \"finishedbookings\" WHERE carlicenseplate='"+license+"' AND deliverydate is null");
            ResultSet rs = getbooking.executeQuery();
            
            if(rs.next()){
                PreparedStatement updateDeliverydate = conn.prepareStatement("UPDATE finishedbookings set deliverydate='"+formatter.format(currentdate)+"'");
                updateDeliverydate.executeUpdate();
               
                PreparedStatement updateCar = conn.prepareStatement("UPDATE \"Cars\" SET mileage="+newMileage +" WHERE \"licensePlate\"='"+license+"'");
                updateCar.executeUpdate();
                conn.close();
            }
            else{
                return false;
            }       
        }
        catch(Exception e){
            System.out.println("Failed: "+e);
            return false;
        }
        return true;
    }

    // Calculate if there are extra costs (tank full, car clean, delivering later than booking delivery date)
    public String calculatePrice(String tank, String clean, String damages, String license){
        int extraCosts = 0;
        String total= "";
        LocalDate deliveryDate = new LocalDate();

        if(tank.equals("no")){
            extraCosts += 50;
            
        }
        
        else if(clean.equals("no")){
            extraCosts += 30;
        }
  
        try{
            Connection conn = DriverManager.getConnection(Application.jdbcURL,Application.username,Application.password);
            PreparedStatement getTotalamount = conn.prepareStatement("SELECT * FROM \"finishedbookings\" WHERE carlicenseplate='"+license+"' AND deliverydate is null");
            ResultSet rs = getTotalamount.executeQuery();
            if(rs.next()){
                total = rs.getString(3);
                LocalDate endDate = LocalDate.parse(rs.getString(6));
                int days = Days.daysBetween(endDate,deliveryDate).getDays();
                
                if(days>0){
                    extraCosts = extraCosts+ (days*25);
                }
            }
            conn.close();
        }
        catch(Exception e){
            System.out.println("Failed to calculate price: "+e);
            Notification.show("Failed to calculate price",6000,Position.TOP_CENTER);
        }
    
        return ("Extra costs: "+extraCosts+"| DB amount:"+total);
        
    }



    public static boolean getEmployee(){
        return isEmployee;
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
        MenuItem menuItemLogin = menuBar.addItem("Login");
        menuItemLogin.addComponentAsFirst(new Icon(VaadinIcon.USER));
        menuItemLogin.addClickListener(e -> menuItemLogin.getUI().ifPresent(ui -> ui.navigate("Login")));
        MenuItem menuItemReview = menuBar.addItem("Reviews");
        menuItemReview.addClickListener(e -> menuItemReview.getUI().ifPresent(ui -> ui.navigate("Reviews")));
       
        if(Employee.getEmployee()){
            MenuItem menuItemReservation = menuBar.addItem("Employee");
            menuItemReservation.addClickListener(e -> menuItemReservation.getUI().ifPresent(ui -> ui.navigate("Employee")));
        }
         

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
