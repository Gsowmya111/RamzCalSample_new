package com.ramzcalender.sample;

/**
 * Created by edison office on 7/17/2018.
 */

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
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

import static com.ramzcalender.sample.SignIn_page.contacts_str;


/* Fragment used as page 1 */
public class Activity_Fragment extends Fragment {
    ArrayList aa, aa1, aa2, aa3, aa4, aa5, aa6;
    String name, name1, name2, name3, name4, name5, name6;
    ListView listview;
    private JSONArray Roomes;
    Customadapter disadpt;
    public static String mobile_str, class_name;
    Globals globalVariable;
    String fname, lname, emaill_id, ph, customerstatus, ades, levl;
    FloatingActionButton fb_cont;
    static String task_activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_activity, container, false);
        listview = (ListView) rootView.findViewById(R.id.listview);
        fb_cont = (FloatingActionButton) rootView.findViewById(R.id.floating_contacts);

        globalVariable = (Globals) getActivity().getApplicationContext();
        //  googleMap = ((MapView) rootView.findViewById(R.id.YOURMAPID)).getMap();
        class_name = "Contacts";

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

        fb_cont.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                contacts_str = "new_cont";
                Intent ii = new Intent(getActivity().getApplicationContext(), Contacts_page.class);
                getActivity().startActivity(ii);
            }
        });


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String c = globalVariable.getClass_names();
                fname = String.valueOf(aa.get(position));
                lname = String.valueOf(aa1.get(position));
                ph = String.valueOf(aa2.get(position));
                emaill_id = String.valueOf(aa3.get(position));
                ades = String.valueOf(aa4.get(position));
                levl = String.valueOf(aa5.get(position));
                customerstatus = String.valueOf(aa6.get(position));

                Intent intent = new Intent(getActivity(), SingleContact_MainPage.class);
                intent.putExtra("fnam", fname);
                intent.putExtra("lnam", lname);
                intent.putExtra("phh", ph);
                intent.putExtra("mai", emaill_id);
                intent.putExtra("add", ades);
                intent.putExtra("lev", levl);
                intent.putExtra("stat", customerstatus);
                getActivity().startActivity(intent);


                Toast.makeText(getActivity().getApplicationContext(), "you clicked" + fname, Toast.LENGTH_LONG).show();

            }
        });

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                return false;
            }
        });


        return rootView;
    }

    //for HTTP POST
    void postt() throws JSONException {
        // Creating HTTP client
        HttpClient httpClient = new DefaultHttpClient();
        // Creating HTTP Post
        HttpPost httpPost = new HttpPost("http://edisonbro.in/crm/insert_contacts.php");
        // Building post parameters
        // key and value pair
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
        nameValuePair.add(new BasicNameValuePair("cont", "all_contacts"));
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

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    //   tv.setText(finalResponseBody);
                    disadpt = new Customadapter(getActivity().getApplicationContext(), aa, aa1, aa2, aa3, aa4, aa5, aa6);
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

    class Customadapter extends BaseAdapter {
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

        public Customadapter(Context c, ArrayList<String> fnam, ArrayList<String> lname, ArrayList phno, ArrayList mail, ArrayList address, ArrayList level, ArrayList cust_status) {
            // TODO Auto-generated constructor stub
            this.mContext = c;
            this.fname = fnam;
            this.phno = phno;
            this.lname = lname;
            this.email = mail;
            this.address = address;
            this.hir_lev = level;
            this.cus_sta = cust_status;
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

            TextView fname, phnumber, maill, lname, adr, levl, status;
            Button btnaddr;
        }

        public View getView(int pos, View child, ViewGroup parent) {
            Holder mHolder;

            if (child == null) {
                layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                child = layoutInflater.inflate(R.layout.single_item_saleman, null);
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
            mHolder.status.setText(cus_sta.get(pos));
            mHolder.adr.setText(address.get(pos));


            return child;
        }

    }


}
