package com.example.courseapp;

public class StudentModel {
    String id="";
    String username ="";
    String email="";
    String domaine ="";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDomaine() {
        return domaine;
    }

    public void setDomaine(String domaine) {
        this.domaine = domaine;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    String niveau ="";
    String created_at="";

    public StudentModel(String id, String username, String email, String domaine, String niveau, String created_at) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.domaine = domaine;
        this.niveau = niveau;
        this.created_at = created_at;
    }


    public StudentModel(){

    }
}


