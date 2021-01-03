package com.example;

import java.time.LocalDateTime;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value="Rent")
@CssImport("./styles/styles.css")
public class Rent extends VerticalLayout {
    private static final long serialVersionUID = 1L;
    public LocalDateTime today = LocalDateTime.now(); 

    public Rent() {
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setSizeFull();
        addHeader();

        H2 title = new H2("Rent a car");

        DateTimePicker pickupDatePicker = new DateTimePicker();
        pickupDatePicker.setDatePlaceholder("Date");
        pickupDatePicker.setTimePlaceholder("Time");
        pickupDatePicker.setLabel("Pick up date");

        DateTimePicker dropoffDatePicker = new DateTimePicker();
        dropoffDatePicker.setDatePlaceholder("Date");
        dropoffDatePicker.setTimePlaceholder("Time");
        dropoffDatePicker.setLabel("Drop off date");

        CheckboxGroup<String> checkboxExtras = new CheckboxGroup<>();
        checkboxExtras.setLabel("Extra");
        checkboxExtras.setItems("GPS", "Insurance", "Winter tires", "Extra driver", "Childseat");
        checkboxExtras.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);

        Span errorMessage = new Span();
        errorMessage.getStyle().set("color", "var(--lumo-error-text-color)");
        errorMessage.getStyle().set("padding", "15px 0");

        Button confirmButton = new Button("Confirm choice and proceed to checkout");
        confirmButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        confirmButton.addClickListener(e -> checkDates(pickupDatePicker.getValue(), dropoffDatePicker.getValue(), errorMessage));

        Button cancelButton = new Button("Cancel");
        cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        cancelButton.addClickListener(e -> UI.getCurrent().navigate(""));

        add(title, pickupDatePicker, dropoffDatePicker, checkboxExtras, errorMessage, confirmButton, cancelButton);
    }

    public void checkDates(LocalDateTime pickupDate, LocalDateTime dropoffDate, Span errorMessage) {  
        if (pickupDate == null || dropoffDate == null) {
            errorMessage.setText("Please enter the dates");
        } else if (pickupDate.isBefore(today)) {
            errorMessage.setText("Please enter a valid pickup date");
        } else if (pickupDate.isAfter(today.plusYears(1))) {
            errorMessage.setText("You can't rent a car for the next year");
        } else if (dropoffDate.isBefore(pickupDate) || dropoffDate.isBefore(today)) {
            errorMessage.setText("Please enter a valid dropoff date");
        } else if (dropoffDate.isAfter(pickupDate.plusMonths(1))) {
            errorMessage.setText("You can rent a car for a maximum of one month in a row");
        } else {
            UI.getCurrent().navigate("Payment");
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
        MenuItem menuItemRentInformation = subMenuRent.addItem("Information");
        menuItemRentInformation.addClickListener(e -> menuItemRentInformation.getUI().ifPresent(ui -> ui.navigate("Information")));
        
        SubMenu subMenuLogin = menuItemLogin.getSubMenu();
        MenuItem menuItemRegister = subMenuLogin.addItem("Register");
        menuItemRegister.addClickListener(e -> menuItemRegister.getUI().ifPresent(ui -> ui.navigate("Register")));
        add(header, menuBar);
    }
}
