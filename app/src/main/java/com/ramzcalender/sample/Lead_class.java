package com.ramzcalender.sample;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static com.ramzcalender.sample.Lead_MainPage.leads_cla_nam;
import static com.ramzcalender.sample.SingleLead_MainPage.adesss;
import static com.ramzcalender.sample.SingleLead_MainPage.emidd;
import static com.ramzcalender.sample.SingleLead_MainPage.levlel;
import static com.ramzcalender.sample.SingleLead_MainPage.lnname;
import static com.ramzcalender.sample.SingleLead_MainPage.namee;
import static com.ramzcalender.sample.SingleLead_MainPage.phhnum;
import static com.ramzcalender.sample.SingleLead_MainPage.staats;

/**
 * Created by edison office on 7/16/2018.
 */


public class Lead_class extends Activity {
    Button save,cancel;
    EditText fname,lname,comapany_name,title,mobile_no1,mobile_no2,email,other_cont,address1,address2,descriptn,Industry_type;
    Spinner source_lead;
    TextView status_lead;
    String[] status_lead_array = new String[] {
            " Active", " Cold", " Inactive"
    };
    String[] source_lead_array = new String[] {
            "No source"
    };
    private String data_send;
    private JSONArray Keyword;
    private String updnot, succ;
    private String data_push;
    View alertLayout;

    //   TextView ownername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_lead_activity);

        save = (Button) findViewById(R.id.save_btn_lead);
        cancel = (Button) findViewById(R.id.cancel_btn_lead);

        fname = (EditText) findViewById(R.id.first_name_lead);
        lname = (EditText) findViewById(R.id.last_name_lead);
        comapany_name = (EditText) findViewById(R.id.company_name_lead);
        title = (EditText) findViewById(R.id.title_lead);
        mobile_no1 = (EditText) findViewById(R.id.mobile_name_lead);
        mobile_no2 = (EditText) findViewById(R.id.other_mobno);
        email = (EditText) findViewById(R.id.email_lead);
       // other_cont = (EditText) findViewById(R.id.other_lead);
        address1 = (EditText) findViewById(R.id.address_lead);
        address2 = (EditText) findViewById(R.id.other_address);
        descriptn = (EditText) findViewById(R.id.descr_lead);
        Industry_type = (EditText) findViewById(R.id.indstry_type_lead);

        status_lead= (TextView) findViewById(R.id.status_lead);
        source_lead= (Spinner) findViewById(R.id.source_lead);
/*

        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, status_lead_array);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        status_lead.setAdapter(adp1);
*/

        ArrayAdapter<String> adp = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, source_lead_array);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        source_lead.setAdapter(adp);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String firstame=fname.getText().toString();
                final String lastame=lname.getText().toString();
                String comapname=comapany_name.getText().toString();
                final String eid=email.getText().toString();
                String titlee=title.getText().toString();
                final String mob1=mobile_no1.getText().toString();
                String mob2=mobile_no2.getText().toString();
                final String addr1=address1.getText().toString();
                String addr2=address2.getText().toString();
                String desc=descriptn.getText().toString();
                String inds_type=Industry_type.getText().toString();
                String lead_source= source_lead.getSelectedItem().toString();
                String statu_lead= status_lead.getText().toString();
                if (leads_cla_nam.equals("Lead_Mainpage")) {

                     data_push="#"+firstame+"*"+lastame+"*"+comapname+"*"+titlee+"*"+mob1+"*"+mob2+"*"+eid+"*"+addr1+"*"+addr2+"*"+statu_lead+"*"+lead_source+"*"+inds_type+"*"+desc+"#";

                }else{

                     data_push="#"+phhnum+"*"+firstame+"*"+lastame+"*"+comapname+"*"+titlee+"*"+mob1+"*"+mob2+"*"+eid+"*"+addr1+"*"+addr2+"*"+lead_source+"*"+inds_type+"*"+statu_lead+"*"+desc+"#";

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


        if(leads_cla_nam.equals("Lead_Single_page")){
            fname.setText(namee);
            lname.setText(lnname);
            email.setText(emidd);
            address1.setText(adesss);
            Industry_type.setText(levlel);
            status_lead.setText(staats);
            mobile_no1.setText(phhnum);
        }else{
            fname.setText("");
            lname.setText("");
            email.setText("");
            address1.setText("");
            Industry_type.setText("");
            status_lead.setText("");
            mobile_no1.setText("");
        }




        status_lead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* final Dialog dialog = new Dialog(Lead_class.this);
                dialog.setContentView(R.layout.alert_lead_stage);
                dialog.setTitle("This Task is for...");
                // set the custom dialog components - text, image and button
                final Button atve = (Button) dialog.findViewById(R.id.button_active);
                final Button cold = (Button) dialog.findViewById(R.id.button_cold);
                final Button inactive = (Button) dialog.findViewById(R.id.button_inactive);


                atve.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        status_lead.setText( atve.getText().toString());
                        dialog.cancel();
                    }
                });

                cold.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        status_lead.setText( cold.getText().toString());
                        dialog.cancel();
                    }
                });

                inactive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        status_lead.setText( inactive.getText().toString());
                        dialog.cancel();
                    }
                });
                dialog.show();
