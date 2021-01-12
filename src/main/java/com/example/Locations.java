
package com.example;

import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dependency.CssImport;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;




@Route(value="Locations")
@PageTitle("Locations")
@CssImport("./styles/styles.css")
public class Locations extends VerticalLayout {
    
    private Grid<Locations> grid;

    @Id
    String  countryCode;
    @Id
    String  City;
    @Id
    String  Address;

    @Id
    private Button search;

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String jdbcURL = "jdbc:postgresql://localhost:5432/Test_Project";
    private String jdbcUsername = "postgres";
    private String jdbcPassword = "asd";
    public List<Locations> locationlist = new ArrayList<>();

    public Locations() {
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setSizeFull();
        //addClassName("cv, grid");
        addHeader();
        getLocations();
        
        // Import data out of postgreSQL
        H2 pageName = new H2("Add link here .. (to database)");
        add(pageName);

        // Table (grid) for the 3 values
        grid = new Grid<>(Locations.class, false);
        grid.setItems(locationlist);
        grid.removeAllColumns();
        grid.addColumn(Locations::getCountryCode).setHeader("Countrycode");
        grid.addColumn(Locations::getCity).setHeader("City");
        grid.addColumn(Locations::getAddress).setHeader("Address");
        grid.setHeightFull();
        add(grid);

    }

    public Locations(String countrycode, String city, String address){
        super();
        countryCode = countrycode;
        City = city;
        Address = address;
    }
    public void getLocations(){
        try{

            Connection conn = DriverManager.getConnection(jdbcURL,jdbcUsername,jdbcPassword);
            PreparedStatement checkUsnEmail = conn.prepareStatement("SELECT * FROM \"locations\"");
            ResultSet rs = checkUsnEmail.executeQuery();
            while(rs.next()){
                String countrycode = rs.getString(1);
                String city= rs.getString(2);
                String address = rs.getString(3);
                
                
                locationlist.add(new Locations(countrycode,city,address));
            }
            conn.close();
        }
        catch(Exception e){
            
        }
    }
    public String getCountryCode() {
        return countryCode;
    }
    public String getCity() {
        return City;
    }
    public String getAddress() {
        return Address;
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