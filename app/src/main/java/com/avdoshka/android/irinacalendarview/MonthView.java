package com.avdoshka.android.irinacalendarview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by Ирина on 04.05.2016.
 */
public class MonthView extends TableLayout implements View.OnClickListener{
    private static final float INITIAL_TEXT_SIZE = 45f;
    private static final String TEXT_TO_MEASURE = "22";
    private static final float VALID_TEXT_WIDTH_PERCENTAGE = 0.8f;
    private int monthNumber; //January -> 1
    private int year;
   // private OnLeftOrRightButtonClickListener onLeftOrRightButtonClickListener;

    {
        Calendar calendar = Calendar.getInstance();
        monthNumber = calendar.get(Calendar.MONTH ) + 1;
        year = calendar.get(Calendar.YEAR);
    }

    public interface OnLeftOrRightButtonClickListener {
        void onLeftButtonClick();
        void onRightButtonClick();
    }

    public MonthView(Context context) {
        this(context, null);

    }

    public MonthView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MonthView, 0, 0);
            try {
                monthNumber = a.getInteger(R.styleable.MonthView_month, monthNumber);
                year = a.getInteger(R.styleable.MonthView_year, year);
            } finally {
                a.recycle();
            }
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layoutInflater.inflate(R.layout.month_view_layout, this, true);
            initViewGroup();
        }
    }

    void initViewGroup() {
        final TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayout);
        final TextView view = (TextView) ((TableRow)tableLayout.getChildAt(4)).getChildAt(3);

        // Расчет ширины, высоты элемента и размера шрифта
        view.post(new Runnable() {
            @Override
            public void run() {
                int width = view.getWidth();
                int height = view.getHeight();
                //Log.d("TAG", "view.width: " + width + "view.height: " + height);
                Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.LINEAR_TEXT_FLAG);
                //paint.setStyle(Paint.Style.FILL);
                //paint.setTextAlign(Paint.Align.CENTER);
                paint.setTextSize(INITIAL_TEXT_SIZE);
                Rect bounds = new Rect();
                paint.getTextBounds(TEXT_TO_MEASURE, 0, TEXT_TO_MEASURE.length(), bounds);
                int textHeight = bounds.height();
                int textWidth = bounds.width();
                float validWidthCoeff = VALID_TEXT_WIDTH_PERCENTAGE * width / textWidth;
                int desiredTextHeight = height >> 1 ;
                float desiredHeightCoeff =  ((float)desiredTextHeight) / textHeight;
                float coeff = Math.min(validWidthCoeff, desiredHeightCoeff);
                float textSizePX = (int)(INITIAL_TEXT_SIZE * coeff);
                setSizesAndText(tableLayout, width, height, textSizePX);
            }
        });



    }

    // Проставляет рассчитанные ширину, высоту, размер шрифта для всех элементов таблицы
    private void setSizesAndText(TableLayout tableLayout, int width, int height, float textSizePX) {
        Month month = new Month(monthNumber, year);
        FrameLayout frameLayout = (FrameLayout) tableLayout.getChildAt(0);
        frameLayout.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, height));

        TextView textView = (TextView) findViewById(R.id.month_label_text_view);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSizePX);
        textView.setText(month.getMonthLabel());
        FrameLayout.LayoutParams monthLabelLP = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, height);
        monthLabelLP.gravity = Gravity.CENTER;
        textView.setLayoutParams(monthLabelLP);

        //Настройка кнопок
        FrameLayout.LayoutParams buttonLayoutParams = new FrameLayout.LayoutParams(height, height);
        Button button = (Button) findViewById(R.id.button_previous_month);
        button.setLayoutParams(buttonLayoutParams);
        button.setOnClickListener(this);

        buttonLayoutParams = new FrameLayout.LayoutParams(height, height);
        buttonLayoutParams.gravity = Gravity.END;
        button = (Button) findViewById(R.id.button_next_month);
        button.setLayoutParams(buttonLayoutParams);
        button.setOnClickListener(this);

        TableRow tableRow = (TableRow) tableLayout.getChildAt(1);
        String [] dayNames = {"Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс"};
        for (int i = 0; i < tableRow.getChildCount(); i++) {
            textView = (TextView)tableRow.getChildAt(i);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSizePX * 0.7f );
            textView.setLayoutParams(new TableRow.LayoutParams(width, height));
            textView.setText(dayNames[i]);
        }


        int [] daysArr = month.getMonthArr();
        int index = 0;

        for (int i = 2; i < tableLayout.getChildCount(); i++) {
            tableRow = (TableRow) tableLayout.getChildAt(i);
            for (int j = 0; j < tableRow.getChildCount(); j++) {
                textView = (TextView)tableRow.getChildAt(j);
                int day = daysArr[index++];

                    textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSizePX);
                    TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(width, height);
                    textView.setLayoutParams(layoutParams);

                if (day != -1) {
                    textView.setText(String.valueOf(day));
                }
                else
                    textView.setText("");
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_previous_month:
            /*if (monthNumber > 1)
                    monthNumber--;
                else {
                    monthNumber =  12;
                    year--;
                }
                initViewGroup();*/
                OnLeftOrRightButtonClickListener onLeftOrRightButtonClickListener;
                try {
                    onLeftOrRightButtonClickListener = (OnLeftOrRightButtonClickListener) getParent();
                }
                catch (ClassCastException e) {
                    throw new ClassCastException("" + getParent() + " must implement OnLeftOrRightButtonClickListener");
                }
                onLeftOrRightButtonClickListener.onLeftButtonClick();
                break;
            case R.id.button_next_month:
                /*if (monthNumber < 12)
                    monthNumber++;
                else {
                    monthNumber = 1;
                    year++;
                }
                initViewGroup();*/
                try {
                    onLeftOrRightButtonClickListener = (OnLeftOrRightButtonClickListener) getParent();
                }
                catch (ClassCastException e) {
                    throw new ClassCastException("" + getParent() + " must implement OnLeftOrRightButtonClickListener");
                }
                onLeftOrRightButtonClickListener.onRightButtonClick();
                break;
        }
    }

    public void setMonthNumber(int monthNumber) {
        this.monthNumber = monthNumber;
        initViewGroup();
    }

    public void setYear(int year) {
        this.year = year;
        initViewGroup();
    }

    public int getMonthNumber() {
        return monthNumber;
    }

    public int getYear() {
        return year;
    }
}
