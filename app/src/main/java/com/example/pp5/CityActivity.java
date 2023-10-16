package com.example.pp5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class CityActivity extends AppCompatActivity {

    TextView txt1,txt2,txt3,txt4;
    Spinner spinner;
    Button button;

    String[] cities = {"City","Gliwice","Zabrze","Bytom"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        txt1 = findViewById(R.id.txtView1);
        txt2 = findViewById(R.id.txtView2);
        txt3 = findViewById(R.id.txtView3);
        txt4 = findViewById(R.id.txtView4);
        spinner = findViewById(R.id.spinner);
        button = findViewById(R.id.btn);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(CityActivity.this,R.layout.list_item,cities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("City")){
                    button.setVisibility(View.GONE);
                }else {
                    button.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CityActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });



    }


}