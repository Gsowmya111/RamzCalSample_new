package com.ramzcalender.sample;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
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
import java.util.ArrayList;
import java.util.List;

public class Fragment_Unqualified extends Fragment {
    ListView listView;
    TextView tv;
    ImageView iv;
    private JSONArray Keywordd;
    ArrayList name_arr, amt_arr, hot_arr, ass_arr, sta_arr, sor_arr, date_arr,sln_arr,ph_arr;
    ArrayList name1, amt1, hot1, ass1, sta1, sor1, date1,sln1,ph1;
    String name_lv,amt_lv,hot_lv,ass_lv,sta_lv,sor_lv,date_v,slnn_lv,phn_lv;
    String name_str, amt_str, hot_str, ass_str, sta_str, sor_str,date_str,sln_str,ph_str;
    Customadapter disadpt;
    int listcount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_prospecting, container, false);
        listView = (ListView) rootView.findViewById(R.id.listview);
        tv= (TextView) rootView.findViewById(R.id.tv);
        iv= (ImageView) rootView.findViewById(R.id.iv);

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





        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                name_lv = String.valueOf(name1.get(position));
                amt_lv = String.valueOf(amt1.get(position));
                hot_lv = String.valueOf(hot1.get(position));
                ass_lv = String.valueOf(ass1.get(position));
                sta_lv = String.valueOf(sta1.get(position));
                sor_lv = String.valueOf(sor1.get(position));
                date_v = String.valueOf(date1.get(position));
                slnn_lv=String.valueOf(sln1.get(position));
                phn_lv=String.valueOf(ph1.get(position));
                Intent intent = new Intent(getActivity(), SingleDeal_MainPage.class);
                intent.putExtra("nam", name_lv);
                intent.putExtra("amt", amt_lv);
                intent.putExtra("hott", hot_lv);
                intent.putExtra("asso", ass_lv);
                intent.putExtra("stag", sta_lv);
                intent.putExtra("sur", sor_lv);
                intent.putExtra("datt", date_v);
                intent.putExtra("sln", slnn_lv);
                intent.putExtra("phn", phn_lv);
                getActivity().startActivity(intent);





            }
        });











     /*  */
        return rootView;
    }

    void postt() throws JSONException {
        // Creating HTTP client
        HttpClient httpClient = new DefaultHttpClient();
        // Creating HTTP Post
        HttpPost httpPost = new HttpPost("http://edisonbro.in/crm/deal.php");


        // Building post parameters
        // key and value pair
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
        nameValuePair.add(new BasicNameValuePair("dealslist", "alldeals"));
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
                Keywordd = jsonObj.getJSONArray("deals");
                name_arr = new ArrayList();
                amt_arr = new ArrayList();
                hot_arr = new ArrayList();
                ass_arr = new ArrayList();
                sta_arr = new ArrayList();
                sor_arr = new ArrayList();
                date_arr = new ArrayList();
                sln_arr = new ArrayList();
                ph_arr = new ArrayList();

                name1 = new ArrayList();
                amt1 = new ArrayList();
                hot1 = new ArrayList();
                ass1 = new ArrayList();
                sta1 = new ArrayList();
                sor1 = new ArrayList();
                date1 = new ArrayList();
                sln1 = new ArrayList();
                ph1 = new ArrayList();
                name1.clear();
                amt1.clear();
                hot1.clear();
                ass1.clear();
                sta1.clear();
                sor1.clear();
                sln1.clear();
                ph1.clear();
                for (int i = 0; i < Keywordd.length(); i++) {
                    JSONObject jsonobject = Keywordd.getJSONObject(i);

                    name_str = jsonobject.getString("dealname");
                    amt_str = jsonobject.getString("amount");
                    hot_str = jsonobject.getString("hot");
                    ass_str = jsonobject.getString("name");
                    ph_str=jsonobject.getString("associate");
                    sta_str = jsonobject.getString("stages");
                    sor_str = jsonobject.getString("source");
                    date_str = jsonobject.getString("date");
                    sln_str = jsonobject.getString("slno");

                    name_arr.add(name_str);
                    amt_arr.add(amt_str);
                    hot_arr.add(hot_str);
                    ass_arr.add(ass_str);
                    sta_arr.add(sta_str);
                    sor_arr.add(sor_str);
                    date_arr.add(date_str);
                    sln_arr.add(sln_str);
                    ph_arr.add(ph_str);
                    if(sta_str.equals("Unqualified")) {
                        name1.add(name_str);
                        amt1.add(amt_str);
                        hot1.add(hot_str);
                        ass1.add(ass_str);
                        sta1.add(sta_str);
                        sor1.add(sor_str);
                        date1.add(date_str);
                        sln1.add((sln_str));
                        ph1.add(ph_str);
                    }

                }

                Log.d("TAG", "Http post Response: " + responseBody);
            }
            Log.d("TAG", responseBody);

            final String finalResponseBody = responseBody;

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    //   tv.setText(finalResponseBody);
                    //  disadpt = new Customadapter(getActivity().getApplicationContext(), name1, amt1, hot1, ass1,sta1,sor1,date1);
                    disadpt = new Customadapter(getActivity().getApplicationContext(), name1, amt1, hot1, ass1,sta1,sor1,date1,sln1,ph1);
                    listView.setAdapter(disadpt);

                    if(Keywordd.length()<=0) {
                        listView.setVisibility(View.INVISIBLE);
                        iv.setVisibility(View.VISIBLE);
                        tv.setText("Looks like you dont have any deals at this stage that match your filters...!");
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

    class Customadapter extends BaseAdapter {
        ArrayList result;
        Activity_Fragment context;
        private ArrayList<String> nam;
        private ArrayList<String> hot;
        private ArrayList<String> datee;
        private ArrayList<String> ass;
        private ArrayList<String> stag;
        private ArrayList<String> amount;
        private ArrayList<String> sour;
        private ArrayList<String> slnn;
        private ArrayList<String> phh;

        private Context mContext;
        LayoutInflater layoutInflater;

        double latitute = 21.7679;
        double longitude = 78.8718;

        //  public Customadapter(Context c,  ArrayList<String> denam, ArrayList<String> hott, ArrayList dat, ArrayList ass,ArrayList stagee, ArrayList amt, ArrayList sor) {
        public Customadapter(Context c, ArrayList<String> denam, ArrayList amt, ArrayList<String> hott, ArrayList ass, ArrayList stagee, ArrayList sor, ArrayList dat, ArrayList slnn, ArrayList ph1) {

            this.mContext = c;
            this.nam = denam;
            this.hot = hott;
            this.datee = dat;
            this.ass = ass;
            this.stag=stagee;
            this.amount=amt;
            this.sour=sor;
            this.slnn=slnn;
            this.phh=ph1;


        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return nam.size();
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

            TextView delnam, delhot, dealdat, delasso,delsta,dealamt,dealsour;
            Button btnaddr;
        }

        public View getView(int pos, View child, ViewGroup parent) {
            Holder mHolder;

            if (child == null) {
                layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                child = layoutInflater.inflate(R.layout.single_item_deals, null);
                mHolder = new Holder();

                //link to TextView
                mHolder.delnam = (TextView) child.findViewById(R.id.dealname);
                // mHolder.lname = (TextView) child.findViewById(R.id.lname);
                mHolder.delhot = (TextView) child.findViewById(R.id.deal_hot);
                mHolder.dealdat = (TextView) child.findViewById(R.id.date_deal);
                mHolder.delasso = (TextView) child.findViewById(R.id.asso_deal);
                mHolder.dealamt = (TextView) child.findViewById(R.id.deal_amot);
                mHolder.delsta = (TextView) child.findViewById(R.id.adress_singe);
                //  mHolder.btnaddr = (Button) child.findViewById(R.id.button_Address);


                child.setTag(mHolder);
            } else {
                mHolder = (Holder) child.getTag();
            }


            //transfer to TextView in screen
            //  mHolder.tno.setText(anum.get(pos));
            mHolder.delnam.setText(nam.get(pos));
            mHolder.delhot.setText(hot.get(pos));
            mHolder.dealdat.setText(datee.get(pos));
            mHolder.delasso.setText(ass.get(pos));
            mHolder.dealamt.setText(amount.get(pos));
            //    mHolder.adr.setText(address.get(pos));

            /*if(listcount<=0){
                listView.setVisibility(View.INVISIBLE);
                iv.setVisibility(View.VISIBLE);
                tv.setText("Looks like you dont have any deals at this stage that match your filters...!");
            }else{
                listView.setVisibility(View.VISIBLE);
                iv.setVisibility(View.INVISIBLE);
                tv.setVisibility(View.INVISIBLE);
            }*/

            return child;
        }

    }


}

