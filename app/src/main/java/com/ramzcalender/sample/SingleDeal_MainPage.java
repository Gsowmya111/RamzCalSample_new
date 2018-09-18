package com.ramzcalender.sample;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import static com.ramzcalender.sample.Choose_Contacts_for_task.mobile_str;
import static com.ramzcalender.sample.Deals_main_page.str;
import static com.ramzcalender.sample.SignIn_page.contacts_str;
import static com.ramzcalender.sample.SignIn_page.owner_name;

/**
 * Created by edison office on 8/4/2018.
 */
public class SingleDeal_MainPage extends AppCompatActivity {
    private String data1;
    private JSONArray Keyword;
    private String succ;
    TextView date, con_stage, taskfor, owner, descrptn,amout,sour;
    Button edit, del, back;
    private String slnn, phnn, hott, staa, sourr, daat, amt, nam,ass;
    public static String amt_deal,curr_deal,des_deal,phnoo_deal,ref_deal,slno_deal,sor_deal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_deal_editing);

        edit = (Button) findViewById(R.id.edit_single_deal);
        del = (Button) findViewById(R.id.setings_del_single_deal);
        back = (Button) findViewById(R.id.back_single_deal);

        amout = (TextView) findViewById(R.id.amout_deal_single);
        date = (TextView) findViewById(R.id.date_deal);
        con_stage = (TextView) findViewById(R.id.current_stage);
        taskfor = (TextView) findViewById(R.id.referal_deal);
        owner = (TextView) findViewById(R.id.owner_deall);
        descrptn = (TextView) findViewById(R.id.descri_deal);
        sour = (TextView) findViewById(R.id.source_deall);


        str="sdmp";

        budne_data();

        amt_deal=amout.getText().toString();
        curr_deal=con_stage.getText().toString();
        des_deal=descrptn.getText().toString();
        phnoo_deal=phnn;
        ref_deal=taskfor.getText().toString();
        slno_deal=slnn;
        sor_deal=sour.getText().toString();



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SingleDeal_MainPage.this, Deals_main_page.class);
                startActivity(i);
                finish();
            }
        });


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contacts_str="Single_deal_mainpage";
                Intent i1 = new Intent(SingleDeal_MainPage.this, Deals_page.class);
                startActivity(i1);
                finish();
            }
        });

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SingleDeal_MainPage.this);
                alertDialogBuilder.setMessage("Are   You sure u  wanted to delete contact");
                alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                                Thread t = new Thread() {
                                    public void run() {
                                        try {

                                            data1="#"+slnn+"*"+phnn+"#";

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
        nam = bundle.getString("nam");
        amt = bundle.getString("amt");
        hott = bundle.getString("hott");
        ass = bundle.getString("asso");
        staa = bundle.getString("stag");
        sourr = bundle.getString("sur");
        daat = bundle.getString("datt");
        slnn = bundle.getString("sln");
        phnn = bundle.getString("phn");

        amout.setText(amt);
        sour.setText(sourr);
        taskfor.setText(ass);
        date.setText(daat );
        con_stage.setText(staa);
        descrptn.setText(nam);
        owner.setText(owner_name);
        //  Toast.makeText(Single_task_Editing.this, "time"+tim, Toast.LENGTH_LONG).show();

    }

    void postt() throws JSONException {
        // Creating HTTP client
        HttpClient httpClient = new DefaultHttpClient();
        // Creating HTTP Post
        HttpPost httpPost = new HttpPost("http://edisonbro.in/crm/deal.php");

        // Building post parameters
        // key and value pair
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();


        String trdata = data1;


        nameValuePair.add(new BasicNameValuePair("deldeals", trdata));

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
                    succ = jsonobject.getString("deldeals");

                }


            }
            Log.d("TAG", responseBody);

            final String finalResponseBody = responseBody;

            runOnUiThread(new Runnable() {
                @Override
                public void run() {


                    if (succ.equals("false")) {
                        Toast.makeText(SingleDeal_MainPage.this, "Deal deleted success ", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(SingleDeal_MainPage.this, "Eror in deleting Deal ", Toast.LENGTH_LONG).show();
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




