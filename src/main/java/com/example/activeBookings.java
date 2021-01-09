package com.example;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.router.Route;


@Route("activeBookings")
public class activeBookings extends VerticalLayout{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String customerUsername;
    private String beginDate;
    private String endDate;
    private String deliveryDate;
    private String Amount;
    private String carLicenseplate;
    
   
    public List<activeBookings> bookingList = new ArrayList<>();

    public activeBookings(){
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setSizeFull();
        addHeader();
        getBookings();
        H2 title = new H2("Active booking list");
    

        Grid<activeBookings> grid = new Grid<>(activeBookings.class);
        grid.setItems(bookingList);
        grid.removeAllColumns();
        grid.addColumn(activeBookings::getUsername).setHeader("Customer Username");
        grid.addColumn(activeBookings::getCarLicense).setHeader("License plate");
        grid.addColumn(activeBookings::getAmount).setHeader("Amount");
        grid.addColumn(activeBookings::getBeginDate).setHeader("Begin date");
        grid.addColumn(activeBookings::getEndDate).setHeader("End date");
        grid.addColumn(activeBookings::getDeliveryDate).setHeader("Delivery date");

        add(title,grid);
    }
    // Ervoor zorgen dat de row hoogte groter wordt, bij lange tekst (wrap) of css
    //  http://www.w3schools.com/cssref/css3_pr_word-wrap.asp https://vaadin.com/forum/thread/15297878/15302575
    public activeBookings(String usn, String begindate, String enddate,String amount, String license){
        super();
        customerUsername = usn;
        beginDate = begindate;
        endDate = enddate;
       
        Amount = amount;
        carLicenseplate = license;
        
    }
    public void getBookings(){
        try{

            Connection conn = DriverManager.getConnection(Application.jdbcURL,Application.username,Application.password);
            PreparedStatement checkUsnEmail = conn.prepareStatement("SELECT * FROM \"reservations\" WHERE deliverydate is null ORDER BY customerusername ASC");
            ResultSet rs = checkUsnEmail.executeQuery();
            while(rs.next()){
                String usn = rs.getString(4);
                String license = rs.getString(3);
                String amount = rs.getString(10);
                String begindate= rs.getString(6);
                String enddate= rs.getString(8);
                
                bookingList.add(new activeBookings(usn,begindate,enddate,amount,license));
            }
            conn.close();
        }
        catch(Exception e){
            
        }
    }

    
    public String getUsername(){
        return customerUsername;
    }
    public String getBeginDate(){
        return beginDate;
    }
    public String getEndDate(){
        return endDate;
    }
    public String getDeliveryDate(){
        return deliveryDate;
    }
    public String getAmount(){
        return Amount;
    }
    public String getCarLicense(){
        return carLicenseplate;
    }
  

    // @Override
    // public int hashCode(){
    //     return customerUsername;
    // }
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
       
        if(Admin.getAdmin()){
            MenuItem menuItemReservation = menuBar.addItem("Admin");
            menuItemReservation.addClickListener(e -> menuItemReservation.getUI().ifPresent(ui -> ui.navigate("Admin")));
        }
        
        MenuItem menuItemReservation = menuBar.addItem("Employee");
        menuItemReservation.addClickListener(e -> menuItemReservation.getUI().ifPresent(ui -> ui.navigate("Employee")));
        
         

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
