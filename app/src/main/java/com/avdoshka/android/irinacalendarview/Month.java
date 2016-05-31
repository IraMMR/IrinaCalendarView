package com.avdoshka.android.irinacalendarview;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

/**
 * Created by Ирина on 04.05.2016.
 */
public class Month {
    private int [] monthArr = new int [42];//6 lines with 7 days {-1, -1, 1, 2, ... 29, 30, -1, -1, .. -1}
    private int monthNumber; // January -> 1
    private int year;


    public Month(int monthNumber, int year) {
        this.monthNumber = monthNumber;
        this.year = year;
        initMonthArray();
    }

    private void initMonthArray() {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.MONTH, monthNumber - 1);
        calendar.set(Calendar.YEAR, year);
        int thisMonthMax = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        //На какой день недели приходится первое число (Воскресенье = 1 ...)
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int firstMonthDayDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        //calculate first day of month in array
        int startMonthIndex;
        if (firstMonthDayDayOfWeek != Calendar.SUNDAY)
            startMonthIndex = firstMonthDayDayOfWeek - 2; // Пн = 2. Начинаем с 0 индекса
        else
            startMonthIndex = firstMonthDayDayOfWeek + 5;

        calendar.add(Calendar.MONTH, -1);
        int previousMonthMaximum = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        fillMonthArray(startMonthIndex, thisMonthMax, previousMonthMaximum);
    }

    private void fillMonthArray(int startMonthIndex, int thisMonthMax, int previousMonthMaximum) {
        // fill dates of the current month
        int j = 1;
        for (int i = startMonthIndex; j < thisMonthMax + 1; i++) {
            monthArr[i] = j++;
        }

        // fill dates of the next month
        //j = 1;
        for (int i = startMonthIndex + thisMonthMax; i < 42; i++) {
            monthArr[i] = -1;//j++;
        }

        // fill dates of the previous month
        //j = previousMonthMaximum;
        for (int i = startMonthIndex - 1; i > -1; i--) {
            monthArr[i] = -1;//j--;
        }
    }

    public int[] getMonthArr() {
        return monthArr;
    }

    public String getMonthLabel() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("LLLL yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.MONTH, monthNumber - 1);
        calendar.set(Calendar.YEAR, year);
        return simpleDateFormat.format(calendar.getTimeInMillis());
    }

    public static void main(String[] args) {
        for (int i = 1; i < 13; i++) {
            Month month = new Month(i, 2016);
            System.out.println("**********Month-------" + i + "  ***************");
            System.out.println(Arrays.toString(month.getMonthArr()));
            System.out.println();
        }
        for (int i = 0; i < 12; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.clear();
            calendar.set(Calendar.MONTH, i);
            calendar.set(Calendar.YEAR, 2016);
            System.out.println(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

        }

    }
}
