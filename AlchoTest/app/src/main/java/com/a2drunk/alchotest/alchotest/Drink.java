package com.a2drunk.alchotest.alchotest;

/**
 * Created by Stefan on 04.10.2017.
 * i Ace
 */

public class Drink {
    String description;
    float mll;
    float procentAlcho;

    public Drink(String description, float mll, float procentAlcho) {
        this.description = description;
        this.mll = mll;
        this.procentAlcho = procentAlcho;
    }

    public float returnAlchocolmll(){

        return mll*procentAlcho;}

    @Override
    public String toString() {return description + '\'' + mll + procentAlcho;}

    public float returnStandardDrink()
    {return mll*procentAlcho*100*(float)0.789;}




}
