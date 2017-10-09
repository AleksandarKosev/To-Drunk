package com.a2drunk.alchotest.alchotest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    private Button submit;
    private RadioGroup radioSexGroup;
    private EditText weight_text;

    String warning_body_kg = "Please insert your weight!";

    float BODY_WEIGHT_IN_KG = 0;
    int GENDERTYPE = 0;
    float lastDrinkHr = 0; //IN HOURS
    float drinkingPeriodHr = 0; //HOURS OF DRINKING

    float BODY_WEIGHT_IN_LB = 0;
    double GENDER =  0; //0.73 for man//ili 0.66 for women
    float BODY_WATER_CONSTANT = (float) 0;//0.58 for man//0.49 for women
    float METABOLISM_CONSTANT = (float) 0;//0.015 for man//0.017 for women


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



    void storeBODY_WEIGHT_IN_KG() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("BODY_WEIGHT_IN_KG", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putFloat("BODY_WEIGHT_IN_KG", BODY_WEIGHT_IN_KG);        // Saving integer
        editor.apply();
        //ediotr.commit();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        radioSexGroup = (RadioGroup) findViewById(R.id.radioGroup);
        weight_text = (EditText) findViewById(R.id.weightText);

        buttonClick();
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
                            GENDERTYPE = 1;
                            SharedPreferences pref = getApplicationContext().getSharedPreferences("GENDERTYPE", MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putInt("GENDERTYPE", GENDERTYPE);        // Saving integer
                            editor.apply();

                        }

                       else if(selectedID == R.id.radioButton2)
                        {
                            GENDERTYPE = 2;
                            SharedPreferences pref = getApplicationContext().getSharedPreferences("GENDERTYPE", MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putInt("GENDERTYPE", GENDERTYPE);        // Saving integer
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
