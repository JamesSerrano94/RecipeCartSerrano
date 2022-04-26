package com.example.recipecartnew;

public class User {
    private String name, username, email, password, measureType, imageURL;
    private static User instance;
    public static User getInstance(){
        if (instance == null){
            instance = new User();
        }
        return instance;
    }
    private User(){ }
    public void logout(){
        this.username = null;
        this.email = null;
        this.password = null;
        this.measureType = null;
        this.name = null;
        this.imageURL = null;
        this.instance = null;
    }


    public void setName(String name){this.name = name;}
    public String getName(){return this.name;}

    public void setUsername(String username){
        this.username = username;
    }
    public String getUsername(){
        return this.username;
    }

    public void setPassword(String password){
        this.password = password;
    }
    public String getPassword(){
        return this.password;
    }

    public void setEmail(String email){
        this.email = email;
    }
    public String getEmail(){
        return this.email;
    }

    public void setMeasureType(String measureType){
        this.measureType = measureType;
    }
    public String getMeasureType(){
        return this.measureType;
    }

    public void setImageURL(String URL){ this.imageURL = URL.trim();}
    public String getImageURL(){return this.imageURL;}
}
