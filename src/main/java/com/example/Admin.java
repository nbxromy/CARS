package com.example;


import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.datepicker.DatePicker;
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
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;


import com.vaadin.flow.component.button.Button;

@Route("Admin")
@CssImport("./styles/styles.css")
public class Admin extends VerticalLayout{

    private static final long serialVersionUID = 1L;
    private static boolean isAdmin;
    private boolean isEmp;

    public Admin(){

        addHeader();
        
        isAdmin = true;
        Employee.isEmployee = true;
        
        addCar();
        formDeleteCar();
        formCreateEmployee();
        
    }
    
    public static boolean getAdmin(){
        return true;
    }
    // Form for adding a car to the database

    public void addCar(){
        FormLayout form = new FormLayout();
        form.addClassName("formAddcar");
        form.setMaxWidth("15em");

        H2 title = new H2("Add car to database");
        TextField model = new TextField("Car model");
        model.setMaxWidth("10em");
        TextField licenseplate = new TextField("License plate ");
        licenseplate.setMaxWidth("8em");
        TextField mileage = new TextField("Mileage"); 
        mileage.setMaxWidth("8em");
        TextField location = new TextField("Location code");
        location.setMaxWidth("10em");
        Button buttonCar = new Button("Add");

        form.setColspan(title,2);
        form.add(title, model, licenseplate, mileage, location,buttonCar);
        add(form);

        buttonCar.addClickListener(click->{ 
            if((model.getValue()!="") & (mileage.getValue()!="") & (licenseplate.getValue()!="") & (location.getValue()!="")){
                sendCar(Integer.parseInt(model.getValue()), Integer.parseInt(mileage.getValue()), licenseplate.getValue(), location.getValue());
                
            }
            else{
                Notification.show("Fill in all fields", 5000, Position.TOP_CENTER);
            }
        });        
    }   

    // Form for deleting a car

    public void formDeleteCar(){
        FormLayout form = new FormLayout();
        form.addClassName("formDeletecar");
        H2 title = new H2("Delete car from database");
        form.setMaxWidth("15em");
        TextField license = new TextField();
        license.setLabel("License plate");
        license.setMaxWidth("8em");
        Button send = new Button("Delete");
        form.add(title,license,send);
        send.addClickListener(click->{
            if((license.getValue()!="")){
                deleteCar(license.getValue());
            }
            
        });
        add(form);
    }

    public void formCreateEmployee(){

        FormLayout form = new FormLayout();
        form.addClassName("formEmployee");
        form.setMaxWidth("25em");
        H2 title = new H2("Create employee account");
        form.setColspan(title, 2);

        TextField Usn = new TextField("Username");
        Usn.setMaxWidth("10em");
        PasswordField Pass = new PasswordField("Password");
        Pass.setMaxWidth("10em");
        PasswordField checkPass = new PasswordField("Password check");
        EmailField Email = new EmailField("Email");
        Email.setErrorMessage("Please enter a valid email");
        TextField Number = new TextField("Phone number");
        TextField cityZip = new TextField("City and zip");
        TextField Address = new TextField("Address");
        DatePicker Birthdate = new DatePicker("Birth date");
        Birthdate.setClearButtonVisible(true);
        TextField codeWorker = new TextField("Insert 0 for admin account, insert 1 for employee account");
        Button buttonCreate = new Button("Create");
        
        form.add(title,Usn,Pass,checkPass,Email,Number,cityZip,Address,Birthdate,codeWorker,buttonCreate);

        buttonCreate.addClickListener(click->{
            if((Usn.getValue()=="")||(Pass.getValue()=="")||(checkPass.getValue()=="")|| (Email.getValue()=="")||(Number.getValue()=="")||(cityZip.getValue()=="")||(Address.getValue()=="")||(Birthdate.getValue()==null)){
                Notification.show("Fill in all fields", 5000, Position.TOP_CENTER);
               
            }
            // Still unable to show notification when account is created
            else{ 
                if(!checkPass.getValue().equals(Pass.getValue())){
                    Notification.show("Passwords do not match", 5000, Position.TOP_CENTER);
                }
                else if(Integer.parseInt(codeWorker.getValue())!=0 & Integer.parseInt(codeWorker.getValue())!=1){
                    Notification.show("Incorrect code worker", 5000, Position.TOP_CENTER);
                }
                else{
                    if(createEmployee(Usn.getValue(), Pass.getValue(), Email.getValue(), Number.getValue(),cityZip.getValue(), Address.getValue(), Birthdate.getValue(), codeWorker.getValue())){
                        Notification.show("Account created!", 5000, Position.TOP_CENTER);
                    }
                    else{
                        //Notification.show("Failed", 5000, Position.TOP_CENTER);
                    }
                    //  Notification.show("Account created!", 5000, Position.TOP_CENTER);
                }
            }
        });
        add(form);
    }

