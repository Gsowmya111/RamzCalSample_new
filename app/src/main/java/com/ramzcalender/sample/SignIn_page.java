package com.ramzcalender.sample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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



public class SignIn_page extends AppCompatActivity {
    EditText et_uname,et_pass;
    TextView logout,signup;
    Button signin;
    private String responseBody,finalResponseBody;
    private String et_mob,et_pas;
    String mob_jsn,levl_jsn,nam_jsn;
    Globals globalVariable;
    private JSONArray Roomes;
    public static String level_number,login_numbr,owner_name,contacts_str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_page);
        signin = (Button) findViewById(R.id.btn_signin);
        et_uname = (EditText) findViewById(R.id.et_username);
        et_pass = (EditText) findViewById(R.id.et_password);
        logout= (TextView) findViewById(R.id.tv_logout);
        signup= (TextView) findViewById(R.id.tv_signup_link);

        globalVariable = (Globals) getApplicationContext();
        globalVariable.setClass_names("Sign_in");
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  contacts_str="Sigup_page";
                Intent i = new Intent(SignIn_page.this,Contacts_page.class);
                startActivity(i);*/
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contacts_str="Signin_page";
              et_mob=  et_uname.getText().toString();
                et_pas = et_pass.getText().toString();
                login_numbr=et_mob;
                globalVariable.setName(et_mob);
                globalVariable.setLogin_mob(et_mob);

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
        HttpPost httpPost = new HttpPost("http://edisonbro.in/crm/login.php");

        // Building post parameters
        // key and value pair
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
        nameValuePair.add(new BasicNameValuePair("phone", et_mob));
        nameValuePair.add(new BasicNameValuePair("password", et_pas));

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
                Roomes = jsonObj.getJSONArray("login");

                for (int i = 0; i < Roomes.length(); i++) {
                    JSONObject jsonobject = Roomes.getJSONObject(i);

                    mob_jsn = jsonobject.getString("phone");
                    levl_jsn = jsonobject.getString("level");
                    nam_jsn = jsonobject.getString("name");

                }

            }
            Log.d("TAG", responseBody);

            final String finalResponseBody = responseBody;

           runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    level_number=levl_jsn;
                    owner_name=mob_jsn;

                    if(  level_number.equals("level 2")||level_number.equals("level 3")||level_number.equals("level 4")||level_number.equals("level 5")){
                        startService(new Intent(SignIn_page.this, BackgroundService.class));
                    }

                  if(mob_jsn.equals(et_mob)){

                      Intent inte= new Intent(SignIn_page.this,MainActivity.class);
                      startActivity(inte);
                      startService(new Intent(SignIn_page.this, BackgroundService.class));
                  }else{
                      Toast.makeText(SignIn_page.this, "Error in Login ..Please check credentials ", Toast.LENGTH_LONG).show();
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
