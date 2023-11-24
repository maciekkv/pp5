package com.example.pp5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {
    TextView textView1,textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_about);
        textView1 = findViewById(R.id.txtView1);
        textView2 = findViewById(R.id.txtView2);
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}