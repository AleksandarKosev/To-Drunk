package com.a2drunk.alchotest.alchotest;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.crash.FirebaseCrash;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.security.AccessController.getContext;

public class CalculatorActivity extends AppCompatActivity {

    private InterstitialAd mInterstitialAd;


    LineGraphSeries<DataPoint> series;


    private Spinner spinner1;
    private EditText count, hours;
    private ListView list;
    ArrayAdapter<String> adapter;
    private Button submit, ok, okay, Like, remove, chartview, close;
    private TextView result;

    Dialog calcDialog;


    void storedrinkingPeriodHr() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("drinkingPeriodHr", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putFloat("drinkingPeriodHr", drinkingPeriodHr);        // Saving integer
        editor.apply();
        //FirebaseCrash.logcat(Log.ERROR, TAG, "Storing whatSys");
        //FirebaseCrash.log("Storing whatSys in storeWhatSys");
        //ediotr.commit();
    }



    final ArrayList<Drink> mainListOfDrinks = new ArrayList<Drink>();

    String[] drinksMetric = {"Beer 330ml, 330, 4", "Beer 500ml, 500, 4", "Wine 187ml, 187, 12", "Whisky 40ml, 40, 40", "Vodka 40ml, 40, 40", "Tequila 20ml, 20, 40", "Cognac 40ml, 40, 40", "Gin Tonic 250ml, 250, 19","Ouzo 40ml, 40, 40"};
    List<String> drinksMetricSpinner = Arrays.asList("Beer 330ml", "Beer 500ml", "Wine 187ml", "Whisky 40ml", "Vodka 40ml", "Tequila 20ml", "Cognac 40ml","Gin Tonic 250ml","Ouzo 40ml");

    String[] drinksImperial = {"Beer 12oz, 340, 4", "Beer 17.6oz, 500, 4", "Wine 6.6oz, 187, 12", "Wisky 1.4oz, 40, 40", "Vodka 1.4oz, 40, 40", "Tequila 0.7oz, 20, 40","Cognac 1.4oz, 40, 40","Gin Tonic 8.8oz, 250, 19","Ouzo 1.4oz, 40, 40"};
    List<String> drinksImperialSpinner = Arrays.asList("Beer 12oz", "Beer 17.6oz", "Wine 6.6oz", "Wisky 1.4oz", "Vodka 1.4oz", "Tequila 0.7oz","Cognac 1.4oz","Gin Tonic 8.8oz","Ouzo 1.4oz");

    private static final String TAG = "Debug";


    public float numSD;

    float mll;
    float procentAlcho;


