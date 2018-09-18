package com.ramzcalender.sample;

import android.app.Activity;
import android.os.Bundle;

import com.android.datetimepicker.date.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.datetimepicker.date.DatePickerDialog;
import com.ramzcalender.RWeekCalendar;
import com.ramzcalender.listener.CalenderListener;
import com.ramzcalender.utils.CalUtil;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;

import java.util.Calendar;

import static com.ramzcalender.sample.RecyclerAdapter.cont_nam;
/**
 * Created by edison office on 7/17/2018.
 */
public class Calender_MainPage extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    RWeekCalendar rCalendarFragment;
    TextView mDateSelectedTv;
    FloatingActionButton fb;
    DataBaseHandler db = null;
    Button task,lead,conta,deal,calendr,cancel,plus,cal_back;
    BottomSheetDialog dialog;
    CoordinatorLayout coordinatorLayout;
    GridView gd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calender_mainpage);
     //   db = new DataBaseHandler(Calender_MainPage.this);
     //   db.getWritableDatabase();


        rCalendarFragment = new RWeekCalendar();

        /*Define you startDate and end Date*/
        rCalendarFragment.startDate(1989, 9, 1);//Start date
        rCalendarFragment.endDate(2050, 12, 31);//Ending date

        Bundle args = new Bundle();

       /*Should add this attribute if you adding  the NOW_BACKGROUND or DATE_SELECTOR_BACKGROUND Attribute*/
        args.putString(RWeekCalendar.PACKAGENAME, getApplicationContext().getPackageName());
        args.putInt(RWeekCalendar.CALENDER_TYPE, RWeekCalendar.NORMAL_CALENDER);
        args.putString(RWeekCalendar.DATE_SELECTOR_BACKGROUND, "bg_select");//set background to the selected dates

        rCalendarFragment.setArguments(args);

        // Attach to the activity
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.container, rCalendarFragment);
        t.commit();



        CalenderListener listener = new CalenderListener() {
            @Override
            public void onSelectPicker() {

                //User can use any type of pickers here the below picker is only Just a example

                DatePickerDialog.newInstance(Calender_MainPage.this, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).show(getFragmentManager(), "datePicker");


            }

            @Override
            public void onSelectDate(LocalDateTime mSelectedDate) {

                //callback when a date is selcted

                //  mDateSelectedTv.setText("" + mSelectedDate.getDayOfMonth() + "-" + mSelectedDate.getMonthOfYear() + "-" + mSelectedDate.getYear());
            }
        };

        //setting the listener
        rCalendarFragment.setCalenderListener(listener);

    }

    @Override
    public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
        //IMPORTANT: get the year,month and date from picker you using and call setDateWeek method
        Calendar calendar = Calendar.getInstance();

        calendar.set(year, monthOfYear, dayOfMonth);
        rCalendarFragment.setDateWeek(calendar);//Sets the selected date from Picker

    }
}
