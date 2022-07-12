package com.example.prac2;

public class UserModel {
    private int id;
    private String name;
    private String description;
    private Boolean followed;
    public UserModel() { }
    public UserModel(int id, String name, String description, Boolean followed) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.followed = followed;
    }


}
