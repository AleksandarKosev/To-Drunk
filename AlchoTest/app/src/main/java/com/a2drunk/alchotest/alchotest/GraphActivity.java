package com.a2drunk.alchotest.alchotest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.*;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.a2drunk.alchotest.alchotest.CalculatorActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

/**
 * Created by Stefan on 06.11.2017.
 */

public class GraphActivity extends AppCompatActivity {

    //LineGraphSeries<DataPoint> series;

    private AdView mAdView2;

    BarGraphSeries<DataPoint> series;
    private TextView graphresult;
    private Button backButton;
    float drinkingPeriodHr = 0;
    float  percent = 0;


    float BODY_WEIGHT_IN_KG = 0;
    boolean whatSys = true;
    float countOfSD;
    int GENDERTYPE = 0; //no need here!!!!!
    float lastDrinkHr = 0; //IN HOURS
    float BODY_WEIGHT_IN_LB = 0;
    float GENDER = (float) 0; //0.73 for man//ili 0.66 for women
    float BODY_WATER_CONSTANT = (float) 0;//0.58 for man//0.49 for women
    float METABOLISM_CONSTANT = (float) 0;//0.015 for man//0.017 for women




    void getConstantsFromDataBase(){
        SharedPreferences drinkingPeriodHrPref = getSharedPreferences("drinkingPeriodHr", 0);
        drinkingPeriodHr = drinkingPeriodHrPref.getFloat("drinkingPeriodHr", 0);

        SharedPreferences percentPref = getSharedPreferences("percent", 0);
        percent = percentPref.getFloat("percent", 0);

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

        SharedPreferences countOfSDPref = getSharedPreferences("countOfSD", 0);
        countOfSD = countOfSDPref.getFloat("countOfSD", 0);

    }


    public float mainFormula2(float sumofSD) {

        return ((((float) 0.806 * sumofSD * (float) 1.2) / (BODY_WATER_CONSTANT * BODY_WEIGHT_IN_KG)) - (METABOLISM_CONSTANT * drinkingPeriodHr)) * 10;


    }


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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_activity);


        // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
        MobileAds.initialize(this, "ca-app-pub-3760255090560782~9342154398");
        mAdView2 = (AdView) findViewById(R.id.adViewGraph);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView2.loadAd(adRequest);

        getConstantsFromDataBase();
        setConstans();

        percent = mainFormula2(countOfSD);

        GraphView graph = (GraphView) findViewById(R.id.graph);
        graphresult = (TextView) findViewById(R.id.graph_result);
        backButton=(Button)findViewById(R.id.backButton);
         series = new BarGraphSeries<DataPoint>();

            for (int i = 0; i <200; i++) {
            if(percent <=  0.00)
            {
                graphresult.setText("Your BAC will be 0 ‰ in about " + drinkingPeriodHr + " hours!");
                break;
            }

            else {
                //String a = String.format("%.3f", percent);
                //float p = Float.valueOf(a);
                series.appendData(new DataPoint(drinkingPeriodHr, Float.valueOf(String.format("%.2f", percent))), true, 200);
                drinkingPeriodHr = (float) (drinkingPeriodHr + 0.5);
                percent = mainFormula2(countOfSD);


            }
        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        series.setSpacing(60); //30

        series.setTitle("Permilles of alchocol");
        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.BLACK);
        //series.setValuesOnTopSize(35);
        series.setColor(Color.BLUE);
        /*series.setDrawDataPoints(true);
        series.setDataPointsRadius(9);      For LineGraphSeries!
        series.setThickness(8);*/


//        graph.getViewport().setScrollable(true); // enables horizontal scrolling
//        graph.getViewport().setScrollableY(false);
//        graph.getViewport().setScalable(false);
//        graph.getViewport().setScalableY(false);

        graph.getViewport().setScrollable(true);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(10);
        graph.getViewport().setMinY(0);

        graph.getViewport().setMaxY((int) percent + 1);

        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        graph.addSeries(series);


    }
}
