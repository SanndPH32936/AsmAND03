package com.example.asmand03.model;

public class PhoneModel {
    private String _id ;
    private String name;
    private String brand;
    private int price;
    private String des;
    private String img;

    public PhoneModel(String name, String brand, int price, String des, String img) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.des = des;
        this.img = img;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public PhoneModel(String _id, String name, String brand, int price, String des, String img) {
        this._id = _id;
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


    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