    float BODY_WEIGHT_IN_KG = 0;
    boolean whatSys = true;
    int GENDERTYPE = 0;
    float lastDrinkHr = 0; //IN HOURS
    float drinkingPeriodHr = 0; //HOURS OF DRINKING
    float percent;
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
        //FirebaseCrash.logcat(Log.ERROR, TAG, "in calcualteStandardDrink");
        //FirebaseCrash.log("in calcualteStandardDrinks");
        return sumOfSD;
    }

    public float mainFormula2(float sumofSD) {
        //FirebaseCrash.logcat(Log.ERROR, TAG, "in mainFormula2");
        //FirebaseCrash.log("in mainFormula2");
        return ((((float) 0.806 * sumofSD * (float) 1.2) / (BODY_WATER_CONSTANT * BODY_WEIGHT_IN_KG)) - (METABOLISM_CONSTANT * drinkingPeriodHr)) * 10;

        //sumofSD = calculateStandardDrinks()

    }

    //*****?????********************************************************************************

    public float returnAlchocolmll() {

        return mll * procentAlcho;
    }

    //****************************************************************************************
    void getConstantsFromDataBase() {
        //SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        //String ret = settings.getString("statepara1", "0");
        SharedPreferences GENDERTYPEPref = getSharedPreferences("GENDERTYPE", 0);
        GENDERTYPE = GENDERTYPEPref.getInt("GENDERTYPE", 0);

        SharedPreferences BODY_WEIGHT_IN_KGPref = getSharedPreferences("BODY_WEIGHT_IN_KG", 0);
        BODY_WEIGHT_IN_KG = BODY_WEIGHT_IN_KGPref.getFloat("BODY_WEIGHT_IN_KG", 0);

        SharedPreferences whatSysPref = getSharedPreferences("whatSys", 0);
        whatSys = whatSysPref.getBoolean("whatSys", true);

        SharedPreferences BODY_WATER_CONSTPref = getSharedPreferences("BODY_WATER_CONSTANT", 0);
        BODY_WATER_CONSTANT = BODY_WATER_CONSTPref.getFloat("BODY_WATER_CONSTANT", 0);

        SharedPreferences METABOLISM_CONSTANTPref = getSharedPreferences("METABOLISM_CONSTANT", 0);
        METABOLISM_CONSTANT = METABOLISM_CONSTANTPref.getFloat("METABOLISM_CONSTANT", 0);

        SharedPreferences lastDrinkHrPref = getSharedPreferences("lastDrinkHr", 0);
        lastDrinkHr = lastDrinkHrPref.getFloat("lastDrinkHr", 0);

        SharedPreferences drinkingPeriodHrPref = getSharedPreferences("drinkingPeriodHr", 0);
        drinkingPeriodHr = drinkingPeriodHrPref.getFloat("drinkingPeriodHr", 0);

    }

    //****************************************************************************************


    void setConstans() {
        //FirebaseCrash.logcat(Log.ERROR, TAG, "setConstants");
        //FirebaseCrash.log("setConstants");

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


    List getAll() {
        List<String> allDr = new ArrayList<String>();
        for (Drink temp : mainListOfDrinks) {
            allDr.add(temp.description);
        }
        return allDr;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //FirebaseCrash.logcat(Log.ERROR, TAG, "in onCreate in CalcualatorAcitivty");
        //FirebaseCrash.log("in onCreate in CalculatorActivity");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);


        remove = (Button) findViewById(R.id.removebtn);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3760255090560782/2152438824");//TEST: ca-app-pub-3940256099942544/1033173712
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getConstantsFromDataBase();
        setConstans();

        //FirebaseCrash.logcat(Log.ERROR, TAG, "setting the text variable");
        //FirebaseCrash.log("setting the text variable");

        spinner1 = (Spinner) findViewById(R.id.spinner1);
        list = (ListView) findViewById(R.id.list1);
        final String[] text = new String[]{};
        final List<String> elements = new ArrayList<String>(Arrays.asList(text));
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, elements);
        list.setAdapter(adapter);


        //Log.v(TAG, "whatSysNewActv:" + String.valueOf(BODY_WEIGHT_IN_KG));
        //Log.v(TAG, "whatSysNewActv:" + String.valueOf(whatSys));


        if (whatSys == true) {
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, drinksMetricSpinner);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner1.setAdapter(dataAdapter);
        } else {
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, drinksImperialSpinner);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner1.setAdapter(dataAdapter);
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (whatSys == true) {
                    String[] meh;
                    for (String temp : drinksMetric) {
                        meh = temp.split(", ");
                        if (meh[0].equals(spinner1.getSelectedItem().toString())) {
                            mainListOfDrinks.add(new Drink(meh[0], Float.parseFloat(meh[1]), Float.parseFloat(meh[2])));
                            //Log.v(TAG, String.valueOf(meh[0]) + String.valueOf(meh[1]) + String.valueOf(meh[2]));

                        }
                    }
                } else {
                    ////Log.v(TAG, String.valueOf("Entered"));
                    String[] meh;
                    for (String temp : drinksImperial) {
                        meh = temp.split(", ");
                        if (meh[0].equals(spinner1.getSelectedItem().toString())) {
                            mainListOfDrinks.add(new Drink(meh[0], Float.parseFloat(meh[1]), Float.parseFloat(meh[2])));
                            //Log.v(TAG, String.valueOf(meh[0]) + String.valueOf(meh[1]) + String.valueOf(meh[2]));
                        }
                    }
                }
                remove.setClickable(true);

                elements.add((String) spinner1.getSelectedItem());
                adapter.notifyDataSetChanged();
            }
        });

        submit = (Button) findViewById(R.id.button2);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //FirebaseCrash.logcat(Log.ERROR, TAG, "in submit");
                //FirebaseCrash.log("in submit");
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(CalculatorActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_calc, null);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();

                chartview = (Button) mView.findViewById(R.id.chartview);
                close = (Button) mView.findViewById(R.id.close_btn);
                hours = (EditText) mView.findViewById(R.id.Hours);
                result = (TextView) mView.findViewById(R.id.result);
                chartview.setClickable(false);
                ok = (Button) mView.findViewById(R.id.ok);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (!hours.getText().toString().isEmpty()) {

                            //chartview.setClickable(true);

                            if (mInterstitialAd.isLoaded()) {
                                mInterstitialAd.show();
                            }

                            drinkingPeriodHr = Float.valueOf(hours.getText().toString());

                            SharedPreferences pref = getApplicationContext().getSharedPreferences("drinkingPeriodHr", MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putFloat("drinkingPeriodHr", drinkingPeriodHr);        // Saving float drinkingHr
                            editor.apply();


                            countOfSD = calculateStandardDrinks();
                            //This is added later
                            SharedPreferences pref1 = getApplicationContext().getSharedPreferences("countOfSD", MODE_PRIVATE);
                            SharedPreferences.Editor editor1 = pref1.edit();
                            editor1.putFloat("countOfSD", countOfSD);        // Saving float countOfSD
                            editor1.apply();

                            percent = mainFormula2(countOfSD);
                            //This is added later
                            SharedPreferences pref2 = getApplicationContext().getSharedPreferences("percent", MODE_PRIVATE);
                            SharedPreferences.Editor editor2 =pref2.edit();
                            editor2.putFloat("percent", percent);
                            editor2.apply();



                            if (percent <= 0) {
                                result.setText("0.00  permille (‰)");
                            } else {
                                result.setText(String.format("%.3f", percent) + "  permille (‰)");
                            }

                            chartview.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent openChart = new Intent(CalculatorActivity.this, GraphActivity.class);
                                    startActivity(openChart);

                                }
                            });

                        }

                        else if(hours.getText().toString().isEmpty())
                        {
                            chartview.setClickable(false);
                        }
                    }
                });

               // chartview.setClickable(false);


                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog.cancel();
                    }
                });




                //mBuilder.setView(mView);
                //AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });


        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for(int i =mainListOfDrinks.size()-1; i>= 0; i--)
                {
                    Log.v(TAG, String.valueOf("deleting element:"+String.valueOf(i)));
                    mainListOfDrinks.remove(i);
                    Log.v(TAG,"size of mainLIstOfDrinks is:"+String.valueOf(mainListOfDrinks.size()));
                }
                //list.setAdapter(null);
                //adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, elements);
                //list.setAdapter(adapter);
                adapter.clear();
                list.setAdapter(adapter);
                remove.setClickable(false);
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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

        switch (item.getItemId()) {
            case R.id.info:
                AlertDialog.Builder iBuilder = new AlertDialog.Builder(CalculatorActivity.this);
                View iView = getLayoutInflater().inflate(R.layout.info_dialog, null);
                iBuilder.setView(iView);
                final AlertDialog dialog = iBuilder.create();
                okay = (Button) iView.findViewById(R.id.ok_button);
                Like = (Button) iView.findViewById(R.id.Like);
                okay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });

                Like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent1 =new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + CalculatorActivity.this.getPackageName()));
                        startActivity(intent1);
                    }
                });


                dialog.show();

        }


        return true;
    }

}
