package com.example.pp5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pp5.adapters.ViewPagerAdapter;

public class OnboardingActivity extends AppCompatActivity {

    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    LinearLayout dotsLayout;
    Button backBtn, nextBtn, skipBtn;
    TextView[] dots;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        backBtn = findViewById(R.id.backBtn);
        nextBtn = findViewById(R.id.nextBtn);
        skipBtn = findViewById(R.id.skipBtn);
        backBtn.setVisibility(View.GONE);


        //check if onboarding already was opened
        if(restorePrefData()){
            Intent intent = new Intent(OnboardingActivity.this,CityActivity.class);
            startActivity(intent);
            finish();
        }

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getitem(0)>0){
                    viewPager.setCurrentItem(getitem(-1),true);
                }
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getitem(0)<4){
                    viewPager.setCurrentItem(getitem(1),true);
                }else {
                    Intent intent = new Intent(OnboardingActivity.this,CityActivity.class);
                    startActivity(intent);

                    //finish();
                    saveSharedPrefs();
                    finish();
                }
            }
        });

        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OnboardingActivity.this,CityActivity.class);
                startActivity(intent);
                finish();
            }
        });

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        dotsLayout = (LinearLayout) findViewById(R.id.dotsLayout);

        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);
        setDots(0);
        viewPager.addOnPageChangeListener(viewListener);
    }

    private boolean restorePrefData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("appPrefs",MODE_PRIVATE);
        Boolean isOnboardingOpenedBefore = pref.getBoolean("isOnboardingOpened",false);
        return isOnboardingOpenedBefore;
    }

    private void saveSharedPrefs() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("appPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isOnboardingOpened",true);
        editor.commit();
    }

    public void setDots(int position){
        dots = new TextView[5];
        dotsLayout.removeAllViews();

        for(int i=0;i<dots.length;i++){
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.sky,getApplicationContext().getTheme()));
            dotsLayout.addView(dots[i]);
        }
        dots[position].setTextColor(getResources().getColor(R.color.blue,getApplicationContext().getTheme()));
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            setDots(position);

            if(position > 0){
                backBtn.setVisibility(View.VISIBLE);
            }else {
                backBtn.setVisibility(View.GONE);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private int getitem(int i){
        return viewPager.getCurrentItem() + i;
    }
}