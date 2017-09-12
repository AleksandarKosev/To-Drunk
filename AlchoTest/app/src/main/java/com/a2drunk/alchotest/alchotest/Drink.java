package com.a2drunk.alchotest.alchotest;

public class Drink {
    String description;
    float oz;
    float procentAlcho;
    String timestamp;

    public Drink(String description, float oz, float procentAlcho, String timestamp) {
        this.description = description;
        this.oz = oz;
        this.procentAlcho = procentAlcho;
        this.timestamp = timestamp;
    }

    public float returnAlchocolOz(){return oz*procentAlcho;}

    @Override
    public String toString() {return description + '\'' + oz + procentAlcho + timestamp;}

    public float returnStandardDrink(){return oz* (float)0.0295735296875*procentAlcho*(float)0.789;}
}
