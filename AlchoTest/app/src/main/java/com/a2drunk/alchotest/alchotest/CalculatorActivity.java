package com.a2drunk.alchotest.alchotest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CalculatorActivity extends AppCompatActivity {

    private Spinner spinner1;
    private EditText count;
    private ListView list;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        spinner1 = (Spinner) findViewById(R.id.spinner1);
        count = (EditText) findViewById(R.id.editText2);
        list = (ListView) findViewById(R.id.list1);


        final String[] text =  new String[] {(String) spinner1.getSelectedItem(), String.valueOf(count.getText())};
        final List<String> elements = new ArrayList<String>(Arrays.asList(text));
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, elements);
        list.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                elements.add((String) spinner1.getSelectedItem());
                adapter.notifyDataSetChanged();

            }
        });
    }

}
