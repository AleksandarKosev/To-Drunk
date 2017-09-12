package com.a2drunk.alchotest.alchotest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Spinner;

/**
 * Created by Stefan on 06.9.2017.
 */

public class SecondActivity extends AppCompatActivity{

    private Spinner spinner1, spinner2;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);

        ListenerOnSpinner1_ItemSelection();
        GetValues();
    }

    public void ListenerOnSpinner1_ItemSelection(){
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        //spinner1.setOnItemSelectedListener();
    }

    //Get values from dropdown list
    public void GetValues(){

    }
}