    public boolean createEmployee(String usn, String pass, String email, String number, String cityZip, String address, LocalDate bday, String code){
        boolean created= false;
        try{

            Connection conn = DriverManager.getConnection(Application.jdbcURL,Application.username,Application.password);
            PreparedStatement checkUsnEmail = conn.prepareStatement("SELECT * FROM \"Workers\" WHERE username='"+usn+"' OR email='"+email+"'");
            ResultSet rs = checkUsnEmail.executeQuery();

            if(rs.next()){
                Notification.show("Username or email already exists", 5000, Position.TOP_CENTER);
                created= false;
            }
            else{
                PreparedStatement insertWorker = conn.prepareStatement("INSERT INTO \"Workers\" VALUES ('"+usn+"', '"+ pass+"', '"+code +"','"+ cityZip+"', '"+address +"','"+ bday+"','"+number+"','"+ email+"')");
                insertWorker.executeQuery();
                Notification.show("Account created!", 5000, Position.TOP_CENTER);
                
                created =true;
                
            }
            conn.close();
        }
        catch(Exception e){
            System.out.println("Failed to create employee\n"+e);
        }
        return created;
    }

  

    // Function for inserting car to database
    // Checks if licenseplate already exists

    public void sendCar(int model,int mileage ,String license, String location){
        try{
            Connection conn = DriverManager.getConnection(Application.jdbcURL,Application.username,Application.password);
            PreparedStatement checkLicense = conn.prepareStatement("select * from \"Cars\" WHERE \"licensePlate\"='"+ license+ "'");
            ResultSet rs = checkLicense.executeQuery();
            if(rs.next()){
                Notification.show("License plate already exists in the database", 5000, Position.TOP_CENTER);
            }
            else{
                PreparedStatement insert = conn.prepareStatement("INSERT INTO \"Cars\" VALUES ('"+model+"', '"+ license+"', '"+mileage +"','"+ location+"')");
                insert.executeUpdate();
                Notification.show("Car inserted in database!", 5000, Position.TOP_CENTER);
            }
            conn.close();
            
        }
        catch(Exception e){
            Notification.show("Failed to add car", 5000, Position.TOP_CENTER);
            System.out.println(e);
            
        }
    }
    
    // Function to delete car from database
    // Checks if license plate exists

    public void deleteCar(String licenseplate){
        try{
            Connection conn = DriverManager.getConnection(Application.jdbcURL,Application.username,Application.password);
            
            PreparedStatement checkLicense = conn.prepareStatement("select * from \"Cars\" WHERE \"licensePlate\"='"+ licenseplate+ "'");
            ResultSet rs = checkLicense.executeQuery();
            if(rs.next()){
                PreparedStatement delete = conn.prepareStatement("DELETE FROM \"Cars\" WHERE \"licensePlate\"= '" +licenseplate + "'");
                delete.executeUpdate();
                Notification.show("Car deleted from database!", 5000, Position.TOP_CENTER);
            }
            else{
                Notification.show("License plate not found", 5000, Position.TOP_CENTER);
            }
            conn.close();
        }
        catch(Exception e){
            Notification.show("Failed to delete car", 5000, Position.TOP_CENTER);
            System.out.println(e);
        }
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
