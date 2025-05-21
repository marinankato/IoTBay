package model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class User implements Serializable {

    int userID;
    String firstName;
    String lastName;
    String phoneNo;
    String email;
    String password;
    String role;
    LocalDateTime loginDate;
    LocalDateTime logoutDate;
    String status;

    public User() {
    }

    public User(int userId, String firstName, String lastName, String phoneNo, String email, String password, String role) {
        this.userID = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNo = phoneNo;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User(int userId, String firstName, String lastName, String phoneNo, String email, String password,
                String role, LocalDateTime loginDate, LocalDateTime logoutDate, String status) {
        this.userID = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNo = phoneNo;
        this.email = email;
        this.password = password;
        this.role = role;
        this.loginDate = loginDate;
        this.logoutDate = logoutDate;
        this.status = status;
    }

    public int getUserID() {
        return this.userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNo() {
        return this.phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getLoginDate() {
        return this.loginDate;
    }

    public void setLoginDate(LocalDateTime loginDate) {
        this.loginDate = loginDate;
    }

    public LocalDateTime getLogoutDate() {
        return this.logoutDate;
    }

    public void setLogoutDate(LocalDateTime logoutDate) {
        this.logoutDate = logoutDate;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}