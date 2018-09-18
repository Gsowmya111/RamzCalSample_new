package com.ramzcalender.sample;

/**
 * Created by edison office on 8/18/2018.
 */
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.ramzcalender.sample.SignIn_page.contacts_str;
import static com.ramzcalender.sample.SignIn_page.level_number;
import static com.ramzcalender.sample.SignIn_page.owner_name;

public class Chose_Contacts_for_task extends AppCompatActivity{


    RecyclerView recyclerview;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    ArrayList aa, aa1, aa2, aa3, aa4, aa5, aa6,aa7;
    String name, name1, name2, name3, name4, name5, name6,name7;
    ListView listview;
    private JSONArray Roomes;

    public static int flag;

    FloatingActionButton fb;
    DataBaseHandler db = null;
    Button task_main,lead_main,contacts_main,deal_main,calendr_main,cancel_main,plus_main,btn_logout;
    public  static  String Activity_fragm;
    private String level_num;
    private String dataa;
    private String owne;


    String fname,lanam,emaill_id,ph,customerstatus,ades,levll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose__contacts_for_task);

        recyclerview = (RecyclerView)findViewById(R.id.recyclerview1);

        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerview.setLayoutManager(RecyclerViewLayoutManager);
        level_num=level_number;

        Thread t = new Thread() {
            public void run() {
                try {
                    dataa= "all_contacts"+"*"+level_num+"*"+owner_name;
                    postt();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();


    }




    void postt() throws JSONException {
        // Creating HTTP client
        HttpClient httpClient = new DefaultHttpClient();
        // Creating HTTP Post
        HttpPost httpPost = new HttpPost("http://edisonbro.in/crm/insert_contacts.php");

        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
        nameValuePair.add(new BasicNameValuePair("cont", dataa));
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
                Roomes = jsonObj.getJSONArray("contacts");
                aa = new ArrayList();
                aa1 = new ArrayList();
                aa2 = new ArrayList();
                aa3 = new ArrayList();

                aa4 = new ArrayList();
                aa5 = new ArrayList();
                aa6 = new ArrayList();
                aa7 = new ArrayList();
                for (int i = 0; i < Roomes.length(); i++) {
                    JSONObject jsonobject = Roomes.getJSONObject(i);

                    name = jsonobject.getString("first_name");
                    name1 = jsonobject.getString("last_name");
                    name2 = jsonobject.getString("mobile_no1");
                    name3 = jsonobject.getString("email_id");
                    name4 = jsonobject.getString("address_line1");
                    name5 = jsonobject.getString("hierarchy_level");
                    name6 = jsonobject.getString("customer_status");
                    name7 = jsonobject.getString("ownername");
                    aa.add(name);
                    aa1.add(name1);
                    aa2.add(name2);
                    aa3.add(name3);
                    aa4.add(name4);
                    aa5.add(name5);
                    aa6.add(name6);
                    aa7.add(name7);

                    // if(name7.equals())
                }

                Log.d("TAG", "aa: " + aa);
                Log.d("TAG", "bb: " + aa1);
                Log.d("TAG", "cc: " + aa2);

                Log.d("TAG", "Http post Response: " + responseBody);
            }
            Log.d("TAG", responseBody);

            final String finalResponseBody = responseBody;

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    RecyclerView.Adapter adapter = new RecyclerViewAdapter(aa,aa1,aa2,aa3,aa4,aa5,aa6,aa7);

                    recyclerview.setAdapter(adapter);


                    //   tv.setText(finalResponseBody);
                   /* disadpt = new Customadapter(getActivity().getApplicationContext(), aa, aa1, aa2, aa3,aa4,aa5,aa6);
                    listview.setAdapter(disadpt);
                    ArrayList stringArray = aa;
                    Log.d("TAG", String.valueOf(stringArray));*/
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


/*
    @Override
    public void onClick(View v) {
        if (v.getId() == task_main.getId()) {
            contacts_str="task_clicked";

            Intent i1 = new Intent(Chose_Contacts_for_task.this, Task_MainPage.class);
            startActivity(i1);
            overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
        }
        else if (v.getId() == lead_main.getId()) {
            Intent i2 = new Intent(Chose_Contacts_for_task.this, Lead_MainPage.class);
            startActivity(i2);
            overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
        }
        else if (v.getId() == btn_logout.getId()) {
            level_number="0";
            Intent i3 = new Intent(MainActivity.this, SignIn_page.class);
            startActivity(i3);

        } else if (v.getId() == deal_main.getId()) {
            Intent i4 = new Intent(MainActivity.this, Deals_main_page.class);
            startActivity(i4);
            overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
        } else if (v.getId() == calendr_main.getId()) {
            Intent i5 = new Intent(MainActivity.this, CalenderMain.class);
            startActivity(i5);
            overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
        }else if (v.getId() == btn_logout.getId()) {
            Intent i6 = new Intent(MainActivity.this, SignIn_page.class);
            startActivity(i6);

        }else if (v.getId() == contacts_main.getId()) {
            Intent i6 = new Intent(MainActivity.this, MainActivity.class);
            startActivity(i6);

        }
    }*/
    class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>  {

        ArrayList<String> firstname;
        ArrayList<String> lname;
        ArrayList<String> mobnum;
        ArrayList<String> email;
        ArrayList<String> addres;
        ArrayList<String> levl;
        ArrayList<String> stats;
        ArrayList<String> owner_nam;

        View view1;

        public RecyclerViewAdapter(ArrayList fnma, ArrayList lnam, ArrayList mob, ArrayList amil, ArrayList ades, ArrayList levl, ArrayList<String> stat,ArrayList owner ) {

            this.firstname = fnma;
            this.lname = lnam;
            this.mobnum = mob;
            this.email = amil;
            this.addres = ades;
            this.levl = levl;
            this.stats = stat;
            this.owner_nam = owner;
        }

        @Override
        public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

            view1  = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row, viewGroup, false);

            return new RecyclerViewAdapter.ViewHolder(view1);
        }

        @Override
        public void onBindViewHolder(RecyclerViewAdapter.ViewHolder Viewholder, final int i) {

            Viewholder.fnam.setText(firstname.get(i));
            Viewholder.lnam.setText(lname.get(i));
            Viewholder.mob.setText(mobnum.get(i));
            Viewholder.emai.setText(email.get(i));
            Viewholder.adres.setText(addres.get(i));
            Viewholder.stats.setText(levl.get(i));
            Viewholder.level.setText(stats.get(i));
            Viewholder.owner_nam.setText(owner_nam.get(i));
            Viewholder.img.setText("");
            Viewholder.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    fname = String.valueOf(aa.get(i));
                    lanam = String.valueOf(aa1.get(i));
                    ph = String.valueOf(aa2.get(i));
                    emaill_id = String.valueOf(aa3.get(i));
                    ades = String.valueOf(aa4.get(i));
                    levll = String.valueOf(aa5.get(i));
                    customerstatus = String.valueOf(aa6.get(i));

                    Intent intent = new Intent(Chose_Contacts_for_task.this, SingleContact_MainPage.class);
                    intent.putExtra("fnam", fname);
                    intent.putExtra("lnam", lname);
                    intent.putExtra("phh", ph);
                    intent.putExtra("mai", emaill_id);
                    intent.putExtra("add", ades);
                    intent.putExtra("lev", levll);
                    intent.putExtra("stat", customerstatus);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {

            return aa.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{

            TextView fnam,lnam,mob,emai,adres,stats,level,owner_nam,img;

            public ViewHolder(View view) {

                super(view);

                fnam = (TextView)view.findViewById(R.id.fnamee);
                lnam = (TextView)view.findViewById(R.id.laname);
                mob = (TextView)view.findViewById(R.id.moble);
                emai = (TextView)view.findViewById(R.id.email);
                adres = (TextView)view.findViewById(R.id.adress);
                stats = (TextView)view.findViewById(R.id.stats);
                level = (TextView)view.findViewById(R.id.leval);
                owner_nam = (TextView)view.findViewById(R.id.owner);
                img= (TextView) view.findViewById(R.id.img_detail);

                /* img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = getAdapterPosition();
                        fname = String.valueOf(aa.get(pos));
                        lanam = String.valueOf(aa1.get(pos));
                        ph = String.valueOf(aa2.get(pos));
                        emaill_id = String.valueOf(aa3.get(pos));
                        ades = String.valueOf(aa4.get(pos));
                        levll = String.valueOf(aa5.get(pos));
                        customerstatus = String.valueOf(aa6.get(pos));

                        Intent intent = new Intent(MainActivity.this, SingleContact_MainPage.class);
                        intent.putExtra("fnam", fname);
                        intent.putExtra("lnam", lname);
                        intent.putExtra("phh", ph);
                        intent.putExtra("mai", emaill_id);
                        intent.putExtra("add", ades);
                        intent.putExtra("lev", levl);
                        intent.putExtra("stat", customerstatus);
                       startActivity(intent);
                    }
                });*/


                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = getAdapterPosition();
                        level_num = String.valueOf(aa5.get(pos));
                        owne = String.valueOf(aa2.get(pos));
                        Thread t = new Thread() {
                            public void run() {
                                try {

                                    dataa= "all_contacts"+"*"+level_num+"*"+owne;
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
        }

    }

}