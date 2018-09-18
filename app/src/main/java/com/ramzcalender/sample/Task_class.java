package com.ramzcalender.sample;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import static com.ramzcalender.sample.Activity_Fragment.class_name;


import static com.ramzcalender.sample.Choose_Contacts_for_task.mobile_str;
import static com.ramzcalender.sample.Choose_Contacts_for_task.name_str1;
import static com.ramzcalender.sample.Choose_Contacts_for_task.name_str2;
import static com.ramzcalender.sample.Choose_lead_for_task.lead_mobile_str3;
import static com.ramzcalender.sample.Choose_lead_for_task.lead_name_str1;
import static com.ramzcalender.sample.Choose_lead_for_task.lead_name_str2;
import static com.ramzcalender.sample.Lead_MainPage.alert_clas;
import static com.ramzcalender.sample.MainActivity.Activity_fragm;


import static com.ramzcalender.sample.MainActivity.flag;
import static com.ramzcalender.sample.SignIn_page.login_numbr;
import static com.ramzcalender.sample.Single_task_Editing.Classname;
import static com.ramzcalender.sample.Single_task_Editing.date_task;
import static com.ramzcalender.sample.Single_task_Editing.task_alert;
import static com.ramzcalender.sample.Single_task_Editing.task_asso;
import static com.ramzcalender.sample.Single_task_Editing.task_des;
import static com.ramzcalender.sample.Single_task_Editing.task_slno;
import static com.ramzcalender.sample.Single_task_Editing.time_task;

/**
 * Created by edison office on 7/16/2018.
 */


public class Task_class extends Activity {
    Button save, cancel;
    EditText description;
    TextView duedate, settime;
    Button alert_time_spr;
    Button Associated_with;
    private static final int Date_id = 0;
    private static final int Time_id = 1;
    public static String task_activity;

