package com.a2drunk.alchotest.alchotest;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class MainActivity extends AppCompatActivity {


    private Button submit, Ok;
    private RadioGroup radioSexGroup;
    private EditText weight_text;

    String warning_body_kg = "Please insert your weight!";

    float BODY_WEIGHT_IN_KG = 0;
    int GENDERTYPE = 0;
    float lastDrinkHr = 0; //IN HOURS
    float drinkingPeriodHr = 0; //HOURS OF DRINKING

    float BODY_WEIGHT_IN_LB = 0;
    double GENDER =  0; //0.73 for man//ili 0.66 for women
    float BODY_WATER_CONSTANT = (float) 0;//0.58 for man//0.49 for women !!!!
    float METABOLISM_CONSTANT = (float) 0;//0.015 for man//0.017 for women !!!!


    void storeGENDERTYPE() {
        //SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        //SharedPreferences.Editor editor = settings.edit();
        //editor.putInt("GENDERTYPE", GENDERTYPE);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("GENDERTYPE", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("GENDERTYPE", GENDERTYPE);        // Saving integer
        editor.apply();
        //ediotr.commit();
    }

    void storeGENDER() {
        //SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        //SharedPreferences.Editor editor = settings.edit();
        //editor.putInt("GENDERTYPE", GENDERTYPE);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("GENDER", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putFloat("GENDER", (float) GENDER);        // Saving integer
        editor.apply();
        //ediotr.commit();
    }



    void storeBODY_WEIGHT_IN_KG() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("BODY_WEIGHT_IN_KG", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putFloat("BODY_WEIGHT_IN_KG", BODY_WEIGHT_IN_KG);        // Saving integer
        editor.apply();
        //ediotr.commit();
    }


/*
    double BODY_WEIGHT_IN_LB=250;
    double GENDER=0.73; //ili 0.66
    double lastDrinkHr=1.5; */

    //BAC%=(A*5.14/W*r)-0.015*H//A=total alchocol consumed(oz)//W=Body weight(lbs)//r=constant(0.73 or 0.66)//H=last drink
    double mainFormula1(double listOz[],double listProcent[]){
        double pro=0;
        for(int i=0;i<listOz.length;i++){
            pro+=listOz[i]*listProcent[i];
        }
        return (pro*0.75)-BODY_WEIGHT_IN_LB-lastDrinkHr*0.015;

    }

    double mainFormula2(double listOz[],double listProcent[]){
        double pro=1;
        for(int i=0;i<listOz.length;i++){
            pro+=(listOz[i]*listProcent[i]/BODY_WEIGHT_IN_LB*GENDER)-0.015*lastDrinkHr;
        }
        return (pro*0.75)-BODY_WEIGHT_IN_LB-lastDrinkHr*0.015;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        radioSexGroup = (RadioGroup) findViewById(R.id.radioGroup);
        weight_text = (EditText) findViewById(R.id.weightText);

        buttonClick();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
     getMenuInflater().inflate(R.menu.mymenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mymenu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.info:
                AlertDialog.Builder iBuilder = new AlertDialog.Builder(MainActivity.this);
                View iView = getLayoutInflater().inflate(R.layout.info_dialog, null);
                iBuilder.setView(iView);
              final  AlertDialog dialog = iBuilder.create();
                Ok = (Button) iView.findViewById(R.id.ok_button);
                Ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });



                dialog.show();

        }


            return true;
    }

    public void buttonClick(){
        submit = (Button)findViewById(R.id.button);
        //need to take values!!!
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int selectedID = radioSexGroup.getCheckedRadioButtonId();




                       if(selectedID == R.id.radioButton1) {
                            /*GENDER = 0.73;
                            BODY_WATER_CONSTANT = (float) 0.58;
                            METABOLISM_CONSTANT = (float) 0.015;
                            storeGENDERTYPE();*/
                            GENDER = 0.73;
                            SharedPreferences pref = getApplicationContext().getSharedPreferences("GENDER", MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putFloat("GENDER",(float) GENDER);        // Saving integer
                            editor.apply();

                           BODY_WATER_CONSTANT = (float) 0.58;
                            pref = getApplicationContext().getSharedPreferences("BODY_WATER_CONSTANT", MODE_PRIVATE);
                            editor = pref.edit();
                           editor.putFloat("BODY_WATER_CONSTANT", BODY_WATER_CONSTANT);        // Saving integer
                           editor.apply();


                           METABOLISM_CONSTANT = (float) 0.015;

                            pref = getApplicationContext().getSharedPreferences("METABOLISM_CONSTANT", MODE_PRIVATE);
                            editor = pref.edit();
                           editor.putFloat("METABOLISM_CONSTANT", METABOLISM_CONSTANT);        // Saving integer
                           editor.apply();


                        }

                       else if(selectedID == R.id.radioButton2)
                        {
                            GENDER = 0.66;
                            SharedPreferences pref = getApplicationContext().getSharedPreferences("GENDER", MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putFloat("GENDER",(float) GENDER);        // Saving integer
                            editor.apply();

                            BODY_WATER_CONSTANT = (float)0.49;
                            pref = getApplicationContext().getSharedPreferences("BODY_WATER_CONSTANT", MODE_PRIVATE);
                            editor = pref.edit();
                            editor.putFloat("BODY_WATER_CONSTANT", BODY_WATER_CONSTANT);        // Saving integer
                            editor.apply();

                            METABOLISM_CONSTANT = (float) 0.017 ;
                            pref = getApplicationContext().getSharedPreferences("METABOLISM_CONSTANT", MODE_PRIVATE);
                            editor = pref.edit();
                            editor.putFloat("METABOLISM_CONSTANT", METABOLISM_CONSTANT);        // Saving integer
                            editor.apply();

                        }



                String kg = weight_text.getText().toString();
                if(kg.isEmpty())
                {Toast.makeText(MainActivity.this, warning_body_kg, Toast.LENGTH_LONG).show();
                }
               else  {BODY_WEIGHT_IN_KG = Float.parseFloat(kg);
                // if(BODY_WEIGHT_IN_KG > 0) {
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("BODY_WEIGHT_IN_KG", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putFloat("BODY_WEIGHT_IN_KG", BODY_WEIGHT_IN_KG);        // Saving integer
                    editor.apply();

                    //ediotr.commit();
                    startActivity(new Intent(MainActivity.this, CalculatorActivity.class));
                }
                /*else
                {
                    Toast.makeText(MainActivity.this, warning_body_kg, Toast.LENGTH_LONG).show();
                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                }*/


            }
        });
    }

}
