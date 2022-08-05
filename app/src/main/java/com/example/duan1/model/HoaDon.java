package com.example.duan1.model;

import java.util.Date;
import java.util.List;

public class HoaDon {
    String foodname,price,address,date,discountcode,phone;

    public HoaDon(String foodname, String price, String address, String date, String discountcode, String phone) {
        this.foodname = foodname;
        this.price = price;
        this.address = address;
        this.date = date;
        this.discountcode = discountcode;
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public HoaDon() {
    }

    public String getDiscountcode() {
        return discountcode;
    }

    public void setDiscountcode(String discountcode) {
        this.discountcode = discountcode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



    public HoaDon(String foodname, String price, String address) {
        this.foodname = foodname;
        this.price = price;
        this.address = address;
    }

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