*/




                final LayoutInflater inflater1 = getLayoutInflater();
                alertLayout = inflater1.inflate(R.layout.alert_lead_stage, null);
                AlertDialog.Builder alert = new AlertDialog.Builder(Lead_class.this);

                alert.setView(alertLayout);
                alert.setCancelable(true);
                final AlertDialog dialog1 = alert.create();
                dialog1.show();



                final Button atve = (Button) alertLayout.findViewById(R.id.button_active);
                final Button cold = (Button) alertLayout.findViewById(R.id.button_cold);
                final Button inactive = (Button) alertLayout.findViewById(R.id.button_inactive);
                final Button cancel = (Button) alertLayout.findViewById(R.id.cancel_alert);

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog1.cancel();
                    }
                });
                atve.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        status_lead.setText( atve.getText().toString());
                        dialog1.cancel();
                    }
                });

                cold.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        status_lead.setText( cold.getText().toString());
                        dialog1.cancel();
                    }
                });

                inactive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        status_lead.setText( inactive.getText().toString());
                        dialog1.cancel();
                    }
                });
                int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.80);
                int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.30);
                dialog1.getWindow().setLayout(width, height);
                dialog1.show();






            }
        });



        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Lead_class.this,Lead_MainPage.class);
                startActivity(in);
                finish();
            }
        });
    }


    void postt() throws JSONException {
        // Creating HTTP client
        HttpClient httpClient = new DefaultHttpClient();
        // Creating HTTP Post
        HttpPost httpPost = new HttpPost("http://edisonbro.in/crm/leads.php");

        // Building post parameters
        // key and value pair
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();


        String trdata = data_push;

        if (leads_cla_nam.equals("Lead_Mainpage")) {
            nameValuePair.add(new BasicNameValuePair("leads", trdata));
        } else {
            nameValuePair.add(new BasicNameValuePair("edit", trdata));

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
               /* JSONObject jsonObj = new JSONObject(responseBody);
                Keyword = jsonObj.getJSONArray("error");
                Log.d("TAG", "Task class notification response" + responseBody);

                if (leads_cla_nam.equals("Lead_Mainpage")) {
                    for (int i = 0; i < Keyword.length(); i++) {
                        Log.d("TAG", "Task class notification response.." + responseBody);
                        JSONObject jsonobject = Keyword.getJSONObject(i);
                        succ = jsonobject.getString("success");
                    }
                } else {
                    for (int i = 0; i < Keyword.length(); i++) {
                        Log.d("TAG", "Task class notification response.." + responseBody);
                        JSONObject jsonobject = Keyword.getJSONObject(i);
                        updnot = jsonobject.getString("upddeals");
                    }



                }*/



            }
            Log.d("TAG", responseBody);

            final String finalResponseBody = responseBody;

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(finalResponseBody.equals("success")) {
                        Toast.makeText(Lead_class.this, "Leads saved successfully", Toast.LENGTH_LONG).show();
                    }else if(finalResponseBody.equals("error")){
                        Toast.makeText(Lead_class.this, "Leads creating  error", Toast.LENGTH_LONG).show();
                    }else if(finalResponseBody.equals("client is exist")){
                        Toast.makeText(Lead_class.this, "Lead exists already", Toast.LENGTH_LONG).show();
                    }else if(finalResponseBody.equals("update successfully")){
                        Toast.makeText(Lead_class.this, "Lead updated successfully", Toast.LENGTH_LONG).show();
                    }else if(finalResponseBody.equals("update error")){
                        Toast.makeText(Lead_class.this, "Lead updating error", Toast.LENGTH_LONG).show();
                    }

                   /* if (leads_cla_nam.equals("Lead_Mainpage")) {
                        Toast.makeText(Lead_class.this, "Contacts saved successfully", Toast.LENGTH_LONG).show();
                        if(contacts_str.equals("Signin_page")){
                            Intent inte = new Intent(Lead_class.this,SignIn_page.class);
                            startActivity(inte);
                        }


                    }else if(finalResponseBody.equals("update successfully")) {
                        Toast.makeText(Lead_class.this, "Contacts edited successfully  ", Toast.LENGTH_LONG).show();
                    }else  {
                        Toast.makeText(Lead_class.this, "Error ", Toast.LENGTH_LONG).show();
                    }*/

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