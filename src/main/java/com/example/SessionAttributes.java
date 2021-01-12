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


    // Function to login employees
    public static void employeeLogin(String employeeUser){
        VaadinService.getCurrentRequest().getWrappedSession().setAttribute("loggedinEmployee", "true");
        VaadinService.getCurrentRequest().getWrappedSession().setAttribute("loggedEmployee",employeeUser); 
    }

    // Function to logout emplotee using HttpSession
    public static void employeeLogout(){
        VaadinService.getCurrentRequest().getWrappedSession().setAttribute("loggedinEmployee", "false");
        VaadinService.getCurrentRequest().getWrappedSession().setAttribute("loggedEmployeee", "");
    }

    // Return string if employee is logged in or not
    public static String getEmployeeLogin(){
        if (VaadinService.getCurrentRequest().getWrappedSession().getAttribute("loggedinEmployee") == null) {
            return null;
        } else {
            return VaadinService.getCurrentRequest().getWrappedSession().getAttribute("loggedinEmployee").toString();
        }
    }
    // Return string employee user
    public static String getEmployeeUser() {
        if (VaadinService.getCurrentRequest().getWrappedSession().getAttribute("loggedEmployee") == null) {
            return null;
        } else {
            return VaadinService.getCurrentRequest().getWrappedSession().getAttribute("loggedEmployee").toString();
        }
    }
    // Return string admin user
    public static String getAdminUser() {
        if (VaadinService.getCurrentRequest().getWrappedSession().getAttribute("loggedAdmin") == null) {
            return null;
        } else {
            return VaadinService.getCurrentRequest().getWrappedSession().getAttribute("loggedAdmin").toString();
        }
    }
    // Function to login admin
    public static void adminLogin(String adminUser){
        VaadinService.getCurrentRequest().getWrappedSession().setAttribute("loggedinAdmin", "true");
        VaadinService.getCurrentRequest().getWrappedSession().setAttribute("loggedAdmin",adminUser); 
    }

    // Function to logout admin using HttpSession
    public static void adminLogout(){
        VaadinService.getCurrentRequest().getWrappedSession().setAttribute("loggedinAdmin", "false");
        VaadinService.getCurrentRequest().getWrappedSession().setAttribute("loggedAdmin", "");
    }

    // Return string if admin is logged in or not
    public static String getAdminLogin(){
        if (VaadinService.getCurrentRequest().getWrappedSession().getAttribute("loggedinAdmin") == null) {
            return null;
        } else {
            return VaadinService.getCurrentRequest().getWrappedSession().getAttribute("loggedinAdmin").toString();
        }
    }
}
