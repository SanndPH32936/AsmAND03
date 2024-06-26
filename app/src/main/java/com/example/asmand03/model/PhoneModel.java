package com.example.asmand03.model;

import java.util.ArrayList;

public class PhoneModel {
    private String _id ;
    private String name;
    private String brand;
    private int price;
    private String des;
    private String img;

    public PhoneModel(String _id, String name, String brand, int price, String des, String img) {
        this._id = _id;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.des = des;
        this.img = img;
    }

    public PhoneModel(String name, String brand, int price, String des) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.des = des;
    }

    public PhoneModel(String name, String brand, int price, String des, String img) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.des = des;
        this.img = img;
    }


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getImage() {
        return img;
    }

    public void setImage(String image) {
        this.img = image;
    }
}
