package com.example;

import java.util.List;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.example.Application;
 

@Route("Reviews")
@CssImport("./styles/styles.css")
public class Reviews extends VerticalLayout {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    public Reviews() {
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setSizeFull();
        addClassName("reviews");
        addHeader();

        H2 pageName = new H2("Reviews");
        pageName.getElement().getThemeList();
        add(pageName);
        askReview();
        sendReview("Huss", "testt", "aaaaaaaaaaaaaaaaaaaaa");

    }

    public void askReview() {
        FormLayout reviewLayout = new FormLayout();

        TextField usernamField = new TextField();
        usernamField.setLabel("Username");
        usernamField.setMaxWidth("16em");
        PasswordField passwordField = new PasswordField();
        passwordField.setLabel("Password");
        passwordField.setMaxWidth("16em");
        TextArea reviewField = new TextArea("Give us a review!");
        reviewField.setPlaceholder("Write here ...");
        reviewField.setMaxWidth("30em");
        Button sendReview = new Button("Send review");
        if(sendReview.isEnabled())
        reviewLayout.add(usernamField, passwordField, reviewField, sendReview);
        reviewLayout.setColspan(reviewField, 4);
        usernamField.getValue();
        
        add(reviewLayout);
    }

    // Function thatll send the review when the button is pressed.
    public void sendReview(String usn, String password, String review) {
        try {
            Application.insertReviewTable(usn, password, review);
        } 
        catch (Exception e) {
           System.out.println(e);
        }
        
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
        MenuItem menuItemLogin = menuBar.addItem("Login");
        menuItemLogin.addComponentAsFirst(new Icon(VaadinIcon.USER));
        menuItemLogin.addClickListener(e -> menuItemLogin.getUI().ifPresent(ui -> ui.navigate("Login")));

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
