package com.ramzcalender.sample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import static com.ramzcalender.sample.MainActivity.Activity_fragm;
import static com.ramzcalender.sample.MainActivity.flag;
import static com.ramzcalender.sample.SignIn_page.level_number;
import static com.ramzcalender.sample.SignIn_page.owner_name;
import static com.ramzcalender.sample.Task_class.task_activity;

public class Choose_Contacts_for_task extends Activity {
    ArrayList aa, aa1, aa2, aa3, aa4, aa5, aa6;
    String name, name1, name2, name3, name4, name5, name6;
    ListView listview;
    private JSONArray Roomes;
    Customadapter1 disadpt;
    public static String name_str1,name_str2,mobile_str,class_name;
    Globals globalVariable;
    String fname1,lname1,emaill_id1,ph1,customerstatus1,ades1,levl1;
    private String level_num,dataa;
    private String owne;
    Button back_chose_cont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        globalVariable = (Globals) getApplicationContext();
        setContentView(R.layout.activity_choose__contacts_for_task);
        listview = (ListView) findViewById(R.id.listview);
        back_chose_cont= (Button) findViewById(R.id.back_choose_cont);
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

        flag=1;


        back_chose_cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i11 = new Intent(Choose_Contacts_for_task.this,Task_class.class);
                startActivity(i11);


               /* Thread t = new Thread() {
                    public void run() {
                        try {

                            dataa= "all_contacts"+"*"+level_num+"*"+owner_name;
                            postt();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                t.start();*/
            }
        });



        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String c =globalVariable.getClass_names();
                level_num = String.valueOf(aa5.get(position));
                owne = String.valueOf(aa2.get(position));

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
    void postt() throws JSONException {
        // Creating HTTP client
        HttpClient httpClient = new DefaultHttpClient();
        // Creating HTTP Post
        HttpPost httpPost = new HttpPost("http://edisonbro.in/crm/insert_contacts.php");


        // Building post parameters
        // key and value pair
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
        nameValuePair.add(new BasicNameValuePair("cont", dataa));
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
                Roomes = jsonObj.getJSONArray("contacts");
                aa = new ArrayList();
                aa1 = new ArrayList();
                aa2 = new ArrayList();
                aa3 = new ArrayList();

                aa4 = new ArrayList();
                aa5 = new ArrayList();
                aa6 = new ArrayList();
                for (int i = 0; i < Roomes.length(); i++) {
                    JSONObject jsonobject = Roomes.getJSONObject(i);

                    name = jsonobject.getString("first_name");
                    name1 = jsonobject.getString("last_name");
                    name2 = jsonobject.getString("mobile_no1");
                    name3 = jsonobject.getString("email_id");
                    name4 = jsonobject.getString("address_line1");
                    name5 = jsonobject.getString("hierarchy_level");
                    name6 = jsonobject.getString("customer_status");
                    aa.add(name);
                    aa1.add(name1);
                    aa2.add(name2);
                    aa3.add(name3);
                    aa4.add(name4);
                    aa5.add(name5);
                    aa6.add(name6);
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

                    //   tv.setText(finalResponseBody);
                    disadpt = new Customadapter1(Choose_Contacts_for_task.this, aa, aa1, aa2, aa3,aa4,aa5,aa6);
                    listview.setAdapter(disadpt);
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
        private ArrayList<String> fname;
        private ArrayList<String> phno;
        private ArrayList<String> lname;
        private ArrayList<String> email;
        private ArrayList<String> address;
        private ArrayList<String> hir_lev;
        private ArrayList<String> cus_sta;

        private Context mContext;
        LayoutInflater layoutInflater;

        double latitute = 21.7679;
        double longitude = 78.8718;

        public Customadapter1(Context c,  ArrayList<String> fnam, ArrayList<String> lname, ArrayList phno, ArrayList mail,ArrayList address, ArrayList level, ArrayList cust_status) {
            // TODO Auto-generated constructor stub
            this.mContext = c;
            this.fname = fnam;
            this.phno = phno;
            this.lname = lname;
            this.email = mail;
            this.address=address;
            this.hir_lev=level;
            this.cus_sta=cust_status;


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

            TextView fname, phnumber, maill, lname, adr,levl,status;
            Button btnaddr;
        }

        public View getView(final int pos, View child, ViewGroup parent) {
            Holder mHolder;

            if (child == null) {
                layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                child = layoutInflater.inflate(R.layout.single_item_chose_cont, null);
                mHolder = new Holder();

                //link to TextView
                mHolder.fname = (TextView) child.findViewById(R.id.fname);
                // mHolder.lname = (TextView) child.findViewById(R.id.lname);
                mHolder.phnumber = (TextView) child.findViewById(R.id.phnumber);
                mHolder.maill = (TextView) child.findViewById(R.id.email);
                mHolder.levl = (TextView) child.findViewById(R.id.level_single);
                mHolder.status = (TextView) child.findViewById(R.id.status_single);
                mHolder.adr = (TextView) child.findViewById(R.id.adress_singe);
                //  mHolder.btnaddr = (Button) child.findViewById(R.id.button_Address);


                child.setTag(mHolder);
            } else {
                mHolder = (Holder) child.getTag();
            }


            //transfer to TextView in screen
            //  mHolder.tno.setText(anum.get(pos));
            mHolder.fname.setText(fname.get(pos) + "  " + lname.get(pos));
            mHolder.phnumber.setText(phno.get(pos));
            mHolder.levl.setText(hir_lev.get(pos));
            mHolder.maill.setText(email.get(pos));
           // mHolder.status.setText(cus_sta.get(pos));
            mHolder.adr.setText(address.get(pos));


            mHolder.status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   fname1 = String.valueOf(aa.get(pos));
                lname1 = String.valueOf(aa1.get(pos));
                ph1 = String.valueOf(aa2.get(pos));
                emaill_id1 = String.valueOf(aa3.get(pos));
                ades1 = String.valueOf(aa4.get(pos));
                levl1 = String.valueOf(aa5.get(pos));
                customerstatus1 = String.valueOf(aa6.get(pos));
                mobile_str = ph1;
                Activity_fragm = "Choose_contact";
             //   task_activity = "Task_class";
                    if(task_activity.equals("Task_class")) {

                        name_str1 = fname1;
                        name_str2 = lname1;
                        mobile_str = ph1;
                        Intent intet = new Intent(Choose_Contacts_for_task.this, Task_class.class);
                        intet.putExtra("Activity_fragm", Activity_fragm);
                        intet.putExtra("name_str1", name_str1);
                        intet.putExtra("name_str2", name_str2);
                        startActivity(intet);
                    }else if(task_activity.equals("Deal_page")){
                        name_str1 = fname1;
                        name_str2 = lname1;
                        mobile_str = ph1;
                        Intent intet = new Intent(Choose_Contacts_for_task.this, Deals_page.class);
                        intet.putExtra("Activity_fragm", Activity_fragm);
                        intet.putExtra("name_str1", name_str1);
                        intet.putExtra("name_str2", name_str2);
                        intet.putExtra("name_str2", mobile_str);
                        startActivity(intet);
                    }
                }
            });


            return child;
        }

    }








}

