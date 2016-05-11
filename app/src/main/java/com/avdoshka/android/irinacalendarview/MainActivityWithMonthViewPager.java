package com.avdoshka.android.irinacalendarview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.Calendar;

/**
 * Created by Ирина on 04.05.2016.
 */
public class MainActivityWithMonthViewPager extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);
        // Настройка MonthViewPager
        MonthViewPager viewPager = (MonthViewPager) findViewById(R.id.viewpager);
        MonthPagerAdapter adapter = new MonthPagerAdapter(this);
        viewPager.setAdapter(adapter);
        Calendar nowCalendar = Calendar.getInstance();
        viewPager.setCurrentItem(nowCalendar.get(Calendar.MONTH));

    }





}
