package com.example.remi.ekio;


public class User {

    private long id;
    private String intitule;
    private float salaire;

    public User(long id, String intitule, float salaire) {
        super();
        this.id = id;
        this.intitule = intitule;
        this.salaire = salaire;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public float getSalaire() {
        return salaire;
    }

    public void setSalaire(float salaire) {
        this.salaire = salaire;
    }

}
