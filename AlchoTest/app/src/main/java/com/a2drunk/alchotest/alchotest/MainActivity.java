package com.a2drunk.alchotest.alchotest;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.crash.FirebaseCrash;

public class MainActivity extends AppCompatActivity {

    private AdView mAdView;

    private Button submit, Ok, Like;
    private RadioGroup radioSexGroup;
    //IvsS//DECLARATION
    private RadioGroup radioGroupSys;
    private EditText weight_text;
    //IvsS//DECLARATION
    boolean whatSys = true;

    String warning_body_kg = "Please insert your weight!";

    float BODY_WEIGHT_IN_KG = 0;
    int GENDERTYPE = 0;
    float lastDrinkHr = 0; //IN HOURS
    float drinkingPeriodHr = 0; //HOURS OF DRINKING

    float BODY_WEIGHT_IN_LB = 0;
    double GENDER = 0; //0.73 for man//ili 0.66 for women
    float BODY_WATER_CONSTANT = (float) 0;//0.58 for man//0.49 for women !!!!
    float METABOLISM_CONSTANT = (float) 0;//0.015 for man//0.017 for women !!!!
    private static final String TAG = "Debug";

    void storeGENDERTYPE() {
        //SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        //SharedPreferences.Editor editor = settings.edit();
        //editor.putInt("GENDERTYPE", GENDERTYPE);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("GENDERTYPE", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("GENDERTYPE", GENDERTYPE);        // Saving integer
        editor.apply();
        //FirebaseCrash.logcat(Log.ERROR, TAG, "Storing GENDERTYPE");
        //FirebaseCrash.log("Storing GENDERTYPE in storeGENDERTYPE");
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
        //FirebaseCrash.logcat(Log.ERROR, TAG, "Storing GENDER");
        //FirebaseCrash.log("Storing GENDER in storeGENDER");
        //ediotr.commit();
    }

    void storeBODY_WEIGHT_IN_KG() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("BODY_WEIGHT_IN_KG", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putFloat("BODY_WEIGHT_IN_KG", BODY_WEIGHT_IN_KG);        // Saving integer
        editor.apply();
        //FirebaseCrash.logcat(Log.ERROR, TAG, "Storing BODY_WEIGHT_IN_KG");
        //FirebaseCrash.log("Storing BODY_WEIGHT_IN_KG in storeBODY-WEIGHT_IN_kg");
        //ediotr.commit();
    }

    void storeWhatSys() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("whatSys", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("whatSys", whatSys);        // Saving integer
        editor.apply();
        //FirebaseCrash.logcat(Log.ERROR, TAG, "Storing whatSys");
        //FirebaseCrash.log("Storing whatSys in storeWhatSys");
        //ediotr.commit();
    }

/*
    double BODY_WEIGHT_IN_LB=250;
    double GENDER=0.73; //ili 0.66
    double lastDrinkHr=1.5; */

    //BAC%=(A*5.14/W*r)-0.015*H//A=total alchocol consumed(oz)//W=Body weight(lbs)//r=constant(0.73 or 0.66)//H=last drink
    double mainFormula1(double listOz[], double listProcent[]) {
        double pro = 0;
        for (int i = 0; i < listOz.length; i++) {
            pro += listOz[i] * listProcent[i];
        }
        //FirebaseCrash.logcat(Log.ERROR, TAG, "Inside mainFormula1");
        //FirebaseCrash.log("Inside mainFormula1");
        return (pro * 0.75) - BODY_WEIGHT_IN_LB - lastDrinkHr * 0.015;

    }

