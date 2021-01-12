package com.example;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.UI;
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



@Route("finishedBookings")
public class finishedBookings extends VerticalLayout{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String customerUsername;
    private String beginDate;
    private String endDate;
    private String DeliveryDate;
    private String Amount;
    private String carLicenseplate;

   
    public List<finishedBookings> bookingList = new ArrayList<>();

    public finishedBookings(){
        if (SessionAttributes.getAdminLogin() == "true" || SessionAttributes.getAdminLogin() != null) {
            setDefaultHorizontalComponentAlignment(Alignment.CENTER);
            setSizeFull();
            addHeader();
            getBookings();
            H2 title = new H2("Completed booking list");
        
            Grid<finishedBookings> grid = new Grid<>(finishedBookings.class);
            grid.setItems(bookingList);
            grid.removeAllColumns();
            grid.addColumn(finishedBookings::getUsername).setHeader("Customer Username");
            grid.addColumn(finishedBookings::getCarLicense).setHeader("License plate");
            grid.addColumn(finishedBookings::getAmount).setHeader("Amount");
            grid.addColumn(finishedBookings::getBeginDate).setHeader("Begin date");
            grid.addColumn(finishedBookings::getEndDate).setHeader("End date");
            grid.addColumn(finishedBookings::getDeliveryDate).setHeader("Delivery date");

            add(title,grid);
        } else {
            UI.getCurrent().navigate("");
            UI.getCurrent().getPage().reload();
        }
    }

    public finishedBookings(String usn, String begindate, String enddate,String deliverydate, String amount, String license){
        super();
        customerUsername = usn;
        beginDate = begindate;
        endDate = enddate;
        DeliveryDate= deliverydate;
        Amount = amount;
        carLicenseplate = license;
        
    }
    public void getBookings(){
        try{

            Connection conn = DriverManager.getConnection(Application.jdbcURL,Application.username,Application.password);
            PreparedStatement checkUsnEmail = conn.prepareStatement("SELECT * FROM \"reservations\" WHERE deliverydate is not null ORDER BY username ASC");
            ResultSet rs = checkUsnEmail.executeQuery();
            while(rs.next()){
                String usn = rs.getString(4);
                String license = rs.getString(3);
                String amount = rs.getString(10);
                String begindate= rs.getString(6);
                String enddate= rs.getString(8);
                String deliverydate= rs.getString(17);
                bookingList.add(new finishedBookings(usn,begindate,enddate,deliverydate,amount,license));
            }
            conn.close();
        }
        catch(Exception e){
            System.out.println("GetBookings:" +e);
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
        return DeliveryDate;
    }
    public String getAmount(){
        return Amount;
    }
    public String getCarLicense(){
        return carLicenseplate;
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
            MenuItem menuItemLogout = subMenuLogin.addItem("Logout");
            menuItemLogout.addClickListener(e -> SessionAttributes.logout());
            menuItemLogout.addClickListener(e -> menuItemLogout.getUI().ifPresent(ui -> ui.navigate("Login")));
        }

        // If neither admin or employee or another user is logged in, show employee login menu.
        if((SessionAttributes.getEmployeeLogin()=="false" || SessionAttributes.getEmployeeLogin() == null)&& (SessionAttributes.getAdminLogin()=="false" || SessionAttributes.getAdminLogin()== null) && (SessionAttributes.getLoggedIn()=="false") || SessionAttributes.getLoggedIn()==null){
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
