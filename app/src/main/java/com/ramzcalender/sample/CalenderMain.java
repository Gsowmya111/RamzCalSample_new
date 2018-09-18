package com.ramzcalender.sample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
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

import static com.ramzcalender.sample.SignIn_page.level_number;

/**
 * Created by edison office on 7/31/2018.
 */

public class CalenderMain extends Activity {
    CalendarView cal;
    FloatingActionButton fb_cal;
    ListView lv_event;
    private JSONArray Roomes;
    ArrayList aa, aa1, aa2, aa3, aa4, aa5, aa6, aa7,aa8,aa9;
    ArrayList bb, bb1, bb2, bb3, bb4, bb5, bb6, bb7,bb8,bb9;
    String name, name1, name2, name3, name4, name5, name6, name7,name8,name9;
    Customadapter1 disadpt;
    String date_inp ;
    Button cal_back;
    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calender_mainpage);
        cal_back= (Button) findViewById(R.id.back_cal);
        cal = (CalendarView) findViewById(R.id.calendarView1);
        fb_cal = (FloatingActionButton) findViewById(R.id.floating_calender);
        lv_event = (ListView) findViewById(R.id.list_calendr);

        fb_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inti = new Intent(CalenderMain.this, Add_appointment.class);
                startActivity(inti);
                finish();
            }
        });
        cal_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inti = new Intent(CalenderMain.this, MainActivity.class);
                startActivity(inti);
                finish();
            }
        });

        cal.setOnDateChangeListener(new OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                // TODO Auto-generated method stub

               /* Toast.makeText(getBaseContext(), "Selected Date is\n\n"
                                + dayOfMonth + "/" + month + "/"+ year,
                        Toast.LENGTH_LONG).show();*/
                int months= month+1;

             //   date_inp= + dayOfMonth + "/" + months + "/"+ year;

                date_inp= + months + "/" + dayOfMonth + "/"+ year;
                Thread t = new Thread() {
                    public void run() {
                        try {
                          //  bb.clear();bb1.clear();bb2.clear();bb3.clear();bb4.clear();bb5.clear();bb6.clear();bb7.clear();bb8.clear();bb9.clear();

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
        HttpPost httpPost = new HttpPost("http://edisonbro.in/crm/appointment.php");

        // Building post parameters
        // key and value pair
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
        nameValuePair.add(new BasicNameValuePair("appointments_list", "all_appointments"));
        //   nameValuePair.add(new BasicNameValuePair("password", et_pas));

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

                Roomes = jsonObj.getJSONArray("appointments");
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


                bb = new ArrayList();
                bb1 = new ArrayList();
                bb2 = new ArrayList();
                bb3 = new ArrayList();
                bb4 = new ArrayList();
                bb5 = new ArrayList();
                bb6 = new ArrayList();
                bb7 = new ArrayList();
                bb8 = new ArrayList();
                bb9 = new ArrayList();
                Log.d("TAG", "Task class notification response" + responseBody);

                for (int i = 0; i < Roomes.length(); i++) {
                    Log.d("TAG", "Task class notification response.." + responseBody);
                    JSONObject jsonobject = Roomes.getJSONObject(i);

                    name = jsonobject.getString("slno");
                    name1 = jsonobject.getString("from_date");
                    name2 = jsonobject.getString("from_time");
                    name3 = jsonobject.getString("to_date");
                    name4 = jsonobject.getString("to_time");
                    name5 = jsonobject.getString("name");
                    name6 = jsonobject.getString("mobile");
                    name7 = jsonobject.getString("address_line");
                    name8 = jsonobject.getString("description");
                    name9 = jsonobject.getString("title");
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

                    if(date_inp.equals(name1)){
                        bb.add(name);
                        bb1.add(name1);
                        bb2.add(name2);
                        bb3.add(name3);
                        bb4.add(name4);
                        bb5.add(name5);
                        bb6.add(name6);
                        bb7.add(name7);
                        bb8.add(name8);
                        bb9.add(name9);
                    }
                }
            }
            Log.d("TAG", responseBody);

            final String finalResponseBody = responseBody;

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                   // disadpt = new Customadapter1(getApplicationContext(), aa, aa1, aa2, aa3, aa4, aa5, aa6, aa7,aa8,aa9);

                        disadpt = new Customadapter1(getApplicationContext(), bb, bb1, bb2, bb3, bb4, bb5, bb6, bb7,bb8,bb9);
                        lv_event.setAdapter(disadpt);
                        ArrayList stringArray = aa;
                        Log.d("TAG", String.valueOf(stringArray));
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

    class Customadapter1 extends BaseAdapter {
        ArrayList result;
        Activity_Fragment context;
        private ArrayList<String> slno;
        private ArrayList<String> from_dat;
        private ArrayList<String> from_tie;
        private ArrayList<String> to_dat;
        private ArrayList<String> to_tim;
        private ArrayList<String> namee;
        private ArrayList<String> mob;
        private ArrayList<String> addres_lin;
        private ArrayList<String> desc;
        private ArrayList<String> titee;
        private Context mContext;
        LayoutInflater layoutInflater;

        double latitute = 21.7679;
        double longitude = 78.8718;

        public Customadapter1(Context c, ArrayList<String> slno, ArrayList<String> fromdat, ArrayList fromtim, ArrayList todat, ArrayList totime, ArrayList nam, ArrayList mobl, ArrayList ad_line, ArrayList des, ArrayList tit) {
            // TODO Auto-generated constructor stub
            this.mContext = c;
            this.slno = slno;
            this.from_dat = fromdat;
            this.from_tie = fromtim;
            this.to_dat = todat;
            this.to_tim = totime;
            this.namee = nam;
            this.mob = mobl;
            this.addres_lin = ad_line;
            this.desc = des;
            this.titee = tit;

        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return slno.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        public class Holder {

            TextView slnoo, from_timee, from_datee, to_timee, to_datee, nmaee, mobee,desee,titee,adree;
            Button btnaddr;
        }

        public View getView(int pos, View child, ViewGroup parent) {

            Holder mHolder;
            if (child == null) {
                layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                child = layoutInflater.inflate(R.layout.single_item_appoint_page, null);
                mHolder = new Holder();

                //link to TextView
                mHolder.titee = (TextView) child.findViewById(R.id.fname);
                mHolder.nmaee = (TextView) child.findViewById(R.id.desc);
                mHolder.mobee = (TextView) child.findViewById(R.id.phnumber);
                mHolder.adree = (TextView) child.findViewById(R.id.email);
                mHolder.from_timee = (TextView) child.findViewById(R.id.namm);
                mHolder.from_datee = (TextView) child.findViewById(R.id.noti);
                //   mHolder.adr = (TextView) child.findViewById(R.id.adress_singe);
                //  mHolder.btnaddr = (Button) child.findViewById(R.id.button_Address);


                child.setTag(mHolder);
            } else {
                mHolder = (Holder) child.getTag();
            }


            //transfer to TextView in screen
            //  mHolder.tno.setText(anum.get(pos));
            mHolder.titee.setText(titee.get(pos));
            mHolder.nmaee.setText(namee.get(pos));
            mHolder.mobee.setText(mob.get(pos));
            mHolder.adree.setText(addres_lin.get(pos));
            mHolder.from_timee.setText(from_tie.get(pos));
            mHolder.from_datee.setText(from_dat.get(pos));
            //   mHolder.adr.setText(address.get(pos));
            //  String not =mHolder.noti.setText(address.get(pos));

            return child;
        }

    }


}
