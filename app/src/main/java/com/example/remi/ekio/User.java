package com.example.remi.ekio;


public class User {

    private long id;
    private String email;
    private String nom;
    private String password;
    private Boolean autoConnect;

    public User(long id, String email, String nom, String password, Boolean autoConnect) {
        super();
        this.id = id;
        this.email = email;
        this.nom = nom;
        this.password = password;
        this.autoConnect = autoConnect;
    }
    public User(String email, String nom, String password, Boolean autoConnect) {
        super();
        this.email = email;
        this.nom = nom;
        this.password = password;
        this.autoConnect = autoConnect;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public String getNom() {
        return nom;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Boolean getAuto() {
        return autoConnect;
    }

    public void setAuto(Boolean autoConnect) {
        this.autoConnect = autoConnect;
    }

}
