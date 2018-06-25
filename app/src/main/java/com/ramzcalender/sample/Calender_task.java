package com.ramzcalender.sample;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import static com.ramzcalender.sample.RecyclerAdapter.cont_nam;

public class Calender_task extends AppCompatActivity {
    DataBaseHandler db = null;
    TextView attende_plus,deal_plus,from_date,to_date,from_time,to_time;
    ListView listView_atendee,listView_deal;
   Button save,cancel;
        int j;
    private static final int Date_id = 0;
    private static final int Time_id = 1;


    LinearLayout container;
    private String fromdatestr;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calender_task);
        db = new DataBaseHandler(Calender_task.this);
        db.getWritableDatabase();

        to_time= (TextView) findViewById(R.id.to_time);
        from_time= (TextView) findViewById(R.id.from_time);
        to_date= (TextView) findViewById(R.id.To_date_picker);
        from_date= (TextView) findViewById(R.id.from_date_picker);
        attende_plus= (TextView) findViewById(R.id.Ateendee_plus);
        deal_plus= (TextView) findViewById(R.id.Deals_plus);
       // ListView   listView = (ListView) findViewById(R.id.list_view);
        listView_deal = (ListView) findViewById(R.id.list_deal);
      //  textIn = (EditText)findViewById(R.id.textin);
      //  buttonAdd = (Button)findViewById(R.id.add);
        container = (LinearLayout)findViewById(R.id.container);
        save= (Button) findViewById(R.id.save_contc);
        cancel= (Button) findViewById(R.id.cancel_contc);
        editText=(EditText) findViewById(R.id.edittext_title);



        attende_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* Intent i  = new Intent(Calender_task.this,Contacts_list.class);
                startActivity(i);
                finish();
*/
                Intent intent = new Intent(Calender_task.this,Contacts_list.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
               // intent.setClassName(this,"com.mainscreen.activity2");
                startActivity(intent);


            }
        });
     /*   if(cont_nam.size()>0) {

                DisplayAdapter disadpt = new DisplayAdapter(Calender_task.this, cont_nam);
               listView_atendee.setAdapter(disadpt);


        } if(cont_nam.size()>0) {

        }*/


            final String conctnames_aray= String.valueOf(cont_nam);
        if(cont_nam.size()>0) {

            //
            for ( int i = 0; i < cont_nam.size(); i++) {
             j =i;
                LayoutInflater layoutInflater =
                        (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addView = layoutInflater.inflate(R.layout.row, null);
             //   listView_atendee = (ListView)  addView.findViewById(R.id.list_atendee);
                TextView textOut = (TextView) addView.findViewById(R.id.textout);
                Button buttonRemove = (Button) addView.findViewById(R.id.remove);
                textOut.setText(cont_nam.get(i));

                buttonRemove.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        ((LinearLayout) addView.getParent()).removeView(addView);
                      //  cont_nam.remove(j);
                    }
                });
            container.addView(addView);
        }
        }


        deal_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater =
                        (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addView = layoutInflater.inflate(R.layout.row, null);
                TextView textOut = (TextView)addView.findViewById(R.id.textout);
                textOut.setText(cont_nam.toString());
                Button buttonRemove = (Button)addView.findViewById(R.id.remove);
                buttonRemove.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        ((LinearLayout)addView.getParent()).removeView(addView);
                    }});

                container.addView(addView);

            }
        });




        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Calender_task.this,conctnames_aray,Toast.LENGTH_LONG).show();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(Calender_task.this,Sample.class);
                startActivity(i);
                finish();
            }
        });


        from_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// Show Date dialog
                 fromdatestr="fromdate";
                showDialog(Date_id);
            }
        });

        to_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              fromdatestr="todate";
                showDialog(Date_id);
            }
        });
    }




    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
       // Log.i(TAG, "onSaveInstanceState");

        // getting the reference of editext from xml
        CharSequence text  = editText.getText();// getting text u entered in edittext
        outState.putCharSequence("savedText", text);// saved that text in bundle object i.e. outState

    }



    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
     //   Log.i(TAG, "onRestoreInstanceState");

        CharSequence savedText=
                savedInstanceState.getCharSequence("savedText");// getting the text of editext

        editText.setText(savedText);// set the text that is retrieved from bundle object
    }





    protected Dialog onCreateDialog(int id) {

        // Get the calander
        Calendar c = Calendar.getInstance();

        // From calander get the year, month, day, hour, minute
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        switch (id) {
            case Date_id:

                // Open the datepicker dialog
                return new DatePickerDialog(Calender_task.this, date_listener, year,
                        month, day);
            case Time_id:

                // Open the timepicker dialog
                return new TimePickerDialog(Calender_task.this, time_listener, hour,
                        minute, false);

        }
        return null;
    }

    // Date picker dialog
    DatePickerDialog.OnDateSetListener date_listener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            // store the data in one string and set it to text
            String date1 = String.valueOf(month) + "/" + String.valueOf(day)
                    + "/" + String.valueOf(year);
            if(fromdatestr.equals("fromdate")) {
                from_date.setText(date1);
                showDialog(Time_id);

            }else  if(fromdatestr.equals("todate")) {
                to_date.setText(date1);
                showDialog(Time_id);
            }

        }
    };
    TimePickerDialog.OnTimeSetListener time_listener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hour, int minute) {
            // store the data in one string and set it to text
            String time1 = String.valueOf(hour) + ":" + String.valueOf(minute);


            if(fromdatestr.equals("fromdate")) {
                from_time.setText(time1);
                showDialog(Time_id);

            }else  if(fromdatestr.equals("todate")) {
                to_time.setText(time1);
                showDialog(Time_id);
            }

        }
    };





    }

