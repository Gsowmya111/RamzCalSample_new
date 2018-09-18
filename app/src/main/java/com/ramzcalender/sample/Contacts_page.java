package com.ramzcalender.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
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
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import static com.ramzcalender.sample.SignIn_page.contacts_str;
import static com.ramzcalender.sample.SignIn_page.level_number;
import static com.ramzcalender.sample.SignIn_page.owner_name;
import static com.ramzcalender.sample.SingleContact_MainPage.ades;
import static com.ramzcalender.sample.SingleContact_MainPage.emid;
import static com.ramzcalender.sample.SingleContact_MainPage.levle;
import static com.ramzcalender.sample.SingleContact_MainPage.lnamee;
import static com.ramzcalender.sample.SingleContact_MainPage.nam;
import static com.ramzcalender.sample.SingleContact_MainPage.phhno;
import static com.ramzcalender.sample.SingleContact_MainPage.sta;

/**
 * Created by edison office on 7/17/2018.
 */

public class Contacts_page extends Activity {
    Button save,cancel;
    EditText fname,lname,comapany_name,title,mobile_no1,mobile_no2,email,other_cont,address1,address2,descriptn,ownr_nam;
    TextView ownername;
    Spinner hierachy_level,cust_status;
    static String responseBody;
    static String finalResponseBody;