    DataBaseHandler_new db = null;
    private SQLiteDatabase dataBase;
    private String responseBody, finalResponseBody;
    private String data_send;
    private String phnumb;
    private static String des, due, tim, assoc, alet;
    private SharedPreferences savednotes;
    Globals globalVariable;
    private JSONArray Roomes1;
    private String slno_jsn, date_jsn, tim_jsn, alert_jsn, noti_jsn;
    String sln;
    private JSONArray Keyword;
    private String valu;
    private String updnot, succ;
    View alertLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task);
        db = new DataBaseHandler_new(Task_class.this);
        db.getWritableDatabase();

        //creating IDs
        save = (Button) findViewById(R.id.save_btn1);
        cancel = (Button) findViewById(R.id.cancel_btn_1);
        description = (EditText) findViewById(R.id.task_descr);
        duedate = (TextView) findViewById(R.id.tv_due_date);
        settime = (TextView) findViewById(R.id.tv_set_time);
        alert_time_spr = (Button) findViewById(R.id.spinr_alert_task);

        //spinner items adding from array
        Associated_with = (Button) findViewById(R.id.spinr_Associated_with);

        globalVariable = (Globals) getApplicationContext();
        globalVariable.setClass_names("Class_task");
        //  class_name="task";

        sln = task_slno;


        if (Activity_fragm.equals("Choose_contact")) {
            phnumb = mobile_str;
            Associated_with.setText(name_str1 + " " + name_str2);

        } else if (Activity_fragm.equals("Main_cls")) {
            Associated_with.setText("");
        } else if (Activity_fragm.equals("Choose_lead")) {
            phnumb = lead_mobile_str3;
            Associated_with.setText(lead_name_str1 + " " + lead_name_str2);

        }


        Associated_with.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // custom dialog
                task_activity = "Task_class";

              /*  final Dialog dialog = new Dialog(Task_class.this);
                dialog.setContentView(R.layout.alert_task_class_association);
                dialog.setTitle("This Task is for...");
                // set the custom dialog components - text, image and button
                Button cont = (Button) dialog.findViewById(R.id.button_contact_alert);
                Button lead = (Button) dialog.findViewById(R.id.button_lead_alert);
                Button deal = (Button) dialog.findViewById(R.id.button_deal_alert);
                Button notspecified = (Button) dialog.findViewById(R.id.button_notspecified_alert);
                //  Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                // if button is clicked, close the custom dialog
                cont.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent icon = new Intent(Task_class.this, Choose_Contacts_for_task.class);
                        startActivity(icon);
                        // finish();


                    }
                });

                dialog.show();*/


                final LayoutInflater inflater1 = getLayoutInflater();
                alertLayout = inflater1.inflate(R.layout.alert_task_class_association, null);
                AlertDialog.Builder alert = new AlertDialog.Builder(Task_class.this);

                alert.setView(alertLayout);
                alert.setCancelable(true);
                final AlertDialog dialog1 = alert.create();
                dialog1.show();


                Button cont = (Button) alertLayout.findViewById(R.id.button_contact_alert);
                Button lead = (Button) alertLayout.findViewById(R.id.button_lead_alert);
                //    Button deal = (Button) dialog.findViewById(R.id.button_deal_alert);
                Button notspecified = (Button) alertLayout.findViewById(R.id.button_notspecified_alert);
                cont.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent icon = new Intent(Task_class.this, Choose_Contacts_for_task.class);
                        startActivity(icon);
                        finish();


                    }
                });
                lead.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert_clas = "task";
                        Intent icon = new Intent(Task_class.this, Choose_lead_for_task.class);
                        startActivity(icon);
                        finish();


                    }
                });


                int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.75);
                int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.30);
                dialog1.getWindow().setLayout(width, height);
                dialog1.show();


            }

        });


        description.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                globalVariable.setName(description.getText().toString());
                //  globalVariable.setEmail("xxxxxx@aaaa.com");
            }
        });

        if ((flag == 1) || (Activity_fragm.equals("Choose_lead"))) {
            final String na = globalVariable.getName();
            description.setText(na);
            final String n1 = globalVariable.getEmail();
            duedate.setText(n1);
            final String n2 = globalVariable.getPasswords();
            settime.setText(n2);
        } else if ((flag == 0)) {
            duedate.setText("Set data");
            description.setText("");
            settime.setText("SetTime");
        }
        if (class_name.equals("Single_task_edit")) {
            class_name = "";
            duedate.setText(date_task);
            description.setText(task_des);
            settime.setText(time_task);
            //  Associated_with.setText(task_asso);
            alert_time_spr.setText(task_alert);
            sln = task_slno;
        }


        alert_time_spr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // custom dialog
                task_activity = "Task_class";

              /*  final Dialog dialog = new Dialog(Task_class.this);
                dialog.setContentView(R.layout.alert_task_class_alerttime);
                dialog.setTitle("Set alert time for ...");
                dialog.setCancelable(true);
                // set the custom dialog components - text, image and button
                final Button fivemin = (Button) dialog.findViewById(R.id.button_alert_5min);
                final Button ten = (Button) dialog.findViewById(R.id.button_alert_10min);
                final Button fiftn = (Button) dialog.findViewById(R.id.button_alert_15min);
                final Button notime = (Button) dialog.findViewById(R.id.button_alert_no);
                //  Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                // if button is clicked, close the custom dialog
                fivemin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        alert_time_spr.setText(fivemin.getText().toString());
                        dialog.cancel();

                    }
                });

                ten.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        alert_time_spr.setText(ten.getText().toString());
                        dialog.cancel();
                    }
                });
                fiftn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        alert_time_spr.setText(fiftn.getText().toString());
                        dialog.cancel();
                    }
                });
                notime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        alert_time_spr.setText(notime.getText().toString());
                        dialog.cancel();
                    }
                });
                dialog.show();*/


                final LayoutInflater inflater1 = getLayoutInflater();
                alertLayout = inflater1.inflate(R.layout.alert_task_class_alerttime, null);
                AlertDialog.Builder alert = new AlertDialog.Builder(Task_class.this);

                alert.setView(alertLayout);
                alert.setCancelable(true);
                final AlertDialog dialog1 = alert.create();
                dialog1.show();


                final Button fivemin = (Button) alertLayout.findViewById(R.id.button_alert_5min);
                final Button ten = (Button) alertLayout.findViewById(R.id.button_alert_10min);
                final Button fiftn = (Button) alertLayout.findViewById(R.id.button_alert_15min);
                final Button notime = (Button) alertLayout.findViewById(R.id.button_alert_no);

                fivemin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        alert_time_spr.setText(fivemin.getText().toString());
                        dialog1.cancel();

                    }
                });

                ten.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        alert_time_spr.setText(ten.getText().toString());
                        dialog1.cancel();
                    }
                });
                fiftn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        alert_time_spr.setText(fiftn.getText().toString());
                        dialog1.cancel();
                    }
                });
                notime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        alert_time_spr.setText(notime.getText().toString());
                        dialog1.cancel();
                    }
                });

                int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.75);
                int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.30);
                dialog1.getWindow().setLayout(width, height);
                dialog1.show();


            }

        });


        savednotes = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                des = description.getText().toString();
                due = duedate.getText().toString();
                tim = settime.getText().toString();
                assoc = Associated_with.getText().toString();
                alet = alert_time_spr.getText().toString();
                // makeTag(des);
                globalVariable.setLogin_mob(phnumb);
                if ((class_name.equals("Task_class")) || (Activity_fragm.equals("Choose_lead"))) {
                    data_send = "#" + des + "*" + due + "*" + tim + "*" + phnumb + "*" + alet + "*" + login_numbr + "#";
                    //
                } else {
                    data_send = "#" + des + "*" + due + "*" + tim + "*" + phnumb + "*" + alet + "*" + login_numbr + "*" + sln + "#";

                }

                Thread t = new Thread() {
                    public void run() {
                        try {
                            postt();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                t.start();
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Task_class.this, Tasks_Tabs_MainPage.class);
                startActivity(in);
                finish();
            }
        });

        duedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(Date_id);
            }
        });

        settime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(Time_id);
            }
        });

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
                return new DatePickerDialog(Task_class.this, date_listener, year,
                        month, day);
            case Time_id:

                // Open the timepicker dialog
                return new TimePickerDialog(Task_class.this, time_listener, hour,
                        minute, false);
        }
        return null;
    }

    private String month1;
    DatePickerDialog.OnDateSetListener date_listener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            // store the data in one string and set it to text
           /* String date1 = String.valueOf(month) + "-" + String.valueOf(day)
                    + "-" + String.valueOf(year);*/

            //  String date1= "2018-06-25";
            int months = month + 1;
            String date1 = String.valueOf(year) + "-" + String.valueOf(month)
                    + "-" + String.valueOf(day);
            if (month < 10) {
                month1 = "0" + months;
            }
            String date11 = String.valueOf(year) + "-" + String.valueOf(month1)
                    + "-" + String.valueOf(day);
            duedate.setText(date11);
            //  sharedData.setValue(date11);
            globalVariable.setEmail(date11);
        }
    };
    TimePickerDialog.OnTimeSetListener time_listener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // store the data in one string and set it to text
            String time1 = String.valueOf(hourOfDay) + ":" + String.valueOf(minute);

            settime.setText(time1);
            globalVariable.setPasswords(time1);

          /*  if (hourOfDay > 12) {
                settime.setText(String.valueOf(hourOfDay - 12) + ":" + (String.valueOf(minute) + "pm"));
            } else if (hourOfDay == 12) {
                settime.setText("12" + ":" + (String.valueOf(minute) + "pm"));
            } else if (hourOfDay < 12) {
                if (hourOfDay != 0) {
                    settime.setText(String.valueOf(hourOfDay) + ":" + (String.valueOf(minute) + "am"));
                } else {
                    settime.setText("12" + ":" + (String.valueOf(minute) + "am"));
                }
            }*/

        }
    };


    void postt() throws JSONException {
        // Creating HTTP client
        HttpClient httpClient = new DefaultHttpClient();
        // Creating HTTP Post
        HttpPost httpPost = new HttpPost("http://edisonbro.in/crm/tasks.php");

        // Building post parameters
        // key and value pair
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();


        String trdata = data_send;

        if ((class_name.equals("Task_class")) || (Activity_fragm.equals("Choose_lead"))) {
            nameValuePair.add(new BasicNameValuePair("task", trdata));
        } else {

            nameValuePair.add(new BasicNameValuePair("updtask", trdata));
        }

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
        }

        // Making HTTP Request
        try {
            final HttpResponse response = httpClient.execute(httpPost);
            String responseBody = null;

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                responseBody = EntityUtils.toString(entity);
                //   JSONArray jsonarray = new JSONArray(responseBody);
                //   Log.d("TAG","Task class notification response2"+responseBody);
                JSONObject jsonObj = new JSONObject(responseBody);
                Keyword = jsonObj.getJSONArray("error");
                Log.d("TAG", "Task class notification response" + responseBody);

                if ((class_name.equals("Task_class")) || (Activity_fragm.equals("Choose_lead"))) {
                    for (int i = 0; i < Keyword.length(); i++) {
                        Log.d("TAG", "Task class notification response.." + responseBody);
                        JSONObject jsonobject = Keyword.getJSONObject(i);
                        succ = jsonobject.getString("success");

                    }
                } else {
                    for (int i = 0; i < Keyword.length(); i++) {
                        Log.d("TAG", "Task class notification response.." + responseBody);
                        JSONObject jsonobject = Keyword.getJSONObject(i);
                        updnot = jsonobject.getString("updnotification");
                    }
                }


            }
            Log.d("TAG", responseBody);

            final String finalResponseBody = responseBody;

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if ((class_name.equals("Task_class")) || (Activity_fragm.equals("Choose_lead"))) {
                        if (succ.equals("false")) {
                            Toast.makeText(Task_class.this, "Task created success ", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(Task_class.this, "Task creation error ", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        if (updnot.equals("false")) {
                            Toast.makeText(Task_class.this, "Task updated success ", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(Task_class.this, "Task updation  error ", Toast.LENGTH_LONG).show();
                        }
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
