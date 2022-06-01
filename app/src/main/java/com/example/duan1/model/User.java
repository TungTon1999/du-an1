package com.example.duan1.model;

public class User {
   private String phonenumber,lastname,firstname,dob,email;

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User(String phonenumber, String lastname, String firstname, String dob, String email) {
        this.phonenumber = phonenumber;
        this.lastname = lastname;
        this.firstname = firstname;
        this.dob = dob;
        this.email = email;
    }
}
