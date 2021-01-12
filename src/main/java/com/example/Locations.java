
package com.example;

import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import java.sql.DriverManager;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
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
import com.vaadin.flow.component.textfield.TextField;



@Route(value="Locations")
@PageTitle("Locations")
@CssImport("./styles/styles.css")
public class Locations extends VerticalLayout {
    
    private Grid<Locations> grid;

    @Id
    private TextField countryCode;
    @Id
    private TextField city;
    @Id
    private TextField address;

    @Id
    private Button search;

    /**
     *
     */
    private static final long serialVersionUID = 1L;



    public Locations() {
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setSizeFull();
        addClassName("cv, grid");
        addHeader();
        
        // Import data out of postgreSQL
        H2 pageName = new H2("Add link here .. (to database)");
        private String jdbcURL = "jdbc:postgresql://localhost:5432/ProjectC";
        private String jdbcUsername = "postgres";
        private String jdbcPassword = "asdf";
        add(pageName);

        // Table (grid) for the 3 values
        grid = new Grid<>(Locations.class, false);
        grid.addColumn("countryCode").setAutoWidth(true);
        grid.addColumn("city").setAutoWidth(true);
        grid.addColumn("address").setAutoWidth(true);
        grid.setHeightFull();

          // Connect to database and get data
          try {
            Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
            String postgresqlcc = "SELECT * FROM location WHERE countryCode='"+ countryCode +"'";
            String postgresqlcity = "SELECT * FROM location WHERE city='"+ city +"'";
            String postgresqladdress = "SELECT * FROM location WHERE address='"+ address +"'";
            Statement statement = connection.createStatement();
            statement.executeUpdate(postgresqlcc, postgresqlcc, postgresqladdress);
            connection.close();
        } catch(SQLException e) {
            System.out.println("Error in connecting postgres");
            e.printStackTrace();
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