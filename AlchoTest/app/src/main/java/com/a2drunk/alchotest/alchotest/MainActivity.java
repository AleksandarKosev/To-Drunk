package com.a2drunk.alchotest.alchotest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    double BODY_WEIGHT_IN_LB=250;
    double GENDER=0.73; //ili 0.66
    double lastDrinkHr=1.5;

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
    }
}
