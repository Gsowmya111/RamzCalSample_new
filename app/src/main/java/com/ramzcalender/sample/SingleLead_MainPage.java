package com.ramzcalender.sample;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static com.ramzcalender.sample.Lead_MainPage.leads_cla_nam;


/**
 * Created by edison office on 7/17/2018.
 */
public class SingleLead_MainPage extends Activity {
    Button back, edit, email, phone, text;
    Button settings;
    TextView tv_name,tvph,tvemail,tvaddress,tvlevel,tvstatus,tvmail;
    String[] settings_array = new String[]{
            "delete contact"
    };
    static String responseBody;
    static String finalResponseBody;
public static String namee,lnname,adesss,phhnum,emidd,staats,levlel,industr;

     String fnamm,lnamm,mob,id,adess,levell,stats,sourcee,statee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_leads_detail_activity);

        back = (Button) findViewById(R.id.back_single_lead);
        edit = (Button) findViewById(R.id.edit_single_lead);
        settings = (Button) findViewById(R.id.setings_del_single_lead);
        email = (Button) findViewById(R.id.email_icon_single_lead);
        phone = (Button) findViewById(R.id.phone_icon_single_lead);
        text = (Button) findViewById(R.id.msg_icon_single_lead);

        tv_name = (TextView) findViewById(R.id.name_textview_single);
        tvph = (TextView) findViewById(R.id.phone_tv_single);
        //tvemail = (TextView) findViewById(R.id.tv_email_single_con);
        tvaddress = (TextView) findViewById(R.id.address_tv_single);
     //   tvlevel = (TextView) findViewById(R.id.level_tv_single);
        tvstatus = (TextView) findViewById(R.id.custmrsts_tv_single);
        tvmail = (TextView) findViewById(R.id.email_tv_single);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SingleLead_MainPage.this);
                alertDialogBuilder.setMessage("Are   You sure u  wanted to delete contact");
                alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                                Thread t = new Thread() {

                                    public void run() {
                                        post();
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

        Bundle bundle = getIntent().getExtras();
         fnamm = bundle.getString("fnam");
        lnamm = bundle.getString("lnam");
     mob = bundle.getString("phh");
         id = bundle.getString("mai");
         adess = bundle.getString("add");
        sourcee = bundle.getString("sour");
        statee = bundle.getString("stat");
        stats = bundle.getString("inds");


        tv_name.setText(fnamm+" "+lnamm);
        tvph.setText(mob);
        tvaddress.setText(adess);
        tvlevel.setText(stats);
        tvstatus.setText(statee);
        tvmail.setText(id);


        namee= tv_name.getText().toString();
        lnname=lnamm;
        adesss=tvaddress.getText().toString();
        phhnum=tvph.getText().toString();
        emidd=tvmail.getText().toString();
        staats=tvstatus.getText().toString();
        levlel=tvlevel.getText().toString();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SingleLead_MainPage.this, Lead_MainPage.class);
                startActivity(i);
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leads_cla_nam = "Lead_Single_page";
                Intent intent = new Intent(SingleLead_MainPage.this, Lead_class.class);
                intent.putExtra("fname", fnamm);
                   intent.putExtra("lname", lnamm);
                intent.putExtra("phhe", mob);
                intent.putExtra("maie", id);
                intent.putExtra("adde",adess);
                intent.putExtra("leve", levell);
                intent.putExtra("state", stats);
                startActivity(intent);



            }
        });
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Uri number = Uri.parse("tel:"+tvph.getText().toString());
              //  Uri number = Uri.parse("tel:123456789");
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
               startActivity(callIntent);

            }
        });
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "test");
                sendIntent.setType("text/plain");

                // Do not forget to add this to open whatsApp App specifically
                sendIntent.setPackage("com.whatsapp");
                startActivity(sendIntent);
            }
        });

    }
    protected void sendEmail() {
        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{ id});
        email.putExtra(Intent.EXTRA_SUBJECT, "");
        email.putExtra(Intent.EXTRA_TEXT, "");

        //need this to prompts email client only
        email.setType("message/rfc822");

        startActivity(Intent.createChooser(email, "Choose an Email client :"));
    }



    private void post() {

        // Creating HTTP client
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost=null;
        List<NameValuePair> nameValuePair = new ArrayList<>();
        // Creating HTTP Post
        httpPost = new HttpPost(
                "http://edisonbro.in/crm/leads.php");

        String trdata = tvph.getText().toString();
        nameValuePair = new ArrayList<>();

        nameValuePair.add(new BasicNameValuePair("del", trdata));


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
                    if(finalResponseBody.equals("deleted")) {
                        Toast.makeText(SingleLead_MainPage.this, "Lead deleted successfully", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(SingleLead_MainPage.this, "Error in deleting Lead ", Toast.LENGTH_LONG).show();
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
