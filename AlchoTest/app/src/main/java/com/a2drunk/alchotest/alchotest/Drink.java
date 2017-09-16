package com.a2drunk.alchotest.alchotest;

public class Drink {
    String description;
    float oz;
    float procentAlcho;

    public Drink(String description, float oz, float procentAlcho) {
        this.description = description;
        this.oz = oz;
        this.procentAlcho = procentAlcho;
    }

    public float returnAlchocolOz(){

        return oz*procentAlcho;}

    @Override
    public String toString() {return description + '\'' + oz + procentAlcho;}

    public float returnStandardDrink(){return oz* (float)0.0295735296875*procentAlcho*100*(float)0.789;}
}
