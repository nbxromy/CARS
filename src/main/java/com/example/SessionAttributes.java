package com.example;
import com.vaadin.flow.server.VaadinService;

// Class to manage login sessions
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
}
