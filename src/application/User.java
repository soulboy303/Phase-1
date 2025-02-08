package application;

import java.util.ArrayList;

/**
 * The User class represents a user entity in the system.
 * It contains the user's details such as userName, password, and roles.
 */
public class User {
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private boolean isTempPassword;
    private ArrayList<String> roles;

    // Constructor to initialize a new User object
    
    public User(String userName, String password, String firstName, String lastName, String emailAddress, String initialRole, boolean isTemp) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.isTempPassword = isTemp;
        this.roles = new ArrayList<>();
        if (initialRole != null) {
            this.roles.add(initialRole);
        }
    }

    // This lets us add a role to a user.
    public void addRole(String role) {
        if (role != null && !roles.contains(role)) {
            roles.add(role);
        }
    }

    // This allows us to set roles at locations
    public void setRole(int index, String role) {
        if (index >= 0 && index < roles.size()) {
            roles.set(index, role);
        } else if (index == roles.size()) {
            roles.add(role);
        } else {
            throw new IndexOutOfBoundsException("Invalid role index");
        }
       // this.numRoles++;
    }

    // I made it so that you can request a specific role location.
    public String getRole(int index) {
        if (index >= 0 && index < roles.size()) {
            return roles.get(index);
        } else {
            throw new IndexOutOfBoundsException("Invalid role index");
        }
    }
    

    // You can retrieve all roles to see what is where on a user
    public ArrayList<String> getAllRoles() {
        return new ArrayList<>(roles); 
    }

    // Getters for other fields
    public String getUserName() { return userName; }
    public String getPassword() { return password; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmailAddress() { return emailAddress; }
    public boolean getIsTemp() { return isTempPassword; }
    public ArrayList<String> getRole() { return roles; }
}