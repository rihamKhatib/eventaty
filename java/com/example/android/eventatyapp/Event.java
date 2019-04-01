package com.example.android.eventatyapp;

public class Event {
    private int id ;
    private String name;
    private String image ;
    private String email;
    private String detail;

    public Event() {
    }

    public Event(int id, String name, String image, String email, String detail) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.email = email;
        this.detail = detail;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


}
