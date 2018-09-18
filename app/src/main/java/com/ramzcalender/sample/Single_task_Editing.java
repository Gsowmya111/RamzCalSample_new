package com.ramzcalender.sample;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
import java.util.List;

import static com.ramzcalender.sample.Activity_Fragment.class_name;
import static com.ramzcalender.sample.SignIn_page.owner_name;

public class Single_task_Editing extends AppCompatActivity {
    TextView date, cont, taskfor, owner, descrptn;
    Button edit, del, back;

    private String slno, phn, tas, dat, tim, not, ale, nam;
    public static String task_des;
    public static String date_task;
    public static String time_task;
    public static String task_asso;
    public static String task_alert;
    public static String task_slno;
    public static String Classname;
    private String data1;
    private JSONArray Keyword;
    private String succ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_task__editing);

        edit = (Button) findViewById(R.id.edit_single_task);
        del = (Button) findViewById(R.id.setings_del_single_task);
        back = (Button) findViewById(R.id.back_single_task);
        date = (TextView) findViewById(R.id.date);
        cont = (TextView) findViewById(R.id.phone_tv_task);
        taskfor = (TextView) findViewById(R.id.tv_task_associ);
        owner = (TextView) findViewById(R.id.owner_task);
        descrptn = (TextView) findViewById(R.id.descri_task);

        //  if(level_number.equals("level 1")) {

        budne_data();
       // flag1=0;
        Classname="Single_task_Editig";
        task_des= descrptn.getText().toString();
        date_task=dat;
        time_task=tim;
        task_asso=taskfor.getText().toString();
        task_alert=ale;
        task_slno=slno;


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Single_task_Editing.this, Task_MainPage.class);
                startActivity(i);
            }
        });


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                class_name="Single_task_edit";
                Intent i1 = new Intent(Single_task_Editing.this, Task_class.class);
                startActivity(i1);
            }
        });

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Single_task_Editing.this);
                alertDialogBuilder.setMessage("Are   You sure u  wanted to delete contact");
                alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                                Thread t = new Thread() {
                                    public void run() {
                                        try {

                                            data1="#"+task_slno+"*"+phn+"#";

                                            postt();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };
                                t.start();

                                // Toast.makeText(SingleContact_MainPage.this,"You clicked yes button",Toast.LENGTH_LONG).show();
                            }
                        });

                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override

                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();



            }
        });
    }

    public void budne_data() {
        Bundle bundle = getIntent().getExtras();
        slno = bundle.getString("slno");
        tas = bundle.getString("task");
        dat = bundle.getString("date");
        tim = bundle.getString("time");
        not = bundle.getString("noti");
        ale = bundle.getString("alert");
        phn = bundle.getString("phone");
        nam = bundle.getString("name");

        date.setText(dat + "," + tim);
        cont.setText(phn);
        taskfor.setText(nam);
        descrptn.setText(tas);
        owner.setText(owner_name);
      //  Toast.makeText(Single_task_Editing.this, "time"+tim, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i1 = new Intent(Single_task_Editing.this, Task_MainPage.class);
        startActivity(i1);
    }
    void postt() throws JSONException {
        // Creating HTTP client
        HttpClient httpClient = new DefaultHttpClient();
        // Creating HTTP Post
        HttpPost httpPost = new HttpPost("http://edisonbro.in/crm/tasks.php");

        // Building post parameters
        // key and value pair
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
        String trdata = data1;
            nameValuePair.add(new BasicNameValuePair("deltask", trdata));
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
                for (int i = 0; i < Keyword.length(); i++) {
                    Log.d("TAG", "Task class notification response.." + responseBody);
                    JSONObject jsonobject = Keyword.getJSONObject(i);
                    succ = jsonobject.getString("delnotification");

                }
            }
            Log.d("TAG", responseBody);

            final String finalResponseBody = responseBody;

            runOnUiThread(new Runnable() {
                @Override
                public void run() {


                        if (succ.equals("false")) {
                            Toast.makeText(Single_task_Editing.this, "Task deleted success ", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(Single_task_Editing.this, "Eror in deleting task ", Toast.LENGTH_LONG).show();
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
