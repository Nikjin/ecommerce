package com.example.ecommerce.controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class LoginActivity extends AppCompatActivity {

    EditText email,password;
    TextView txtregister;
    int final_total;
    Button login;
    int uid;
    private static List<User> userlist;
    String uemail,uname,uaddr,upwd,ucity,ucontact,status,from;
    private String URL_User= Connect.Path+"get_login.php";
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email=(EditText)findViewById(R.id.edtu_email);
        password=(EditText)findViewById(R.id.edtu_pwd);
        txtregister=(TextView)findViewById(R.id.txtregister);
        login=(Button)findViewById(R.id.btnlogin);

        Intent i=getIntent();
        from=i.getStringExtra("from");
        final_total=i.getIntExtra("final_total",0);
        userlist=LoginActivity.getUser();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uemail=email.getText().toString();
                upwd=password.getText().toString();

                if (uemail.equals("")||uemail.equals(null)){
                    Toast.makeText(getBaseContext(),"Please enter your email",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(upwd.equals("")||upwd.equals(null))
                {
                    Toast.makeText(getBaseContext(),
                            "Please Enter Your User Password",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                new Loginuser().execute(uemail,upwd);


        }
        });

        txtregister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent next = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(next);
            }
        });
    }

    private class Loginuser extends AsyncTask<String, Void, Void> {

        protected void onPreExecute(){
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Verify Your Data");
            pDialog.setCancelable(false);
            pDialog.show();
        }


        @Override
        protected Void doInBackground(String... arg) {
            uemail=arg[0];
            upwd=arg[1];

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("email", uemail));
            params.add(new BasicNameValuePair("pwd", upwd));

            ServiceHandler servicehandler = new ServiceHandler();
            String json = servicehandler.makeServiceCall(URL_User, ServiceHandler.GET, params);

            Log.d("Create Response: ", "> " + json);

            if(json!=null)
            {
                try{
                JSONObject user_json_Obj = new JSONObject(json);

                    if (user_json_Obj != null) {


                        JSONArray user_json_array = user_json_Obj
                                .getJSONArray("login");

                        for (int i = 0; i < user_json_array.length(); i++) {
                            JSONObject user_obj_json = (JSONObject) user_json_array.get(i);
                            status= user_obj_json.getString("status");
                            //Log.d("u_id", u_id);
                            if(status.equals("success"))
                            {
                                Intent get =getIntent();
                                String userlogin = get.getStringExtra("userlogin");

                                uid = user_obj_json.getInt("uid");
                                uname = user_obj_json.getString("uname");
                                uaddr = user_obj_json.getString("uaddr");
                                ucity = user_obj_json.getString("ucity");
                                ucontact = user_obj_json.getString("ucontact");
                                uemail = user_obj_json.getString("uemail");
                                Log.d("uid", Integer.toString(uid));
                                Log.d("uname", "uname");
                                uid= Integer.valueOf(uid);
                                User cu = new User(uid, uname, uaddr, ucity, ucontact, uemail);
                                userlist.add(cu);

                                if(from.equals("Cart"))
                                {

                                    Intent payoption = new Intent(getApplicationContext(),PayOptionActivity.class);
                                    payoption.putExtra("final_total", final_total);
                                    startActivity(payoption);
                                }
                              /*  else if(from.equals("Main"))
                                {

                                    String u_id=Integer.toString(uid);
                                    Intent Orderdetail = new Intent(getApplicationContext(), ShowOptionActivity.class);
                                    Orderdetail.putExtra("uid", u_id);
                                    startActivity(Orderdetail);
                                }*/

                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
            }




            return null;
        }
        protected void onPostExecute(Void result){
            super.onPostExecute(result);
            if(pDialog.isShowing())
                pDialog.dismiss();

        }
    }
    public static List<User> getUser()
    {
        if(userlist == null)
        {
            userlist = new ArrayList<User>();
        }
        return userlist;
    }

    }
