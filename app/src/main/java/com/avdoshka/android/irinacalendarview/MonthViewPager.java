package com.avdoshka.android.irinacalendarview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Created by Ирина on 11.05.2016.
 * // Настройка MonthViewPager
 MonthViewPager viewPager = (MonthViewPager) findViewById(R.id.viewpager);
 MonthPagerAdapter adapter = new MonthPagerAdapter(this);
 viewPager.setAdapter(adapter);
 Calendar nowCalendar = Calendar.getInstance();
 viewPager.setCurrentItem(nowCalendar.get(Calendar.MONTH));
 */
public class MonthViewPager extends ViewPager implements MonthView.OnLeftOrRightButtonClickListener {
    public MonthViewPager(Context context) {
        super(context);
    }

    public MonthViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onLeftButtonClick() {
        setCurrentItem(getCurrentItem() - 1);
    }

    @Override
    public void onRightButtonClick() {
        setCurrentItem(getCurrentItem() + 1);
    }
}