    double mainFormula2(double listOz[], double listProcent[]) {
        double pro = 1;
        for (int i = 0; i < listOz.length; i++) {
            pro += (listOz[i] * listProcent[i] / BODY_WEIGHT_IN_LB * GENDER) - 0.015 * lastDrinkHr;
        }
        //FirebaseCrash.logcat(Log.ERROR, TAG, "Inside mainFormula2");
        //FirebaseCrash.log("Inside mainFormula2");
        return (pro * 0.75) - BODY_WEIGHT_IN_LB - lastDrinkHr * 0.015;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //FirebaseCrash.logcat(Log.ERROR, TAG, "In onCreate In MainActivity");
        //FirebaseCrash.log("In onCreate In MainActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
        MobileAds.initialize(this, "ca-app-pub-3760255090560782~9342154398");
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        radioSexGroup = (RadioGroup) findViewById(R.id.radioGroup);
        //IvsS//DECLARATION
        radioGroupSys = (RadioGroup) findViewById(R.id.radioGroupSys);
        weight_text = (EditText) findViewById(R.id.weightText);

        buttonClick();
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
                AlertDialog.Builder iBuilder = new AlertDialog.Builder(MainActivity.this);
                View iView = getLayoutInflater().inflate(R.layout.info_dialog, null);
                iBuilder.setView(iView);
                final AlertDialog dialog = iBuilder.create();
                Ok = (Button) iView.findViewById(R.id.ok_button);
                Like = (Button) iView.findViewById(R.id.Like);
                Ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });

                Like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent1 =new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + MainActivity.this.getPackageName()));
                        startActivity(intent1);
                    }
                });


                dialog.show();

        }


        return true;
    }

    public void buttonClick() {
        //FirebaseCrash.logcat(Log.ERROR, TAG, "in buttonClick");
        //FirebaseCrash.log("in buttonClick");
        submit = (Button) findViewById(R.id.button);
        //need to take values!!!
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //IvsS//Setting whatSys either true or false
                int selectedIDSys = radioGroupSys.getCheckedRadioButtonId();
                if (selectedIDSys==R.id.radioButtonSys1){
                    whatSys=true;
                }else if(selectedIDSys==R.id.radioButtonSys2){
                    whatSys=false;
                }

                Log.v(TAG, "whatSys:"+String.valueOf(whatSys));


                int selectedID = radioSexGroup.getCheckedRadioButtonId();
                if (selectedID == R.id.radioButton1) {
                    //FirebaseCrash.logcat(Log.ERROR, TAG, "in if");
                    //FirebaseCrash.log("in if");
                            /*GENDER = 0.73;
                            BODY_WATER_CONSTANT = (float) 0.58;
                            METABOLISM_CONSTANT = (float) 0.015;
                            storeGENDERTYPE();*/
                    GENDER = 0.73;
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("GENDER", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putFloat("GENDER", (float) GENDER);        // Saving integer
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


                } else if (selectedID == R.id.radioButton2) {
                    //FirebaseCrash.logcat(Log.ERROR, TAG, "in else if");
                    //FirebaseCrash.log("in else if");
                    GENDER = 0.66;
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("GENDER", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putFloat("GENDER", (float) GENDER);        // Saving integer
                    editor.apply();

                    BODY_WATER_CONSTANT = (float) 0.49;
                    pref = getApplicationContext().getSharedPreferences("BODY_WATER_CONSTANT", MODE_PRIVATE);
                    editor = pref.edit();
                    editor.putFloat("BODY_WATER_CONSTANT", BODY_WATER_CONSTANT);        // Saving integer
                    editor.apply();

                    METABOLISM_CONSTANT = (float) 0.017;
                    pref = getApplicationContext().getSharedPreferences("METABOLISM_CONSTANT", MODE_PRIVATE);
                    editor = pref.edit();
                    editor.putFloat("METABOLISM_CONSTANT", METABOLISM_CONSTANT);        // Saving integer
                    editor.apply();

                }


                String kg = weight_text.getText().toString();
                if (kg.isEmpty()) {
                    Toast.makeText(MainActivity.this, warning_body_kg, Toast.LENGTH_LONG).show();
                } else {
                    //IvsS//If whatSys is true nvm, if false convert to kg
                    if(whatSys==true){
                        BODY_WEIGHT_IN_KG = Float.parseFloat(kg);
                    }else if(whatSys==false){
                        BODY_WEIGHT_IN_KG = Float.parseFloat(kg)/(float)2.20462;
                    }
                    // if(BODY_WEIGHT_IN_KG > 0) {
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("BODY_WEIGHT_IN_KG", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putFloat("BODY_WEIGHT_IN_KG", BODY_WEIGHT_IN_KG);        // Saving integer
                    editor.apply();

                    SharedPreferences preff = getApplicationContext().getSharedPreferences("whatSys", MODE_PRIVATE);
                    SharedPreferences.Editor editorr = preff.edit();
                    editorr.putBoolean("whatSys", whatSys);        // Saving integer
                    editorr.apply();

                    Log.v(TAG, "whatSys:"+String.valueOf(BODY_WEIGHT_IN_KG));
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
