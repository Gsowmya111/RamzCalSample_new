package com.ramzcalender.sample;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.ramzcalender.sample.SignIn_page.contacts_str;
import static com.ramzcalender.sample.SignIn_page.login_numbr;
import static java.security.AccessController.getContext;

/**
 * Created by edison office on 7/17/2018.
 */
public class SingleContact_MainPage extends Activity {
    Button back, edit, email, phone, text;
    Button settings;
    TextView tv_name,tvph,tvemail,tvaddress,tvlevel,tvstatus,tvmail,tv_last_cont;
    String[] settings_array = new String[]{
            "delete contact"
    };
    static String responseBody;
    static String finalResponseBody;
public static String nam,lnamee,phhno,emid,sta,levle,ades;

     String fnamm,lnamm,mob,id,adess,levell,stats,statuss,del_no;

    private ArrayList<String> conNames;
    private ArrayList<String> conNumbers;
    private ArrayList<String> conTime;
    private ArrayList<String> conDate;
    private ArrayList<String> conType;
    private String last;
    int sum= 0;
    private String trdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_contacts_detail_activity);

        back = (Button) findViewById(R.id.back_single_cust);
        edit = (Button) findViewById(R.id.edit_single_cust);
        settings = (Button) findViewById(R.id.setings_del_single_cust);
        email = (Button) findViewById(R.id.email_icon_singlecust);
        phone = (Button) findViewById(R.id.phone_icon_singlecust);
        text = (Button) findViewById(R.id.msg_icon_singlecust);

        tv_name = (TextView) findViewById(R.id.name_textview_single);
        tvph = (TextView) findViewById(R.id.phone_tv_single);
        //tvemail = (TextView) findViewById(R.id.tv_email_single_con);
        tvaddress = (TextView) findViewById(R.id.address_tv_single);
        tv_last_cont = (TextView) findViewById(R.id.last_contacted);
        tvstatus = (TextView) findViewById(R.id.custmrsts_tv_single);
        tvmail = (TextView) findViewById(R.id.email_tv_single);

        conNames = new ArrayList<String>();
        conNumbers = new ArrayList<String>();
        conTime = new ArrayList<String>();
        conDate = new ArrayList<String>();
        conType = new ArrayList<String>();



        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SingleContact_MainPage.this);
                alertDialogBuilder.setMessage("Are   You sure u  wanted to delete contact");
                alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                                Thread t = new Thread() {

                                    public void run() {
                                       trdata = tvph.getText().toString();
                                        statuss="delete";
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
        levell = bundle.getString("lev");
        stats = bundle.getString("stat");
        del_no= bundle.getString("num_deals");

        Cursor curLog = CallLogHelper.getAllCallLogs(getContentResolver());
        setCallLogs(curLog) ;

        tv_name.setText(fnamm);
        tvph.setText(mob);
        tvaddress.setText(adess);
//        tvlevel.setText(levell);
        tvstatus.setText(stats);
        tvmail.setText(id);
        tv_last_cont.setText(last);
      //  tv_last_cont.setText(del_no);

        nam= tv_name.getText().toString();
        lnamee=lnamm;
        ades=tvaddress.getText().toString();
        phhno=tvph.getText().toString();
        emid=tvmail.getText().toString();
        sta=tvstatus.getText().toString();
        levle=levell;




        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SingleContact_MainPage.this, MainActivity.class);
                startActivity(i);
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contacts_str="SinglePage";

                Intent intent = new Intent(SingleContact_MainPage.this, Contacts_page.class);
                intent.putExtra("fname", fnamm);
                   intent.putExtra("lname", lnamm);
                intent.putExtra("phhe", mob);
                intent.putExtra("maie", id);
                intent.putExtra("adde",adess);
                intent.putExtra("leve", levell);
                intent.putExtra("state", stats);
                startActivity(intent);



                Intent i = new Intent(SingleContact_MainPage.this, Contacts_page.class);
                startActivity(i);
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



        Thread t = new Thread() {

            public void run() {
               // for(int j = 0;j<=sum;j++){
                    trdata = "*"+login_numbr+"@"+tvph.getText().toString()+"@"+sum+"@"+last+"*";
                    statuss="dialed_list";
                    post();
              //  }


            }
        };
        t.start();




    }



    private void setCallLogs(Cursor curLog) {
        while (curLog.moveToNext()) {
            String callNumber = curLog.getString(curLog
                    .getColumnIndex(android.provider.CallLog.Calls.NUMBER));
            conNumbers.add(callNumber);

            String callName = curLog
                    .getString(curLog
                            .getColumnIndex(android.provider.CallLog.Calls.CACHED_NAME));
            if (callName == null) {
                conNames.add("Unknown");
            } else
                conNames.add(callName);

            String callDate = curLog.getString(curLog
                    .getColumnIndex(android.provider.CallLog.Calls.DATE));
            SimpleDateFormat formatter = new SimpleDateFormat(
                    "dd-MMM-yyyy HH:mm");
            String dateString = formatter.format(new Date(Long
                    .parseLong(callDate)));
            conDate.add(dateString);

            String callType = curLog.getString(curLog
                    .getColumnIndex(android.provider.CallLog.Calls.TYPE));
            if (callType.equals("1")) {
                conType.add("Incoming");
            } else
                conType.add("Outgoing");

            String duration = curLog.getString(curLog
                    .getColumnIndex(android.provider.CallLog.Calls.DURATION));
            conTime.add(duration);
        }

        Log.d("TAG", String.valueOf(conNumbers));
        Log.d("TAG", String.valueOf(conDate));
        for(int i = 0;i<conNumbers.size();i++) {
            if (conNumbers.get(i).toString().equals(mob)) {
                Toast.makeText(SingleContact_MainPage.this, "Numbers dates:" + conDate.get(i), Toast.LENGTH_LONG);
                Log.d("TAG", "Numbers dates:" + conDate.get(i));
                last = conDate.get(i);
                // int no= i1+1;
                break;
            }

        }

        for (int i1 = 0; i1 < conNumbers.size(); i1++) {
            if (conNumbers.get(i1).toString().equals(mob)) {

                sum=sum+1;
                Log.d("TAG", "Numbers od times:" + sum);
                //  last = conDate.get(i1);

            }

        }
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
                "http://edisonbro.in/crm/insert_contacts.php");


        nameValuePair = new ArrayList<>();
        if(statuss.equals("delete")) {

            nameValuePair.add(new BasicNameValuePair("del", trdata));
        }else{
            nameValuePair.add(new BasicNameValuePair("dailed", trdata));
        }

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

                    if(statuss.equals("delete")) {

                        if (finalResponseBody.equals("deleted")) {
                            Toast.makeText(SingleContact_MainPage.this, "Contacts deleted successfully", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(SingleContact_MainPage.this, "Error in deleting Contacts ", Toast.LENGTH_LONG).show();
                        }
                    }else{

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
