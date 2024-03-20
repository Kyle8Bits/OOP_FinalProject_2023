package com.example.finalproject_oop_2023.User;


public class User implements Comparable<User>{
    private String id;
    private String username;
    private String password;



    public User( String id, String username, String password) {
        this.id =id;
        this.username = username;
        this.password = password;
    }

    public User(String username){
        this.username= username;
    }



    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }




    @Override
    public int compareTo(User o) {
        return this.id.compareTo(o.id);
    }
}
