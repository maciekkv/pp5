package com.example.pp5.adapters;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.pp5.R;

public class ViewPagerAdapter extends PagerAdapter {

    Context context;
    int images[] = {
            R.drawable.onboarding3,
            R.drawable.onboarding1,
            R.drawable.onboarding5,
            R.drawable.onbarding2,
            R.drawable.onboarding4
    };

    int titles[] = {
            R.string.title1,
            R.string.title2,
            R.string.title3,
            R.string.title4,
            R.string.title5
    };

    int describtions[] = {
            R.string.desc1,
            R.string.desc2,
            R.string.desc3,
            R.string.desc4,
            R.string.desc5
    };

    public ViewPagerAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (LinearLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slider_layout,container,false);

        ImageView imageView = (ImageView) view.findViewById(R.id.onboardingImg);
        TextView textTitle = (TextView) view.findViewById(R.id.txtView1);
        TextView textDesc = (TextView) view.findViewById(R.id.txtView2);

        imageView.setImageResource(images[position]);
        textTitle.setText(titles[position]);
        textDesc.setText(describtions[position]);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}
