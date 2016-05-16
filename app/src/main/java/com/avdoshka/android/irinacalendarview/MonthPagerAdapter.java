package com.avdoshka.android.irinacalendarview;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Ирина on 11.05.2016.
 */
public class MonthPagerAdapter extends PagerAdapter {

    private Context mContext;

    public MonthPagerAdapter(Context context) {
        super();
        mContext = context;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        MonthView monthView = new MonthView(mContext);
        int currentMonthNumber = position % 12;
        int currentYearNumber = position / 12;
        monthView.setYear(monthView.getYear() + currentYearNumber);
        monthView.setMonthNumber(++currentMonthNumber);
        monthView.initViewGroup();
        collection.addView(monthView);
        return monthView;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


}