    String[] hierarchy_levels_array = new String[] {
            "level 1", "level 2", "level 3", "level 4", "level 5"
    };
    String[] customer_status_array = new String[] {
            "Current Customer","Past customer","Non Customer"
    };
    private String data_push;
    ArrayList a = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_creation);

      a.clear();
        a.add("Current Customer");
        a.add("Past customer");
        a.add("Non Customer");

    //    contacts_str="Contacts";

        save = (Button) findViewById(R.id.save_btn_cont);
        cancel = (Button) findViewById(R.id.cancel_btn_contact);
        fname = (EditText) findViewById(R.id.first_name_cont);
        lname = (EditText) findViewById(R.id.last_name_cont);
        comapany_name = (EditText) findViewById(R.id.company_name_cont);
        title = (EditText) findViewById(R.id.title_cont);
        mobile_no1 = (EditText) findViewById(R.id.mobile1_num_cont);
        mobile_no2 = (EditText) findViewById(R.id.mobile2_num_cont);
        email = (EditText) findViewById(R.id.email_cont);
        address1 = (EditText) findViewById(R.id.address1_cont);
        address2 = (EditText) findViewById(R.id.address2_cont);
        descriptn = (EditText) findViewById(R.id.description_cont);
        hierachy_level= (Spinner) findViewById(R.id.hierachy_cont);
        cust_status= (Spinner) findViewById(R.id.customerstatus_cont);
        ownr_nam= (EditText) findViewById(R.id.owner_cont);

        ownr_nam.setText(owner_name);

       // String[] countries=getResources().getStringArray(R.array.levels_name);
        String[] countries1=getResources().getStringArray(R.array.levels_name1);
        String[] countries2=getResources().getStringArray(R.array.levels_name2);
        String[] countrie3=getResources().getStringArray(R.array.levels_name3);
        String[] countries4=getResources().getStringArray(R.array.levels_name4);

        if(level_number.equals("level 1")){
            ArrayAdapter adapter = ArrayAdapter.createFromResource(
                    this, R.array.levels_name, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            hierachy_level.setAdapter(adapter);
        }
      else  if(level_number.equals("level 2")){
            ArrayAdapter adapter = ArrayAdapter.createFromResource(
                    this, R.array.levels_name1, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            hierachy_level.setAdapter(adapter);
        }
         else  if(level_number.equals("level 3")){
            ArrayAdapter adapter = ArrayAdapter.createFromResource(
                    this, R.array.levels_name2, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            hierachy_level.setAdapter(adapter);
        }
        else  if(level_number.equals("level 4")){
            ArrayAdapter adapter = ArrayAdapter.createFromResource(
                    this, R.array.levels_name3, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            hierachy_level.setAdapter(adapter);
        }else  if(level_number.equals("level 5")){
            ArrayAdapter adapter = ArrayAdapter.createFromResource(
                    this, R.array.levels_name4, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            hierachy_level.setAdapter(adapter);
        }

       /* ArrayAdapter<String> adp1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, hierarchy_levels_array);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hierachy_level.setAdapter(adp1);*/



        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, a);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cust_status.setAdapter(adapter);

        // contacts_str="MainPage";

            /*Bundle bundle = getIntent().getExtras();
            String fnamm1 = bundle.getString("fname");
            String lnamm1 = bundle.getString("lnam");
            String mob1 = bundle.getString("phhe");
            String id1 = bundle.getString("maie");
            String adess1 = bundle.getString("adde");
            String levell1 = bundle.getString("leve");
            String stats = bundle.getString("state");

            int retval = a.indexOf(levell1);*/

        String levv= levle;
        String stat= sta;
        int retval = a.indexOf(levv);
      if(contacts_str.equals("SinglePage")){

            fname.setText(nam);
            lname.setText(lnamee);
            mobile_no1.setText(phhno);
            email.setText(emid);
            address1.setText(ades);
           cust_status.setSelection(retval);

          if(levle.equals("level 1")){
              ArrayAdapter adapter1 = ArrayAdapter.createFromResource(
                      this, R.array.levels_name, android.R.layout.simple_spinner_item);
              adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
              hierachy_level.setAdapter(adapter1);
          }
          else  if(levle.equals("level 2")){
              ArrayAdapter adapter1 = ArrayAdapter.createFromResource(
                      this, R.array.levels_name1, android.R.layout.simple_spinner_item);
              adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
              hierachy_level.setAdapter(adapter1);
          }
          else  if(levle.equals("level 3")){
              ArrayAdapter adapter1 = ArrayAdapter.createFromResource(
                      this, R.array.levels_name2, android.R.layout.simple_spinner_item);
              adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
              hierachy_level.setAdapter(adapter1);
          }
          else  if(levle.equals("level 4")){
              ArrayAdapter adapter1 = ArrayAdapter.createFromResource(
                      this, R.array.levels_name3, android.R.layout.simple_spinner_item);
              adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
              hierachy_level.setAdapter(adapter1);
          }else  if(levle.equals("level 5")){
              ArrayAdapter adapter1 = ArrayAdapter.createFromResource(
                      this, R.array.levels_name4, android.R.layout.simple_spinner_item);
              adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
              hierachy_level.setAdapter(adapter1);
          }





        }

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Contacts_page.this,MainActivity.class);
                startActivity(in);
                finish();
            }
        });

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
                String ownr=ownr_nam.getText().toString();

                String hierarcy= hierachy_level.getSelectedItem().toString();
                String cus_stats= cust_status.getSelectedItem().toString();

                if(contacts_str.equals("SinglePage")) {
                    data_push="#"+phhno+"*"+firstame+"*"+lastame+"*"+comapname+"*"+titlee+"*"+mob1+"*"+mob2+"*"+eid+"*"+addr1+"*"+addr2+"*"+hierarcy+"*"+cus_stats+"*"+desc+"#";

                }else{
                    data_push="#"+firstame+"*"+lastame+"*"+comapname+"*"+titlee+"*"+mob1+"*"+mob2+"*"+eid+"*"+addr1+"*"+addr2+"*"+hierarcy+"*"+cus_stats+"*"+desc+"*"+ownr+"#";

                }

                if((firstame.equals(""))||(lastame.equals(""))||(mob1.equals(""))||(addr1.equals(""))&&(eid.equals(""))){
                    Toast.makeText(Contacts_page.this, "Please fill credentials", Toast.LENGTH_LONG).show();
                }else {
                    Thread t = new Thread() {

                        public void run() {
                            post();
                        }
                    };
                    t.start();

                }

                }
        });
    }



    private void post() {

        // Creating HTTP client
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost=null;
        List<NameValuePair> nameValuePair = new ArrayList<>();
        // Creating HTTP Post
            httpPost = new HttpPost(
                    "http://edisonbro.in/crm/insert_contacts.php");

            String trdata = data_push;
              nameValuePair = new ArrayList<>();
        if(contacts_str.equals("SinglePage")) {

            nameValuePair.add(new BasicNameValuePair("edit", trdata));
        }else{
            nameValuePair.add(new BasicNameValuePair("contacts", trdata));
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
                        if(finalResponseBody.equals("success")) {
                            Toast.makeText(Contacts_page.this, "Contacts saved successfully", Toast.LENGTH_LONG).show();
                            if(contacts_str.equals("Signin_page")){
                                Intent inte = new Intent(Contacts_page.this,SignIn_page.class);
                                startActivity(inte);
                            }


                        }else if(finalResponseBody.equals("update successfully")) {
                            Toast.makeText(Contacts_page.this, "Contacts edited successfully  ", Toast.LENGTH_LONG).show();
                        }else  {
                            Toast.makeText(Contacts_page.this, "Error ", Toast.LENGTH_LONG).show();
                        }
                    // tvres.setText(finalResponseBody);
                 //   resp=finalResponseBody;


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