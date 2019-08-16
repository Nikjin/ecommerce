package com.example.ecommerce.controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerce.R;
import com.example.ecommerce.model.Connect;
import com.example.ecommerce.model.User;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RegistrationActivity extends AppCompatActivity {

    TextView name,address,city,contact,email,password,confirmpwd;
    String uname,uaddr,ucity,ucontact,uemail,uconfpass;
    String upass;
    Button btnregister;

    private List<User> userlist;
    ProgressDialog pDialog;
    String status;

    private String URL_User = Connect.Path + "insertuser.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        name=(TextView)findViewById(R.id.edtu_name);
        address = (TextView) findViewById(R.id.edtu_address);
        city = (TextView) findViewById(R.id.edtu_city);
        contact = (TextView) findViewById(R.id.edtu_contact);
        email = (TextView) findViewById(R.id.edtu_email);
        password = (TextView) findViewById(R.id.edtu_pwd);
        confirmpwd = (TextView) findViewById(R.id.edtu_confirmpwd);
        btnregister = (Button) findViewById(R.id.btnregister);
        userlist = new ArrayList<User>();

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uname = name.getText().toString();
                uaddr = address.getText().toString();
                ucity = city.getText().toString();
                ucontact = contact.getText().toString();
                uemail = email.getText().toString();
                upass = password.getText().toString();
                uconfpass = confirmpwd.getText().toString();
                Log.d("address", uaddr);
                Log.d("Password :", upass);
                Log.d("Confirm Password :", uconfpass);


                if (uname.equals(null) || uname.equals("")) {
                    Toast.makeText(getBaseContext(), "Please Enter User Name",
                            Toast.LENGTH_SHORT).show();
                    return;
                } else if (uaddr.equals("") || uaddr.equals(null)) {
                    Toast.makeText(getBaseContext(),
                            "Please Enter User Address", Toast.LENGTH_SHORT)
                            .show();
                    return;
                } else if (ucity.equals("") || ucity.equals(null)) {
                    Toast.makeText(getBaseContext(), "Please Enter User City",
                            Toast.LENGTH_SHORT).show();
                    return;
                } else if (ucontact.equals("") || ucontact.equals(null)) {
                    Toast.makeText(getBaseContext(),
                            "Please Enter User Mobile Number",
                            Toast.LENGTH_SHORT).show();
                    return;
                } else if (uemail.equals("") || uemail.equals(null)) {
                    Toast.makeText(getBaseContext(), "Please Enter User Email",
                            Toast.LENGTH_SHORT).show();
                    return;
                } else if (upass.equals("") || upass.equals(null)) {
                    Toast.makeText(getBaseContext(),
                            "Please Enter User Password", Toast.LENGTH_SHORT)
                            .show();
                    return;
                } else if (uconfpass.equals("") || uconfpass.equals(null)) {
                    Toast.makeText(getBaseContext(),
                            "Please Enter User Confirmation Password",
                            Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if (uconfpass.equals(upass)) {
                        /*
                         * Toast.makeText(getBaseContext(),
                         * "Registration Successfull",
                         * Toast.LENGTH_SHORT).show(); return; Intent
                         * intent_user = new Intent(getApplicationContext(),
                         * LoginActivity.class); User cu = new User(uname,
                         * uaddr, ucity, ucontact, uemail, upass);
                         * userlist.add(cu); startActivity(intent_user);
                         */
                        new Reguser().execute(uname, uaddr, ucity, ucontact,
                                uemail, upass);

                    } else {
                        Toast.makeText(getBaseContext(),
                                "Your Password is not match",
                                Toast.LENGTH_SHORT).show();
                        return;

                    }
                }

            }
        });
    }

    public void populate(){
        Intent i=new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(i);
    }

    public class Reguser extends AsyncTask<String,Void,Void>
    {

        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(RegistrationActivity.this);
            pDialog.setMessage("Verify Your Data");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(String... arg) {

         String uname=arg[0];
            String uaddr = arg[1];
            String ucity = arg[2];
            String ucontact = arg[3];
            String uemail = arg[4];
            String upass = arg[5];
            Log.d("uemail", uemail);
            Log.d("upwd", upass);

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("uname", uname));
            params.add(new BasicNameValuePair("uaddr", uaddr));
            params.add(new BasicNameValuePair("ucity", ucity));
            params.add(new BasicNameValuePair("ucontact", ucontact));
            params.add(new BasicNameValuePair("uemail", uemail));
            params.add(new BasicNameValuePair("upwd",upass));

            ServiceHandler servicehandler=new ServiceHandler();
            String json = servicehandler.makeServiceCall(URL_User,ServiceHandler.GET,params);

            Log.d("Create Response: ", "> " + json);

            if (json!=null)
            {
                try{
                    JSONObject user_json_Obj= new JSONObject(json);

                    if (user_json_Obj!=null){

                        JSONArray user_json_array= user_json_Obj.getJSONArray("reg");

                        for (int i=0;i<user_json_array.length();i++){
                            JSONObject user_obj_json=(JSONObject) user_json_array.get(i);

                            status=user_obj_json.getString("status");

                            if (status.equals("success"))
                            {
                                finish();
                            }

                        }
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
            }
            return null;
            }




        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
            populate();

        }
    }
}
