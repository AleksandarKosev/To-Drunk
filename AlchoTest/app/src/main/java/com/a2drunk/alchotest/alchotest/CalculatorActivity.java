package com.a2drunk.alchotest.alchotest;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CalculatorActivity extends AppCompatActivity  {

    private Spinner spinner1;
    private EditText count, hours;
    private ListView list;
    ArrayAdapter<String> adapter;
    private Button submit, ok;
    private TextView result;

    Dialog calcDialog;

    final ArrayList<Drink> mainListOfDrinks = new ArrayList<Drink>();

    private static final String TAG = "Debug";


    public float numSD;

    float mll;
    float procentAlcho;


    float BODY_WEIGHT_IN_KG = 0;
    int GENDERTYPE = 0;
    float lastDrinkHr = 0; //IN HOURS
    float drinkingPeriodHr = 0; //HOURS OF DRINKING

    float BODY_WEIGHT_IN_LB = 0;
    float GENDER = (float) 0; //0.73 for man//ili 0.66 for women
    float BODY_WATER_CONSTANT = (float) 0;//0.58 for man//0.49 for women
    float METABOLISM_CONSTANT = (float) 0;//0.015 for man//0.017 for women

    public float countOfSD;

    //****************************************************************************************
    //EBAC=(((0.806*SD*1.2)/(BW*Wt))-MR*DP)x10//SD=standard drinks//BW=bodtwater constant//Wt=weight in kg//DP=drinking period
    //https://en.wikipedia.org/wiki/Blood_alcohol_content
    public float calculateStandardDrinks() {
        float sumOfSD = 0;
        for (Drink temp : mainListOfDrinks) {
            sumOfSD += temp.returnStandardDrink();
        }
        //Log.v(TAG, "sumOfSD: " + sumOfSD);
        return sumOfSD;
    }

    public float mainFormula2(float sumofSD) {
        return ((((float) 0.806 * sumofSD * (float) 1.2) / (BODY_WATER_CONSTANT * BODY_WEIGHT_IN_KG)) - (METABOLISM_CONSTANT * drinkingPeriodHr)) * 10;

        //sumofSD = calculateStandardDrinks()

    }

    //*****?????********************************************************************************

    public float returnAlchocolmll(){

        return mll*procentAlcho;}

    //****************************************************************************************
    void getConstantsFromDataBase() {
        //SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        //String ret = settings.getString("statepara1", "0");
        SharedPreferences GENDERTYPEPref = getSharedPreferences("GENDERTYPE", 0);
        GENDERTYPE = GENDERTYPEPref.getInt("GENDERTYPE", 0);

        SharedPreferences BODY_WEIGHT_IN_KGPref = getSharedPreferences("BODY_WEIGHT_IN_KG", 0);
        BODY_WEIGHT_IN_KG = BODY_WEIGHT_IN_KGPref.getFloat("BODY_WEIGHT_IN_KG", 0);

        SharedPreferences BODY_WATER_CONSTPref = getSharedPreferences("BODY_WATER_CONSTANT", 0);
         BODY_WATER_CONSTANT = BODY_WATER_CONSTPref.getFloat("BODY_WATER_CONSTANT", 0);

        SharedPreferences METABOLISM_CONSTANTPref = getSharedPreferences("METABOLISM_CONSTANT", 0);
        METABOLISM_CONSTANT =METABOLISM_CONSTANTPref.getFloat("METABOLISM_CONSTANT", 0);

        SharedPreferences lastDrinkHrPref = getSharedPreferences("lastDrinkHr", 0);
        lastDrinkHr = lastDrinkHrPref.getFloat("lastDrinkHr", 0);

        SharedPreferences drinkingPeriodHrPref = getSharedPreferences("drinkingPeriodHr", 0);
        drinkingPeriodHr = drinkingPeriodHrPref.getFloat("drinkingPeriodHr", 0);

    }

    //****************************************************************************************


    void setConstans() {

        BODY_WEIGHT_IN_LB = BODY_WEIGHT_IN_KG * (float) 2.20462;
        if (GENDERTYPE == 1) {
            GENDER = (float) 0.73;
            BODY_WATER_CONSTANT = (float) 0.58;
            METABOLISM_CONSTANT = (float) 0.015;
        } else {
            GENDER = (float) 0.66;
            BODY_WATER_CONSTANT = (float) 0.49;
            METABOLISM_CONSTANT = (float) 0.017;
        }
    }



    List getAll(){
        List<String> allDr=new ArrayList<String>();
        for (Drink temp : mainListOfDrinks) {
            allDr.add(temp.description);
        }
        return allDr;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        spinner1 = (Spinner) findViewById(R.id.spinner1);
        count = (EditText) findViewById(R.id.editText2);
        list = (ListView) findViewById(R.id.list1);


        getConstantsFromDataBase();
        setConstans();




        //final  String[] text =  new String[] {(String) spinner1.getSelectedItem(), String.valueOf(count.getText())};
        final  String[] text =  new String[] {};
        final List<String> elements = new ArrayList<String>(Arrays.asList(text));
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, elements);
        list.setAdapter(adapter);





        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              /*  final  String[] text =  new String[] {};
                final List<String> elements = new ArrayList<String>(Arrays.asList(text));
                adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, elements);
                list.setAdapter(adapter);*/


               // Snackbar.make(view, "Drink added to list!", Snackbar.LENGTH_LONG)
                 //       .setAction("Action", null).show();
                spinner1.getSelectedItem().toString();
                if(spinner1.getSelectedItem().toString().equals("Beer 330ml"))
                {
                    //Drink tmp = new Drink("Beer 330ml", 330, 4);
                    mainListOfDrinks.add(new Drink("Beer 330ml", 330, 4));

                }
                else if (spinner1.getSelectedItem().toString().equals("Beer 500ml"))
                {
                    mainListOfDrinks.add(new Drink("Beer 500ml", 500, 4));
                }
                else if (spinner1.getSelectedItem().toString().equals("Wine 187ml"))
                {
                    mainListOfDrinks.add(new Drink("Wine 187ml", 187, 12));
                }
                else if (spinner1.getSelectedItem().toString().equals("Whisky 40ml"))
                {
                    mainListOfDrinks.add(new Drink("Whisky 40ml", 40, 40));
                }
                else if (spinner1.getSelectedItem().toString().equals("Vodka 40ml"))
                {
                    mainListOfDrinks.add(new Drink("Vodka 40ml", 40, 40));
                }
                else
                {
                    mainListOfDrinks.add(new Drink("Tequila 20ml", 20, 40));
                }

                elements.add((String) spinner1.getSelectedItem());
                adapter.notifyDataSetChanged();

            }
        });

        submit = (Button) findViewById(R.id.button2);
         submit.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 AlertDialog.Builder mBuilder = new AlertDialog.Builder(CalculatorActivity.this);
                 View mView = getLayoutInflater().inflate(R.layout.dialog_calc, null);
                  hours = (EditText) mView.findViewById(R.id.Hours);
                 result = (TextView) mView.findViewById(R.id.result);
                 ok = (Button) mView.findViewById(R.id.ok);
                 ok.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {

                         if(!hours.getText().toString().isEmpty())
                         {
                             drinkingPeriodHr = Float.valueOf(hours.getText().toString());
                             countOfSD = calculateStandardDrinks();
                             float percent = mainFormula2(countOfSD);
                             result.setText(String.format("%.3f", percent));

                         }
                     }
                 });
                 mBuilder.setView(mView);
                 AlertDialog dialog = mBuilder.create();
                 dialog.show();
             }
         });



    }

}
