package com.ad.adrentalhouse;

public class Property {
    String Owner_name;
    String Address1;
    String Address2;
    String Age_of_Property;
    String Email;
    String Floor;
    String Image;
    String Rent_Out_For;
    String Rent_Other_Detail;
    String Phone;
    String Rent_price;
    String Room;
    String Property_Type;

    public Property() {

    }

    public Property(String image, String rent_price, String room, String property_Type) {
        Image = image;
        Rent_price = rent_price;
        Room = room;
        Property_Type = property_Type;
    }


    public Property(String owner_name, String address1, String address2, String age_of_Property, String email, String floor, String image, String rent_Out_For, String rent_Other_Detail, String phone, String rent_price, String room, String property_Type) {
        Owner_name = owner_name;
        Address1 = address1;
        Address2 = address2;
        Age_of_Property = age_of_Property;
        Email = email;
        Floor = floor;
        Image = image;
        Rent_Out_For = rent_Out_For;
        Rent_Other_Detail = rent_Other_Detail;
        Phone = phone;
        Rent_price = rent_price;
        Room = room;
        Property_Type = property_Type;
    }


    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getRent_price() {
        return Rent_price;
    }

    public void setRent_price(String rent_price) {
        Rent_price = rent_price;
    }

    public String getRoom() {
        return Room;
    }

    public void setRoom(String room) {
        Room = room;
    }

    public String getProperty_Type() {
        return Property_Type;
    }

    public void setProperty_Type(String property_Type) {
        Property_Type = property_Type;
    }

    public String getOwner_name() {
        return Owner_name;
    }

    public void setOwner_name(String owner_name) {
        Owner_name = owner_name;
    }

    public String getAddress1() {
        return Address1;
    }

    public void setAddress1(String address1) {
        Address1 = address1;
    }

    public String getAddress2() {
        return Address2;
    }

    public void setAddress2(String address2) {
        Address2 = address2;
    }

    public String getAge_of_Property() {
        return Age_of_Property;
    }

    public void setAge_of_Property(String age_of_Property) {
        Age_of_Property = age_of_Property;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getFloor() {
        return Floor;
    }

    public void setFloor(String floor) {
        Floor = floor;
    }

    public String getRent_Out_For() {
        return Rent_Out_For;
    }

    public void setRent_Out_For(String rent_Out_For) {
        Rent_Out_For = rent_Out_For;
    }

    public String getRent_Other_Detail() {
        return Rent_Other_Detail;
    }

    public void setRent_Other_Detail(String rent_Other_Detail) {
        Rent_Other_Detail = rent_Other_Detail;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}