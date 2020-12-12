package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        TextField customerUsername = new TextField("Customer username");
        TextField Deliverydate = new TextField("Insert current date like YYYY-MM-DD");
        TextField bookingendDate = new TextField("Insert the booking delivery date YYYY-MM-DD");
        TextField tank = new TextField("If tank is full insert 'yes' if not 'no'");
        TextField carClean = new TextField("If car is clean insert 'yes' if not 'no'");
        TextField carDamages = new TextField("Visible damages to the car insert 'yes' if not 'no'");
        TextField mileage = new TextField("Insert new mileage like '555887086'");
        Button finish = new Button("Finish");
        

        finish.addClickListener(click-> {
            //if
            if(checkForumFields(customerUsername.getValue(), Deliverydate.getValue(), bookingendDate.getValue(), tank.getValue(), carClean.getValue(), carDamages.getValue(), mileage.getValue())){
                String price =calculatePrice(tank.getValue(), carClean.getValue(), carDamages.getValue(), customerUsername.getValue(), Deliverydate.getValue(),bookingendDate.getValue());
                getBooking(customerUsername.getValue(), Deliverydate.getValue(), bookingendDate.getValue(),Integer.parseInt(mileage.getValue()));
                removeBooking(customerUsername.getValue(),Deliverydate.getValue());
                ui.access(()->Notification.show(price, 10000, Position.TOP_CENTER));
            }
        });
        
        form.add(title, customerUsername, Deliverydate, bookingendDate, tank, carClean, carDamages, mileage, finish);
        add(form);
    }

    // Checks all forum fields
    public boolean checkForumFields(String usn, String deliveryDate, String bookingEndDate, String carTank, String carClean, String carDamages, String newMileage){
        boolean checkDate= false;
        boolean checkMileage=false;
        int inputmileage=0;

        if(usn.equals("") || deliveryDate.equals("")||bookingEndDate.equals("")||carTank.equals("")||carClean.equals("")||carDamages.equals("")||newMileage.equals("")){
            Notification.show("Please fill in the form", 10000, Position.TOP_CENTER);
            return false;
        }
        else if(!((carTank.equals("no")||carTank.equals("yes"))&& (carClean.equals("yes")||carClean.equals("no"))&&(carDamages.equals("yes")||carDamages.equals("no")))){
            Notification.show("Form inputs are wrong, please fill in 'yes' or 'no'", 10000, Position.TOP_CENTER);
            return false;
        }
        else{

            // Check (parse) if dates and mileage are filled in correctly
            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
            dateformat.setLenient(false);
            try{
                dateformat.parse(deliveryDate);
                dateformat.parse(bookingEndDate);
                checkDate=true;
            }
            catch(ParseException e){
                System.out.println(e);
                checkDate=false;
            }

            try{
                inputmileage= Integer.parseInt(newMileage);
                checkMileage=true;
            }
            catch(Exception e){
                System.out.println(e);
                checkMileage=false;
            }

            if(checkDate==false){
                Notification.show("Date input is wrong", 8000, Position.TOP_CENTER);
                return false;
            }
            else if(checkMileage==false){
                Notification.show("Mileage input is wrong", 8000, Position.TOP_CENTER);
                return false;
            }
            return true;
        }
    }
    // Checks if the input mileage is <= current mileage (Database)
    // public boolean mileageDB(String usn,String bookingEndDate, String inputmileage){
    //     int inputMileage= Integer.parseInt(inputmileage);
    //     int dbMileage=0;
    //     String license="";
    //     try{
    //         Connection conn = DriverManager.getConnection(Application.jdbcURL,Application.username,Application.password);
    //         PreparedStatement DBcheckMileage= conn.prepareStatement("SELECT * FROM \"Bookings\" WHERE  \"customerUsername\"='"+usn+"' AND enddate='"+bookingEndDate+"'");
    //         ResultSet rs = DBcheckMileage.executeQuery();
    //         if(rs.next()){

    //             license= rs.getString(1);
    //             conn.close();
    //             returnMileage(license, inputmileage);
    //             // PreparedStatement getMileage = conn.prepareStatement("SELECT * FROM \"Cars\"WHERE  \"licensePlate\"='"+ license+"'");
    //             // ResultSet rsMileage = getMileage.executeQuery();
    //             // if(rsMileage.next()){
    //             //     dbMileage = rsMileage.getInt(3);
                    
    //             //     if(inputMileage<=dbMileage){
    //             //         return false;
    //             //     }
    //             //     else{
    //             //         return true;
    //             //     }
    //             // }
                
    //         }
    //         System.out.println("DBMILEAGE="+dbMileage);
    //         conn.close();
    //     }
    //     catch(Exception e){
    //         return false;
    //     }

        
        
    //     return true;
    // }
    // public void returnMileage(String licenseplate, String stringmileage){
    //     int inputmileage = Integer.parseInt(stringmileage);
    //     int test =0;
    //     try{
    //         Connection conn = DriverManager.getConnection(Application.jdbcURL,Application.username,Application.password);
    //         PreparedStatement DBcheckMileage= conn.prepareStatement("SELECT * FROM \"Cars\" WHERE  \"licensePlate\"='"+licenseplate+"'");
    //         ResultSet rs = DBcheckMileage.executeQuery();
            
    //         if(rs.next()){
    //             test = rs.getInt(3);
    //         }
    //         else if(inputmileage<=test){
    //             System.out.println("TESTMILEAGE: "+test);
    //             Notification.show("Testmileage: input<db", 8000, Position.TOP_CENTER);
    //         }
    //     }
    //     catch(Exception e){

    //     }
    // }
    
    // Gets the needed booking data to move the booking to (finished) bookings.
    // Also updates the car table with the new mileage 
    public void getBooking(String customerUsername, String deliveryDate, String bookingDate, int newMileage){
        String license="";
    
        try{
            Connection conn = DriverManager.getConnection(Application.jdbcURL,Application.username,Application.password);
            PreparedStatement checkUsnEmail = conn.prepareStatement("SELECT * FROM \"bookings\" WHERE customerusername='"+customerUsername+"' AND enddate='"+bookingDate+"'");
            ResultSet rs = checkUsnEmail.executeQuery();
            
            if(rs.next()){
                license = rs.getString(1);
                var customerusername =rs.getString(2);
                var totalamount = rs.getString(5);
                var bookingdate = rs.getString(6);
                var begindate = rs.getString(7);
                var enddate = rs.getString(8);    

                transferBookingtoFinished(customerusername, totalamount, bookingdate, begindate, enddate, license);

                PreparedStatement updateCar = conn.prepareStatement("UPDATE \"Cars\" SET mileage="+newMileage +" WHERE \"licensePlate\"='"+license+"'");
                updateCar.executeUpdate();
                conn.close();
            }       
        }
        catch(Exception e){
            System.out.println("Failed: "+e);
        }
    }

    // Calculate if there are extra costs (tank full, car clean, delivering later than booking delivery date)
    public String calculatePrice(String tank, String clean, String damages, String usn, String deliverydate, String enddate){
        int extraCosts = 0;
        String total= "";
        LocalDate deliveryDate = LocalDate.parse(deliverydate);

        if(tank.equals("no")){
            extraCosts += 50;
            
        }
        
        else if(clean.equals("no")){
            extraCosts += 30;
        }
  
        try{
            Connection conn = DriverManager.getConnection(Application.jdbcURL,Application.username,Application.password);
            PreparedStatement getTotalamount = conn.prepareStatement("SELECT * FROM \"bookings\" WHERE customerusername='"+usn+"' AND enddate='"+enddate+"'");
            ResultSet rs = getTotalamount.executeQuery();
            if(rs.next()){
                total = rs.getString(5);
                
                
                LocalDate endDate = LocalDate.parse(rs.getString(8));
                int days = Days.daysBetween(endDate,deliveryDate).getDays();
                
                if(days>0){
                    extraCosts = extraCosts+ (days*25);
                }
            }
            conn.close();
        }
        catch(Exception e){
            Notification.show("Failed to calculate price",6000,Position.TOP_CENTER);
        }
    
        return ("Extra costs: "+extraCosts+"| DB amount:"+total);
        
    }

    // Updates the mileage and status from cars, after booking is finished (status column needs to be added and updated)
    public void updateCarMileage(String licenseplate, int newMileage){
        
        try{
            Connection conn = DriverManager.getConnection(Application.jdbcURL,Application.username,Application.password);
            PreparedStatement updateCar = conn.prepareStatement("UPDATE \"Cars\" SET mileage="+newMileage +" WHERE \"licensePlate\"='"+licenseplate+"'");
            updateCar.executeUpdate();
            conn.close();

        }
        catch(Exception e){
        }
    }

    // Inserts the finished booking to the list of finished bookings.
    public void transferBookingtoFinished(String usn, String amount, String bookingdate, String begindate, String enddate, String license){
        try{
            Connection conn = DriverManager.getConnection(Application.jdbcURL,Application.username,Application.password);
            PreparedStatement insertbooking = conn.prepareStatement("INSERT INTO finishedbookings VALUES('"+usn +"','"+license+"','" +amount+"','"+bookingdate+"','"+begindate+"','"+enddate+"')" );
            insertbooking.executeUpdate();
            conn.close();
        
        }
        catch(Exception e){

        }

    }

    // Removes the finished booking from the list of active bookings.
    public void removeBooking(String usn, String enddate){
        try{
            Connection conn = DriverManager.getConnection(Application.jdbcURL,Application.username,Application.password);
            PreparedStatement checkUsnEmail = conn.prepareStatement("DELETE FROM \"bookings\" WHERE customerusername='"+usn+"' AND enddate='"+enddate+"'");
            checkUsnEmail.executeQuery();
            conn.close();
        }
        catch(Exception e){
        }
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
