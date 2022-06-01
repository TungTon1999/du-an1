package com.example.duan1.model;


import java.io.Serializable;

public class SanPham implements Serializable {
    private String name;
    private String price;
    private String type;
    private String image;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SanPham() {
    }

    public SanPham(String name, String price, String type, String image, String id) {
        this.name = name;
        this.price = price;
        this.type = type;
        this.image = image;
        this.id = id;
    }

    public SanPham(String name, String price) {
        this.name = name;
        this.price = price;
    }

    public SanPham(String name, String price, String image) {
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public SanPham(String name, String price, String type, String image) {
        this.name = name;
        this.price = price;
        this.type = type;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
