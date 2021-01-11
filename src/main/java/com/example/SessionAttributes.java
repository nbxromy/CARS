package com.example;
import com.vaadin.flow.server.VaadinService;

// Class to manage vaadin sessions
public class SessionAttributes {

    // Function to login user using HttpSession
    public static void login(String loggedUser) {
        VaadinService.getCurrentRequest().getWrappedSession().setAttribute("loggedIn", "true");
        VaadinService.getCurrentRequest().getWrappedSession().setAttribute("loggedUser", loggedUser);  
    }

    // Function to logout user using HttpSession
    public static void logout() {
        VaadinService.getCurrentRequest().getWrappedSession().setAttribute("loggedIn", "false");
        VaadinService.getCurrentRequest().getWrappedSession().setAttribute("loggedUser", "");
    }

    // Return string if user is logged in or not
    public static String getLoggedIn() {
        if (VaadinService.getCurrentRequest().getWrappedSession().getAttribute("loggedIn") == null) {
            return null;
        } else {
            return VaadinService.getCurrentRequest().getWrappedSession().getAttribute("loggedIn").toString();
        }
    }

    // Return string loggedUser
    public static String getLoggedUser() {
        if (VaadinService.getCurrentRequest().getWrappedSession().getAttribute("loggedUser") == null) {
            return null;
        } else {
            return VaadinService.getCurrentRequest().getWrappedSession().getAttribute("loggedUser").toString();
        }
    }

    // Function to store search value
    public static void setSearchValue(String value) {
        VaadinService.getCurrentRequest().getWrappedSession().setAttribute("searchValue", value);
    }

    // Return search value
    public static String getSearchValue() {
        if (VaadinService.getCurrentRequest().getWrappedSession().getAttribute("searchValue") == null) {
            return null;
        } else {
            return VaadinService.getCurrentRequest().getWrappedSession().getAttribute("searchValue").toString();
        }
    }
    
    // Function to select a car to rent
    public static void selectCar(String car, String licencePlate, String location) {
        VaadinService.getCurrentRequest().getWrappedSession().setAttribute("selectedCar", car);
        VaadinService.getCurrentRequest().getWrappedSession().setAttribute("selectedLicencePlate", licencePlate);
        VaadinService.getCurrentRequest().getWrappedSession().setAttribute("location", location);
    }

    // Return car to rent
    public static String getSelectedCar() {
        if (VaadinService.getCurrentRequest().getWrappedSession().getAttribute("selectedCar") == null) {
            return null;
        } else {
            return VaadinService.getCurrentRequest().getWrappedSession().getAttribute("selectedCar").toString();
        }
    }

    // Return licence plate of car to rent 
    public static String getSelectedLicencePlate() {
        if (VaadinService.getCurrentRequest().getWrappedSession().getAttribute("selectedLicencePlate") == null) {
            return null;
        } else {
            return VaadinService.getCurrentRequest().getWrappedSession().getAttribute("selectedLicencePlate").toString();
        }
    }

    //Return pickup location of car to rent
    public static String getSelectedLocation() {
        if (VaadinService.getCurrentRequest().getWrappedSession().getAttribute("location") == null) {
            return null;
        } else {
            return VaadinService.getCurrentRequest().getWrappedSession().getAttribute("location").toString();
        }
    }
}
