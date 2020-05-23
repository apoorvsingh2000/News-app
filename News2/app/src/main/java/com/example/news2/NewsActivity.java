package com.example.news2;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class NewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //view pager
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);

        //set up a pager adapter
        PagerAdapter pagerAdapter = new CategoryAdapter(getSupportFragmentManager(), 0, this);

        //setting the pager adapter
        viewPager.setAdapter(pagerAdapter);

        // Find the tab layout that shows the tabs
        TabLayout tablayout = (TabLayout) findViewById(R.id.tab_layout);

        // Connect the tab layout with the view pager
        tablayout.setupWithViewPager(viewPager);
    }
}
