package com.ramzcalender.sample;

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


public class Sample extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,View.OnClickListener, AdapterView.OnItemClickListener {
    RWeekCalendar rCalendarFragment;
    TextView mDateSelectedTv;
    FloatingActionButton fb;
    DataBaseHandler db = null;
    Button task,lead,conta,deal,calendr,cancel,plus;
    BottomSheetDialog dialog;
    CoordinatorLayout coordinatorLayout;
    GridView gd;



    String[] web = {
            "Appointment",
            "Task",
            "Lead",
            "Contact",
            "Deal",
            "Email",
            "TextMessage",



    } ;
    int[] imageId = {
            R.drawable.calendar_round,
            R.drawable.tasks_round,
            R.drawable.leads_round,
            R.drawable.contacts_round,
            R.drawable.deals_round,
            R.drawable.mail_round,
            R.drawable.chat_round,


    };



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample);

        db = new DataBaseHandler(Sample.this);
        db.getWritableDatabase();
        rCalendarFragment = new RWeekCalendar();

        /*Define you startDate and end Date*/
        rCalendarFragment.startDate(1989, 9, 1);//Start date
        rCalendarFragment.endDate(2050, 12, 31);//Ending date

        Bundle args = new Bundle();

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator);

     //   gd = (GridView) findViewById(R.id.bottom_sheet);
        task= (Button) findViewById(R.id.taks);
        plus= (Button) findViewById(R.id.plus_buton);
        task.setOnClickListener(this);
        plus.setOnClickListener(this);
     bottomsheet();
       /* task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
*/











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

                DatePickerDialog.newInstance(Sample.this, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).show(getFragmentManager(), "datePicker");


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

//This is the call back from picker used in the sample you can use custom or any other picker

        //IMPORTANT: get the year,month and date from picker you using and call setDateWeek method
        Calendar calendar = Calendar.getInstance();

        calendar.set(year, monthOfYear, dayOfMonth);
        rCalendarFragment.setDateWeek(calendar);//Sets the selected date from Picker


    }


    @Override
    public void onClick(View v) {
        if (v.getId() == task.getId()) {

           // dialog.show();
        } else if (v.getId() == plus.getId()) {
            dialog.show();
        }
    }


    public void bottomsheet() {
        View modalbottomsheet = getLayoutInflater().inflate(R.layout.bottom_grid, null);
        gd = (GridView) modalbottomsheet.findViewById(R.id.bottom_sheet);
        Custom_Grid adapter = new Custom_Grid(Sample.this, web, imageId);

        gd.setAdapter(adapter);
        dialog = new BottomSheetDialog(this);
        dialog.setContentView(modalbottomsheet);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);


//        dialog.show();
     //   cancel = (Button) modalbottomsheet.findViewById(R.id.cancel);
        gd.setOnItemClickListener(this);

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
       //  if (view.getId() == gd.getId()) {
             if(position == 0) {
                 Intent inte = new Intent(Sample.this, Add_appointment.class);
                 startActivity(inte);
                 finish();
             }else if(position ==1){
                 Intent inte = new Intent(Sample.this, Task_class.class);
                 startActivity(inte);
                 finish();
             }
             else if(position ==2){
                 Intent inte = new Intent(Sample.this, Lead_class.class);
                 startActivity(inte);
                 finish();
             }
             else if(position ==3){
                 Intent inte = new Intent(Sample.this, Contacts_page.class);
                 startActivity(inte);
                 finish();
             }
             else if(position ==4){

                 Intent inte = new Intent(Sample.this, Deals_page.class);
                 startActivity(inte);
                 finish();

             }
             else if(position ==5){
                 Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                         "mailto","", null));
                 //   intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                 // intent.putExtra(Intent.EXTRA_TEXT, message);
                 startActivity(Intent.createChooser(intent, "Choose an Email client :"));
             }
             else if(position ==6){
                 Intent intent = new Intent("android.intent.action.VIEW");

                 /** creates an sms uri */
                 Uri data = Uri.parse("sms:");

                 /** Setting sms uri to the intent */
                 intent.setData(data);

                 /** Initiates the SMS compose screen, because the activity contain ACTION_VIEW and sms uri */
                 startActivity(intent);
             }
       // }
    }
}
