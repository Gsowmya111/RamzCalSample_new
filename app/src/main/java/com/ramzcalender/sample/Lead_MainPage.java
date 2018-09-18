package com.ramzcalender.sample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
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

import static com.ramzcalender.sample.MainActivity.row_item_id;

/**
 * Created by edison office on 7/17/2018.
 */
public class Lead_MainPage extends Activity  {
    FloatingActionButton fb_lead;
    public static String leads_cla_nam,alert_clas;
    private JSONArray Roomes;

    ArrayList aa, aa1, aa2, aa3, aa4, aa5, aa6, aa7, aa8, aa9;
    String name, name1, name2, name3, name4, name5, name6, name7, name8, name9;
    String f_name, l_name, ph_lead, emaill_id, adess, sourrce, stattus, inds;
    ListView lv_lead;
    Button back_lead;
    RecyclerView recyclerview;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lead_mainpage);
        fb_lead = (FloatingActionButton) findViewById(R.id.floating_leads);

        back_lead= (Button) findViewById(R.id.back_lead);
        alert_clas="lead";
        row_item_id="leads_pagee";

        recyclerview = (RecyclerView)findViewById(R.id.recyclerview_lead);

        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerview.setLayoutManager(RecyclerViewLayoutManager);



        fb_lead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leads_cla_nam = "Lead_Mainpage";

                Intent intentt = new Intent(Lead_MainPage.this, Lead_class.class);
                startActivity(intentt);
                finish();
            }
        });
        back_lead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(Lead_MainPage.this, MainActivity.class);
                startActivity(inten);
                finish();
            }
        });

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

    /*
    recyclerview.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                f_name = String.valueOf(aa.get(position));
                l_name = String.valueOf(aa1.get(position));
                ph_lead = String.valueOf(aa4.get(position));
                emaill_id = String.valueOf(aa5.get(position));
                adess = String.valueOf(aa6.get(position));
                sourrce = String.valueOf(aa7.get(position));
                stattus = String.valueOf(aa8.get(position));
                inds = String.valueOf(aa9.get(position));

                Intent intent = new Intent(Lead_MainPage.this, SingleLead_MainPage.class);
                intent.putExtra("fnam", f_name);
                intent.putExtra("lnam", l_name);
                intent.putExtra("phh", ph_lead);
                intent.putExtra("mai", emaill_id);
                intent.putExtra("add", adess);
                intent.putExtra("sour", sourrce);
                intent.putExtra("stat", stattus);
                intent.putExtra("inds", inds);
                startActivity(intent);


            }
        });

*/
   // }


    void postt() throws JSONException {
        // Creating HTTP client
        HttpClient httpClient = new DefaultHttpClient();
        // Creating HTTP Post
        HttpPost httpPost = new HttpPost("http://edisonbro.in/crm/leads.php");


        // Building post parameters
        // key and value pair
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
        nameValuePair.add(new BasicNameValuePair("allleads", "all_leads"));
        // nameValuePair.add(new BasicNameValuePair("status", "sd"));
        // Url Encoding the POST parameters
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
                JSONObject jsonObj = new JSONObject(responseBody);
                Roomes = jsonObj.getJSONArray("leadstable");
                aa = new ArrayList();
                aa1 = new ArrayList();
                aa2 = new ArrayList();
                aa3 = new ArrayList();
                aa4 = new ArrayList();
                aa5 = new ArrayList();
                aa6 = new ArrayList();
                aa7 = new ArrayList();
                aa8 = new ArrayList();
                aa9 = new ArrayList();
                for (int i = 0; i < Roomes.length(); i++) {
                    JSONObject jsonobject = Roomes.getJSONObject(i);

                    name = jsonobject.getString("first_name");
                    name1 = jsonobject.getString("last_name");
                    name2 = jsonobject.getString("company_name");
                    name3 = jsonobject.getString("title");
                    name4 = jsonobject.getString("mobile_no1");
                    name5 = jsonobject.getString("email_id");
                    name6 = jsonobject.getString("address_line1");
                    name7 = jsonobject.getString("source");
                    name8 = jsonobject.getString("customer_status");
                    name9 = jsonobject.getString("industry");
                    aa.add(name);
                    aa1.add(name1);
                    aa2.add(name2);
                    aa3.add(name3);
                    aa4.add(name4);
                    aa5.add(name5);
                    aa6.add(name6);
                    aa7.add(name7);
                    aa8.add(name8);
                    aa9.add(name9);
                }


                Log.d("TAG", "Http post Response: " + responseBody);
            }
            Log.d("TAG", responseBody);

            final String finalResponseBody = responseBody;

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                   /* //   tv.setText(finalResponseBody);
                    disadpt = new Customadapter(Lead_MainPage.this, aa, aa1, aa4, aa5, aa6, aa7, aa8, aa9);
                    lv_lead.setAdapter(disadpt);*/


                    RecyclerView.Adapter adapter = new RecyclerViewAdapter(aa,aa1,aa4,aa5,aa6,aa7,aa8,aa9);

                    recyclerview.setAdapter(adapter);



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

    class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>  {

        ArrayList<String> firstname;
        ArrayList<String> lname;
        ArrayList<String> mobnum;
        ArrayList<String> email;
        ArrayList<String> addres;
        ArrayList<String> levl;
        ArrayList<String> stats;
        ArrayList<String> ins;
        ArrayList<String> sorc;


        View view1;

        public RecyclerViewAdapter(ArrayList fnma, ArrayList lnam, ArrayList mob, ArrayList amil, ArrayList ades, ArrayList levl, ArrayList<String> stat, ArrayList sour) {

            this.firstname = fnma;
            this.lname = lnam;
            this.mobnum = mob;
            this.email = amil;
            this.addres = ades;
            this.levl = levl;
            this.stats = stat;
          //  this.ins = indu;
            this.sorc = sour;
        }

        @Override
        public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

            view1  = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row, viewGroup, false);

            return new RecyclerViewAdapter.ViewHolder(view1);
        }

        @Override
        public void onBindViewHolder(RecyclerViewAdapter.ViewHolder Viewholder, int i) {


            Viewholder.fnam.setText(firstname.get(i));
            Viewholder.lnam.setText(lname.get(i));
            Viewholder.mob.setText(mobnum.get(i));
            Viewholder.emai.setText(email.get(i));
            Viewholder.adres.setText(addres.get(i));
            Viewholder.stats.setText(levl.get(i));
            Viewholder.level.setText(stats.get(i));




        }

        @Override
        public int getItemCount() {

            return aa.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder
            {

            TextView fnam,lnam,mob,emai,adres,stats,level,item_row_id1;
            public ViewHolder(View view) {

                super(view);

                fnam = (TextView)view.findViewById(R.id.fnamee);
                lnam = (TextView)view.findViewById(R.id.laname);
                mob = (TextView)view.findViewById(R.id.moble);
                emai = (TextView)view.findViewById(R.id.email);
                adres = (TextView)view.findViewById(R.id.adress);
                stats = (TextView)view.findViewById(R.id.stats);
                level = (TextView)view.findViewById(R.id.leval);
                item_row_id1 = (TextView)view.findViewById(R.id.img_detail);

                if( row_item_id.equals("leads_pagee")){
                    item_row_id1.setVisibility(View.INVISIBLE);
                }

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = getAdapterPosition();
                        Toast.makeText(v.getContext(), "You clicked " + pos, Toast.LENGTH_SHORT).show();


                        f_name = String.valueOf(aa.get(pos));
                        l_name = String.valueOf(aa1.get(pos));
                        ph_lead = String.valueOf(aa4.get(pos));
                        emaill_id = String.valueOf(aa5.get(pos));
                        adess = String.valueOf(aa6.get(pos));
                        sourrce = String.valueOf(aa7.get(pos));
                        stattus = String.valueOf(aa8.get(pos));
                        inds = String.valueOf(aa9.get(pos));

                        Intent intent = new Intent(Lead_MainPage.this, SingleLead_MainPage.class);
                        intent.putExtra("fnam", f_name);
                        intent.putExtra("lnam", l_name);
                        intent.putExtra("phh", ph_lead);
                        intent.putExtra("mai", emaill_id);
                        intent.putExtra("add", adess);
                        intent.putExtra("sour", sourrce);
                        intent.putExtra("stat", stattus);
                        intent.putExtra("inds", inds);
                        startActivity(intent);



                    }
                });
            }


            }

    }


}
