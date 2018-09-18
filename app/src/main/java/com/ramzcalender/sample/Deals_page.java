package com.ramzcalender.sample;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
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
import java.util.Date;
import java.util.List;

import static com.ramzcalender.sample.Activity_Fragment.class_name;
import static com.ramzcalender.sample.Choose_Contacts_for_task.mobile_str;
import static com.ramzcalender.sample.Choose_Contacts_for_task.name_str1;
import static com.ramzcalender.sample.Choose_Contacts_for_task.name_str2;
import static com.ramzcalender.sample.Deals_main_page.str;
import static com.ramzcalender.sample.MainActivity.Activity_fragm;


import static com.ramzcalender.sample.SignIn_page.contacts_str;
import static com.ramzcalender.sample.SignIn_page.login_numbr;
import static com.ramzcalender.sample.SignIn_page.owner_name;
import static com.ramzcalender.sample.SingleDeal_MainPage.amt_deal;
import static com.ramzcalender.sample.SingleDeal_MainPage.curr_deal;
import static com.ramzcalender.sample.SingleDeal_MainPage.des_deal;
import static com.ramzcalender.sample.SingleDeal_MainPage.phnoo_deal;
import static com.ramzcalender.sample.SingleDeal_MainPage.ref_deal;
import static com.ramzcalender.sample.SingleDeal_MainPage.slno_deal;
import static com.ramzcalender.sample.SingleDeal_MainPage.sor_deal;
import static com.ramzcalender.sample.Task_class.task_activity;

/**
 * Created by edison office on 7/17/2018.
 */
