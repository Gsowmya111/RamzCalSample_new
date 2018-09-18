package com.ramzcalender.sample;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by edison office on 6/22/2018.
 */

public class Add_appointment extends Activity {
    Button cancel, save;
    TextView fromtime, to_time, from_date, to_date, text_addr;
    EditText title, person_name, mob_num, addr, des;
    private String fromdatestr;
    private static final int Date_id = 0;
    private static final int Time_id = 1;
  //  Button address;
    String titl, fron_dat, to_dat, from_tim, to_tim, pname, mobnum, address, descrptio;
    String responseBody,  finalResponseBody ;
    private String data_send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_appointment_activity);
        cancel = (Button) findViewById(R.id.buton_cancel_appoint);
        save = (Button) findViewById(R.id.buton_save_appoint);


        fromtime = (TextView) findViewById(R.id.from_time);
        to_time = (TextView) findViewById(R.id.to_time);

        from_date = (TextView) findViewById(R.id.from_date);
        to_date = (TextView) findViewById(R.id.to_date);
        addr = (EditText) findViewById(R.id.addr);
        title = (EditText) findViewById(R.id.title_appointment);
        person_name = (EditText) findViewById(R.id.person_name);
        mob_num = (EditText) findViewById(R.id.mobile_number_appont);
        des = (EditText) findViewById(R.id.description);

        //   address= (Button) findViewById(R.id.address_icon);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titl = title.getText().toString();
                from_tim=fromtime.getText().toString();
                to_tim=to_time.getText().toString();
                fron_dat = from_date.getText().toString();
                to_dat=to_date.getText().toString();
                pname=person_name.getText().toString();
                address=addr.getText().toString();
                descrptio=des.getText().toString();
                mobnum=mob_num.getText().toString();

                Thread t = new Thread() {

                    public void run() {
                     data_send = "#" + fron_dat + "*" + from_tim + "*" + to_dat + "*" + to_tim + "*" + pname + "*" + mobnum +  "*" + address + "*" + descrptio +"*" + titl +"#";

                        post();
                    }
                };
                t.start();

            }
        });





        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Add_appointment.this, CalenderMain.class);
                startActivity(in);
                finish();
            }
        });

        from_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromdatestr = "fromdate";
                showDialog(Date_id);
            }
        });
        to_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromdatestr = "todate";
                showDialog(Date_id);
            }
        });
       /* address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAddress(lat, longi);
            }
        });*/

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
                return new DatePickerDialog(Add_appointment.this, date_listener, year,
                        month, day);
            case Time_id:

                // Open the timepicker dialog
                return new TimePickerDialog(Add_appointment.this, time_listener, hour,
                        minute, false);
        }
        return null;
    }

    // Date picker dialog
    DatePickerDialog.OnDateSetListener date_listener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            // store the data in one string and set it to text
            int months=month+1;
            String date1 = months + "/" + String.valueOf(day)
                    + "/" + String.valueOf(year);


            if (fromdatestr.equals("fromdate")) {
                from_date.setText(date1);
                showDialog(Time_id);

            } else if (fromdatestr.equals("todate")) {
                to_date.setText(date1);
                showDialog(Time_id);
            }

        }
    };
    TimePickerDialog.OnTimeSetListener time_listener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // store the data in one string and set it to text
            //   String time1 = String.valueOf(hour) + ":" + String.valueOf(minute);
            if (fromdatestr.equals("fromdate")) {


                if (hourOfDay > 12) {
                    fromtime.setText(String.valueOf(hourOfDay - 12) + ":" + (String.valueOf(minute) + "pm"));
                } else if (hourOfDay == 12) {
                    fromtime.setText("12" + ":" + (String.valueOf(minute) + "pm"));
                } else if (hourOfDay < 12) {
                    if (hourOfDay != 0) {
                        fromtime.setText(String.valueOf(hourOfDay) + ":" + (String.valueOf(minute) + "am"));
                    } else {
                        fromtime.setText("12" + ":" + (String.valueOf(minute) + "am"));
                    }
                }


                //   fromtime.setText(time1);
                showDialog(Time_id);

            } else if (fromdatestr.equals("todate")) {


                if (hourOfDay > 12) {
                    to_time.setText(String.valueOf(hourOfDay - 12) + ":" + (String.valueOf(minute) + "pm"));
                } else if (hourOfDay == 12) {
                    to_time.setText("12" + ":" + (String.valueOf(minute) + "pm"));
                } else if (hourOfDay < 12) {
                    if (hourOfDay != 0) {
                        to_time.setText(String.valueOf(hourOfDay) + ":" + (String.valueOf(minute) + "am"));
                    } else {
                        to_time.setText("12" + ":" + (String.valueOf(minute) + "am"));
                    }
                }


                //  to_time.setText(time1);
                showDialog(Time_id);
            }

        }
    };

    private String getAddress(double latitude, double longitude) {
        StringBuilder result = new StringBuilder();
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                String a = addresses.get(0).getAddressLine(0);
                //   String ad= String.valueOf(result.append(address.getLocality()).append("\n"));
                result.append(address.getCountryName());
                text_addr.setText(a);
            }
        } catch (IOException e) {
            Log.e("tag", e.getMessage());
        }

        return result.toString();
    }
    private void post() {

        // Creating HTTP client
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost=null;
        List<NameValuePair> nameValuePair = new ArrayList<>();
        // Creating HTTP Post
        httpPost = new HttpPost(
                "http://edisonbro.in/crm/appointment.php");

        String trdata = data_send;
        nameValuePair = new ArrayList<>();

        nameValuePair.add(new BasicNameValuePair("appointment", trdata));


        // Url Encoding the POST parameters
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
        }

        // Making HTTP Request
        try {
            final HttpResponse response = httpClient.execute(httpPost);


            HttpEntity entity = response.getEntity();
            if (entity != null) {
                responseBody = EntityUtils.toString(entity);
                Log.d("TAG", "Http post Response: " + responseBody);
            }
            Log.d("TAG", responseBody);

            finalResponseBody = responseBody;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(finalResponseBody.equals("success")) {
                        Toast.makeText(Add_appointment.this, "Appointment added successfully", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(Add_appointment.this, "Appointment creation error ", Toast.LENGTH_LONG).show();
                    }

                }
            });
        } catch (ClientProtocolException e) {
            // writing exception to log
            e.printStackTrace();
        } catch (IOException e) {
            // writing exception to log
            e.printStackTrace();

        }


    }


}





