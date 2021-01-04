package com.example;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value="FAQ")
@CssImport("./styles/styles.css")
public class FAQ extends VerticalLayout {

    private static final long serialVersionUID = 1L;

    public FAQ() {
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setSizeFull();
        addClassName("faq");
        addHeader();
        H2 pageName = new H2("Frequently Asked Questions");

        var faq1 = new Details("How do I become a Qars member?", new Text("You become a Qars member when you create your account by navigating to the 'Register' page."));
        var faq2 = new Details("How old do I have to be to rent a car?", new Text("You have to be at least 21 years old."));
        var faq3 = new Details("What are the costs to rent a car?", new Text("The costs vary per car, we advise you to check the rental page."));
        var faq4 = new Details("Can I cancel my booking?", new Text("Qars allows you to cancel your reservation online after it is confirmed, provided it is done up to 48 hours prior to rental time."));
        var faq5 = new Details("Is it allowed to rent a car for another person?", new Text("That is not allowed."));
        var faq6 = new Details("Are there any differences or restrictions due to COVID-19?", new Text("Please check the COVID-19 tab."));
        var faq7 = new Details("When do I have to return the vehicle?", new Text("The vehicle should be return to the Qars station at the latest on the date and time shown on the rental agreement document."));
        var faq8 = new Details("What do I need to do in the event of loss of keys?", new Text("You will need to contact the Qars station where you rented your car. You can find the contact details on the 'Locations' page."));
        var faq9 = new Details("What happens if I have a fine?", new Text("In case you have a fine during your rental, you will receive the fine notice from the police or from Qars."));
        var faq10 = new Details("Do I need to check the condition of the vehicle prior leaving the location?", new Text("Yes, you have to check that the condition report reflects the actual vehicle condition."));
        add(pageName, faq1,faq2,faq3,faq4,faq5,faq6,faq7,faq8,faq9,faq10);
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
            MenuItem menuItemRegister = subMenuLogin.addItem("Logout");
            menuItemRegister.addClickListener(e -> SessionAttributes.logout());
            menuItemRegister.addClickListener(e -> menuItemRegister.getUI().ifPresent(ui -> ui.navigate("Login")));
        }
        add(header, menuBar);
    }
}