public class Deals_page extends Activity{
    Button save,cancel;
    Spinner source_deal;
    TextView stage_deal,associate_deal,owner_deal;
    EditText deal_name,amount_deal;
    Switch hot;
    Globals globalVariable;
    CharSequence deal_created;
    String[] stage_deal_array = new String[] {
            "Appointment Sheduled", " Qualified to buy", " Closure won", "Closure lost"
    };
    String[] source_deal_array = new String[] {
            "Newspaper ad","Word of mouth","Refferal","Our website"
    };
    private String hot_string;
    private String de_nam,amount,assoc,stag,sour,ph;
    private String data_send;
    private JSONArray Keyword;
    private String updnot, succ;
    View alertLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deals);
        save = (Button) findViewById(R.id.save_btn_deal);
        cancel = (Button) findViewById(R.id.cancel_btn_deal);
        stage_deal= (TextView) findViewById(R.id.stage_deal);
        source_deal= (Spinner) findViewById(R.id.source_deal);
        associate_deal= (TextView) findViewById(R.id.associationwith_deal);
        owner_deal= (TextView) findViewById(R.id.owner_deal);
        deal_name= (EditText) findViewById(R.id.Deal_name);
        amount_deal= (EditText) findViewById(R.id.Amount_deal);
        hot = (Switch) findViewById(R.id.hot_switch);

        globalVariable = (Globals) getApplicationContext();
        owner_deal.setText(owner_name);

        Date d = new Date();
        deal_created  = DateFormat.format("MMM d, yyyy ", d.getTime());
       // Toast.makeText(Deals_page.this, s , Toast.LENGTH_LONG).show();

        ArrayAdapter<String> adp = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, source_deal_array);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        source_deal.setAdapter(adp);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Deals_page.this,Deals_main_page.class);
                startActivity(in);
                finish();
            }
        });
        owner_deal.setText(login_numbr);
        if( str .equals("sdmp")){

            stage_deal.setText(curr_deal);
            associate_deal.setText(ref_deal);
            amount_deal.setText(amt_deal);
            deal_name.setText(des_deal);
            owner_deal.setText(owner_name);

        }else {
            stage_deal.setText("");
            associate_deal.setText("");
            amount_deal.setText("");
            amount_deal.setText("");
            owner_deal.setText("");
        }


       /* des_deal = tv_des_deal.getText().toString();
        amt_deal = tv_amt_deal.getText().toString();

        ref_deal = tv_ref_deal.getText().toString();
        curr_deal = tv_curr_deal.getText().toString();
        sor_deal = tv_sour_deal.getText().toString();
        dat_deal = tv_dat_dal.getText().toString();
*/



        stage_deal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*final Dialog dialog = new Dialog(Deals_page.this);
                dialog.setContentView(R.layout.alert_deal_stage);
                dialog.setTitle("This Task is for...");
                // set the custom dialog components - text, image and button
                final Button pros = (Button) dialog.findViewById(R.id.button_prospe);
                final Button quali = (Button) dialog.findViewById(R.id.button_qualified);
                final Button quote = (Button) dialog.findViewById(R.id.button_quote);
                final Button closre = (Button) dialog.findViewById(R.id.button_closure);
                final Button won = (Button) dialog.findViewById(R.id.button_won);
                final Button unquali = (Button) dialog.findViewById(R.id.button_unqualified);
                final Button lost = (Button) dialog.findViewById(R.id.button_lost);
                //  Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                // if button is clicked, close the custom dialog
                pros.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                  stage_deal.setText( pros.getText().toString());
                        dialog.cancel();

                    }
                });
                quali.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        stage_deal.setText( quali.getText().toString());
                        dialog.cancel();
                    }
                });
                quote.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        stage_deal.setText( quote.getText().toString());
                        dialog.cancel();
                    }
                });
                closre.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        stage_deal.setText( closre.getText().toString());
                        dialog.cancel();
                    }
                });
                won.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        stage_deal.setText( won.getText().toString());
                        dialog.cancel();
                    }
                });

                unquali.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        stage_deal.setText( unquali.getText().toString());
                        dialog.cancel();
                    }
                });

                lost.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        stage_deal.setText( lost.getText().toString());
                        dialog.cancel();
                    }
                });

                int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.66);
                int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.40);
                dialog.getWindow().setLayout(width, height);
                dialog.show();*/



                final LayoutInflater inflater1 = getLayoutInflater();
                alertLayout = inflater1.inflate(R.layout.alert_deal_stage, null);
                AlertDialog.Builder alert = new AlertDialog.Builder(Deals_page.this);

                alert.setView(alertLayout);
                alert.setCancelable(true);
                final AlertDialog dialog1 = alert.create();
                dialog1.show();



                final Button pros = (Button) alertLayout.findViewById(R.id.button_prospe);
                final Button quali = (Button) alertLayout.findViewById(R.id.button_qualified);
                final Button quote = (Button) alertLayout.findViewById(R.id.button_quote);
                final Button closre = (Button) alertLayout.findViewById(R.id.button_closure);
                final Button won = (Button) alertLayout.findViewById(R.id.button_won);
                final Button unquali = (Button) alertLayout.findViewById(R.id.button_unqualified);
                final Button lost = (Button) alertLayout.findViewById(R.id.button_lost);
              //  final Button cancel = (Button) alertLayout.findViewById(R.id.cancel_alert);

               /* cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog1.cancel();
                    }
                });*/
                pros.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        stage_deal.setText( pros.getText().toString());
                        dialog1.cancel();

                    }
                });
                quali.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        stage_deal.setText( quali.getText().toString());
                        dialog1.cancel();
                    }
                });
                quote.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        stage_deal.setText( quote.getText().toString());
                        dialog1.cancel();
                    }
                });
                closre.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        stage_deal.setText( closre.getText().toString());
                        dialog1.cancel();
                    }
                });
                won.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        stage_deal.setText( won.getText().toString());
                        dialog1.cancel();
                    }
                });

                unquali.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        stage_deal.setText( unquali.getText().toString());
                        dialog1.cancel();
                    }
                });

                lost.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        stage_deal.setText( lost.getText().toString());
                        dialog1.cancel();
                    }
                });

                int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.75);
                int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.62);
                dialog1.getWindow().setLayout(width, height);
                dialog1.show();

            }
        });
        associate_deal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task_activity="Deal_page";
                Intent ii= new Intent(Deals_page.this,Choose_Contacts_for_task.class);
                startActivity(ii);


            }
        });
        if (Activity_fragm.equals("Choose_contact")) {
            owner_deal.setText(owner_name);
            associate_deal.setText(name_str1 + " " + name_str2);
            deal_name.setText(globalVariable.getDeal_nam_var().toString());
            amount_deal.setText(globalVariable.getAmout_var().toString());

        } else if (Activity_fragm.equals("Main_cls")) {
          //  associate_deal.setText("");
        }



        deal_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                globalVariable.setDeal_nam_var(deal_name.getText().toString());
                //  globalVariable.setEmail("xxxxxx@aaaa.com");
            }
        });

        amount_deal.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                globalVariable.setAmout_var(amount_deal.getText().toString());
                //  globalVariable.setEmail("xxxxxx@aaaa.com");
            }
        });


        if(hot.isChecked()) {
            hot_string = "Hot";
           hot.setChecked(true);
            // Toast.makeText(Deals_page.this, "Task creation error "+hot_string , Toast.LENGTH_LONG).show();
        }else{
            hot_string = "No";
            hot.setChecked(false);
            // Toast.makeText(Deals_page.this, "Task creation error "+ hot_string , Toast.LENGTH_LONG).show();
        }



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              de_nam= deal_name.getText().toString();
                amount= amount_deal.getText().toString();
                assoc=associate_deal.getText().toString();
                stag= stage_deal.getText().toString();
                sour= source_deal.getSelectedItem().toString();
                ph=mobile_str;


                if (contacts_str.equals("Deal_main_page")) {
                    data_send = "#" + de_nam + "*" + amount + "*" + hot_string + "*" + ph + "*" + stag + "*" + sour + "*" + owner_name + "*" + deal_created + "#";
                }else{
                    data_send = "#" + de_nam + "*" + amount + "*" + hot_string + "*" + phnoo_deal + "*" + stag + "*" + sour + "*" + owner_name + "*" + deal_created + "*"+slno_deal+"#";

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



}

    void postt() throws JSONException {
        // Creating HTTP client
        HttpClient httpClient = new DefaultHttpClient();
        // Creating HTTP Post
        HttpPost httpPost = new HttpPost("http://edisonbro.in/crm/deal.php");

        // Building post parameters
        // key and value pair
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();


        String trdata = data_send;

        if (contacts_str.equals("Deal_main_page")) {
            nameValuePair.add(new BasicNameValuePair("deals", trdata));
        } else {
            nameValuePair.add(new BasicNameValuePair("upddeals", trdata));

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

                    if (contacts_str.equals("Deal_main_page")) {
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



                }
            }
            Log.d("TAG", responseBody);

            final String finalResponseBody = responseBody;

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (contacts_str.equals("Deal_main_page")) {
                         if (succ.equals("false")) {
                        Toast.makeText(Deals_page.this, "Deal created success ", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(Deals_page.this, "Deal creation error ", Toast.LENGTH_LONG).show();
                    }

                    } else {
                        if (updnot.equals("false")) {
                            Toast.makeText(Deals_page.this, "Deal updated success ", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(Deals_page.this, "Deal updation  error ", Toast.LENGTH_LONG).show();
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
