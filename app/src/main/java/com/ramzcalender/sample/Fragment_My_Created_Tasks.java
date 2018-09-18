package com.ramzcalender.sample;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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

import static com.ramzcalender.sample.Activity_Fragment.class_name;
import static com.ramzcalender.sample.MainActivity.flag;
import static com.ramzcalender.sample.SignIn_page.level_number;
import static com.ramzcalender.sample.SignIn_page.login_numbr;

public class Fragment_My_Created_Tasks extends Fragment {
    Button back;
    ListView lv;
    Globals globalVariable;
    private String logn;
    private JSONArray Roomess;
    ArrayList aa, aa1, aa2, aa3, aa4, aa5, aa6, aa7;
    String name, name1, name2, name3, name4, name5, name6, name7;
    Customadapter1 disadpt;
    DataBaseHandler_new db = null;
    private SQLiteDatabase dataBase;
    private String ph;
    ArrayList<String> task_arr = new ArrayList<String>();
    ArrayList<String> date_arr = new ArrayList<String>();
    ArrayList<String> time_arr = new ArrayList<String>();
    private ArrayList<String> noti_arr = new ArrayList<String>();
    ArrayList<String> alet_arr = new ArrayList<String>();
    ArrayList<String> ph_arr = new ArrayList<String>();
    ArrayList<String> slno_arr = new ArrayList<String>();
    ArrayList<String> nam_arr = new ArrayList<String>();
    private String slno, phn, tas, dat, tim, not, ale, nam;
    private String slno1, phn1, tas1, dat1, tim1, not1, ale1, nam1;
    FloatingActionButton fb_ask;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_created_task, container, false);
        back = (Button) rootView.findViewById(R.id.butn_back_task);
        lv = (ListView) rootView.findViewById(R.id.listview_task);
        fb_ask= (FloatingActionButton) rootView.findViewById(R.id.floating_task);
        globalVariable = (Globals) getActivity().getApplicationContext();
        logn = login_numbr;

        Thread t = new Thread() {
            public void run() {
                try {
                            /*Intent in = new Intent(Task_MainPage.this, Single_task_Editing.class);
                            startActivity(in);*/

                    postt();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                if (!level_number.equals("level 1")) {
                    slno = String.valueOf(slno_arr.get(position));
                    tas = String.valueOf(task_arr.get(position));
                    dat = String.valueOf(date_arr.get(position));
                    tim = String.valueOf(time_arr.get(position));
                    not = String.valueOf(noti_arr.get(position));
                    ale = String.valueOf(alet_arr.get(position));
                    phn = String.valueOf(ph_arr.get(position));
                    nam = String.valueOf(nam_arr.get(position));


                    Intent intent = new Intent(getActivity().getApplicationContext(), Single_task_Editing.class);
                    intent.putExtra("slno", slno);
                    intent.putExtra("task", tas);
                    intent.putExtra("date", dat);
                    intent.putExtra("time", tim);
                    intent.putExtra("noti", not);
                    intent.putExtra("alert", ale);
                    intent.putExtra("phone", phn);
                    intent.putExtra("name", nam);
                    startActivity(intent);
                    Toast.makeText(getActivity().getApplicationContext(), "Slno" + slno + "phone" + phn, Toast.LENGTH_LONG).show();


                } else {


                    slno1 = String.valueOf(aa.get(position));
                    tas1 = String.valueOf(aa1.get(position));
                    dat1 = String.valueOf(aa2.get(position));
                    tim1 = String.valueOf(aa3.get(position));
                    not1 = String.valueOf(aa4.get(position));
                    ale1 = String.valueOf(aa5.get(position));
                    phn1 = String.valueOf(aa6.get(position));
                    nam1 = String.valueOf(aa7.get(position));

                    Intent intent = new Intent(getActivity().getApplicationContext(), Single_task_Editing.class);
                    intent.putExtra("slno", slno1);
                    intent.putExtra("task", tas1);
                    intent.putExtra("date", dat1);
                    intent.putExtra("time", tim1);
                    intent.putExtra("noti", not1);
                    intent.putExtra("alert", ale1);
                    intent.putExtra("phone", phn1);
                    intent.putExtra("name", nam1);
                    startActivity(intent);


                }

            }
        });


     /*  */
        return rootView;
    }
    void postt() throws JSONException {
        // Creating HTTP client
        HttpClient httpClient = new DefaultHttpClient();
        // Creating HTTP Post
        HttpPost httpPost = new HttpPost("http://edisonbro.in/crm/tasks.php");

        // Building post parameters
        // key and value pair
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
        String data1 = "#" + logn + "*" + level_number + "#";
        nameValuePair.add(new BasicNameValuePair("createtasks", data1));
      /*  String data1 = "#" + slno + "*" + phn + "#";
        if (level_number.equals("level 1")) {
            String data = logn + "*" + level_number;

            nameValuePair.add(new BasicNameValuePair("notification", data));
        } else {

            nameValuePair.add(new BasicNameValuePair("updnoti", data1));
            //   nameValuePair.add(new BasicNameValuePair("password", et_pas));
        }*/
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

                if (level_number.equals("level 1")) {

                    Roomess = jsonObj.getJSONArray("tasks");
                    aa = new ArrayList();
                    aa1 = new ArrayList();
                    aa2 = new ArrayList();
                    aa3 = new ArrayList();
                    aa4 = new ArrayList();
                    aa5 = new ArrayList();
                    aa6 = new ArrayList();
                    aa7 = new ArrayList();

                    Log.d("TAG", "Task class notification response" + responseBody);

                    for (int i = 0; i < Roomess.length(); i++) {
                        Log.d("TAG", "Task class notification response.." + responseBody);
                        JSONObject jsonobject = Roomess.getJSONObject(i);
                        name = jsonobject.getString("slno");
                        name1 = jsonobject.getString("task");
                        name2 = jsonobject.getString("date");
                        name3 = jsonobject.getString("time");
                        name4 = jsonobject.getString("noti");
                        name5 = jsonobject.getString("alert");
                        name6 = jsonobject.getString("phone");
                        name7 = jsonobject.getString("name");
                        aa.add(name);
                        aa1.add(name1);
                        aa2.add(name2);
                        aa3.add(name3);
                        aa4.add(name4);
                        aa5.add(name5);
                        aa6.add(name6);
                        aa7.add(name7);

                    }
                } else {
                    Roomess = jsonObj.getJSONArray("error");
                    for (int i = 0; i < Roomess.length(); i++) {
                        Log.d("TAG", "Task class notification response.." + responseBody);
                        JSONObject jsonobject = Roomess.getJSONObject(i);
                        name = jsonobject.getString("notification");
                    }
                }
            }
            Log.d("TAG", responseBody);

            final String finalResponseBody = responseBody;

            getActivity(). runOnUiThread(new Runnable() {
                @Override
                public void run() {

                   // if (level_number.equals("level 1")) {
                        disadpt = new Customadapter1(getActivity().getApplicationContext(), aa, aa1, aa2, aa3, aa4, aa5, aa6, aa7);
                        lv.setAdapter(disadpt);
                        ArrayList stringArray = aa;
                        Log.d("TAG", String.valueOf(stringArray));
                  //  } else {
                  //  }

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
        private ArrayList<String> fname;
        private ArrayList<String> phno;
        private ArrayList<String> lname;
        private ArrayList<String> email;
        private ArrayList<String> address;
        private ArrayList<String> noti;
        private ArrayList<String> cus_sta;
        private ArrayList<String> nama;
        private Context mContext;
        LayoutInflater layoutInflater;

        double latitute = 21.7679;
        double longitude = 78.8718;

        public Customadapter1(Context c, ArrayList<String> fnam, ArrayList<String> lname, ArrayList phno, ArrayList mail, ArrayList address, ArrayList noti, ArrayList cust_status, ArrayList nam) {
            // TODO Auto-generated constructor stub
            this.mContext = c;
            this.fname = fnam;
            this.phno = phno;
            this.lname = lname;
            this.email = mail;
            this.address = address;
            //this.noti = noti;
            this.cus_sta = cust_status;
            this.nama = nam;


        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return fname.size();
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

            TextView fname, phnumber, maill, lname, noti, levl, status;
            Button btnaddr;
        }

        public View getView(int pos, View child, ViewGroup parent) {

            Holder mHolder;
            if (child == null) {
                layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                child = layoutInflater.inflate(R.layout.single_item_task_page, null);
                mHolder = new Holder();

                //link to TextView
                mHolder.fname = (TextView) child.findViewById(R.id.fname);
                mHolder.lname = (TextView) child.findViewById(R.id.desc);
                mHolder.phnumber = (TextView) child.findViewById(R.id.phnumber);
                mHolder.maill = (TextView) child.findViewById(R.id.email);
                mHolder.levl = (TextView) child.findViewById(R.id.namm);
                mHolder.noti = (TextView) child.findViewById(R.id.noti);
                //   mHolder.adr = (TextView) child.findViewById(R.id.adress_singe);
                //  mHolder.btnaddr = (Button) child.findViewById(R.id.button_Address);


                child.setTag(mHolder);
            } else {
                mHolder = (Holder) child.getTag();
            }


            //transfer to TextView in screen
            //  mHolder.tno.setText(anum.get(pos));
            mHolder.fname.setText(email.get(pos));
            mHolder.phnumber.setText(lname.get(pos));
            mHolder.lname.setText(cus_sta.get(pos));
            mHolder.maill.setText(phno.get(pos));
            mHolder.levl.setText(nama.get(pos));
            mHolder.noti.setText(address.get(pos));
            //   mHolder.adr.setText(address.get(pos));
            //  String not =mHolder.noti.setText(address.get(pos));
            String person = address.get(pos);

            if (person.equals("0")) {

                mHolder.fname.setTextColor(Color.RED);
                mHolder.phnumber.setTextColor(Color.RED);
                mHolder.lname.setTextColor(Color.RED);
                mHolder.maill.setTextColor(Color.RED);
                mHolder.levl.setTextColor(Color.RED);

            } else {
                mHolder.fname.setTextColor(Color.GREEN);
                mHolder.phnumber.setTextColor(Color.GREEN);
                mHolder.lname.setTextColor(Color.GREEN);
                mHolder.maill.setTextColor(Color.GREEN);
                mHolder.levl.setTextColor(Color.GREEN);
            }

            return child;
        }

    }


}
