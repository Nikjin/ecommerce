package com.example.ecommerce.controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ecommerce.R;
import com.example.ecommerce.model.Connect;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.User;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShippingActivity extends AppCompatActivity {

    String selectoption;
    String uname, uaddress, ucity, ucontact, uemail, upass, uconfpass, crdno, crdnm, pay_id;
    String rec_name, rec_address, rec_city, rec_contact, rec_email;
    String itemname, itemid, price, quantity;
    int finaltotal, mid;
    String uid, ordnewid;
    String final_total, payment, status, error, qty, ordid, total;
    EditText editname, editaddress, editcity, editcontact, editemail;
    Button btnclear, btnShipping;

    private String URL_OrderMaster = Connect.Path + "insertorder.php";
    private String URL_OrderDetail = Connect.Path + "insertorderdetail.php";
    private String URL_SHIPPING = Connect.Path + "insertshipping.php";
    private String URL_PAYMENT = Connect.Path + "insertpayment.php";

    private List<User> listuser;
    private List<Order> listcart;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping);

        editname = (EditText) findViewById(R.id.editText_UserName);
        editaddress = (EditText) findViewById(R.id.editText_address);
        editcity = (EditText) findViewById(R.id.editText_city);
        editcontact = (EditText) findViewById(R.id.edittextcontact);
        editemail = (EditText) findViewById(R.id.editText_Email);
        btnclear = (Button) findViewById(R.id.btn_clickhere);
        btnShipping = (Button) findViewById(R.id.Btnshipping);
        listuser = LoginActivity.getUser();
        listcart = ProductDetailActivity.getCart();

        Intent i = getIntent();
        finaltotal = i.getIntExtra("final_total", 0);
        final_total = Integer.toString(finaltotal);
        selectoption = i.getStringExtra("selectoption");

        if (selectoption.equals("Card")) {
            crdno = i.getStringExtra("cardno");

            crdnm = i.getStringExtra("cardnm");
            Log.d("in condition", crdnm + " " + crdno);

        }

        payment = selectoption;

        for (int j = 0; j < listuser.size(); j++) {
            uid = Integer.toString(listuser.get(j).getUid());
            uname = listuser.get(j).getUname();
            uaddress = listuser.get(j).getUaddr();
            ucity = listuser.get(j).getUcity();
            ucontact = listuser.get(j).getUcontact();
            uemail = listuser.get(j).getUemail();
        }

        editname.setText(uname);
        editaddress.setText(uaddress);
        editcity.setText(ucity);
        editcontact.setText(ucontact);
        editemail.setText(uemail);


        btnclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if  ((editname.getText().toString().trim().length() > 0) && (editaddress.getText().toString().trim().length() > 0) &&
                        (editcity.getText().toString().trim().length() > 0) && (editcontact.getText().toString().trim().length() > 0) &&
                        editemail.getText().toString().trim().length() > 0){

                    editname.setText("");
                    editaddress.setText("");
                    editcity.setText("");
                    editcontact.setText("");
                    editemail.setText("");

                }
            }
        });

        btnShipping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((editname.getText().toString().trim().length() > 0) && (editaddress.getText().toString().trim().length() > 0) &&
                        (editcity.getText().toString().trim().length() > 0) && (editcontact.getText().toString().trim().length() > 0) &&
                        editemail.getText().toString().trim().length() > 0) {

                    rec_name = editname.getText().toString();
                    rec_address = editaddress.getText().toString();
                    rec_city = editcity.getText().toString();
                    rec_contact = editcontact.getText().toString();
                    rec_email = editemail.getText().toString();
                    Log.d("Receiver Name", rec_name);

                    Log.d("before add new order", final_total);
                    //Steps to insert Data
                    // 1. insert data into OrderMaster AddnewOrder & get MaseterOrderId mid
                    // 2. insert data into Payment if payment done by CARD
                    // 3. insert data into Shipping
                    // 4. Finally insert data into OrderDetail itemwise

                    new AddnewOrder().execute(uid, final_total, payment);


                }
            }
        });

    }

    private class AddnewOrder extends AsyncTask<String, Void, Void> {

        boolean isNewOrderCreated = false;

        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ShippingActivity.this);
            pDialog.setMessage("Order will be Created...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(String... arg) {

            uid = arg[0];
            final_total = arg[1];
            payment = arg[2];

            Log.d("uid", uid + " " + final_total + " " + payment);

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("uid", uid));
            params.add(new BasicNameValuePair("amount", final_total));
            params.add(new BasicNameValuePair("paymode", payment));

            ServiceHandler sh = new ServiceHandler();
            String json = sh.makeServiceCall(URL_OrderMaster, ServiceHandler.GET, params);

            Log.d("Create Response: ", "> " + json);

            if (json != null) {
                try {
                    JSONObject user_json_Obj = new JSONObject(json);
                    if (user_json_Obj != null) {

                        JSONArray user_json_array = user_json_Obj
                                .getJSONArray("order");

                        for (int i = 0; i < user_json_array.length(); i++) {
                            JSONObject order_obj_json = (JSONObject) user_json_array.get(i);
                            error = order_obj_json.getString("error");
                            //Log.d("u_id", u_id);
                            if (error.equals("false")) {

                                mid = order_obj_json.getInt("OrderId");
                                Log.d("OrderId", Integer.toString(mid));


                            }

                            else {
                                Log.d("error","EEERRRRROOOORRRR");
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

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
            Log.d("In  AddNewOrder", "AddNewOrdDet");
            AddNewOrderDetailFunc();

        }
    }

    public void AddNewOrderDetailFunc() {
        for (int j = 0; j < listcart.size(); j++) {
            itemid = Integer.toString(listcart.get(j).getPro_id());
            itemname = listcart.get(j).getPro_name();
            price = Integer.toString(listcart.get(j).getPro_price());
            quantity = Integer.toString(listcart.get(j).getQty());
            total = Integer.toString(listcart.get(j).getTot());

            Log.d("itemid in Funct ", itemid);
            ordid = Integer.toString(mid);
            new AddnewOrderDetail().execute(ordid, itemid, itemname, price, quantity, total);

        }

        new AddShippinng().execute(uid,ordid,rec_name, rec_address, rec_city, rec_contact, rec_email);


    }

    private class AddnewOrderDetail extends AsyncTask<String, Void, Void> {

        boolean isNewOrderCreated = false;

        @Override

        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ShippingActivity.this);
            pDialog.setMessage("new Order will be Created...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(String... arg) {


            ordid = arg[0];
            itemid = arg[1];
            itemname = arg[2];
            price = arg[3];
            qty = arg[4];
            total = arg[5];
            Log.d("ord_id", ordid + " " + itemid + " " + itemname + " " + price + " " + quantity + " " + total);

            Log.d("ord_id In Background", ordid);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("ordid", ordid));
            params.add(new BasicNameValuePair("itemid", itemid));
            params.add(new BasicNameValuePair("itemname", itemname));
            params.add(new BasicNameValuePair("price", price));
            params.add(new BasicNameValuePair("qty", quantity));
            params.add(new BasicNameValuePair("total", total));


            ServiceHandler sh = new ServiceHandler();
            String json = sh.makeServiceCall(URL_OrderDetail, ServiceHandler.GET, params);

            Log.d("Create Response: ", "> " + json);

            if (json != null) {
                try {
                    JSONObject order_json_obj = new JSONObject(json);
                    if (order_json_obj != null) {

                        JSONArray order_json_array = order_json_obj
                                .getJSONArray("orderdetail");

                        for (int i = 0; i < order_json_array.length(); i++) {
                            JSONObject order_obj_json = (JSONObject) order_json_array.get(i);
                            ordnewid = order_obj_json.getString("OrderDetId");

                            error = order_json_obj.getString("error");

                            if (error.equals("false")) {
                                isNewOrderCreated = true;

                            } else {
                                Log.e("Orderdetail Error:", ">" + order_json_obj.getString("message"));

                            }
                            Log.d("OrderDetId", ordnewid);

                        }
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("JSON Data", "Did'nt recive any data from server");
            }
            return null;


        }

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();

            Log.d("In  AddShipping", "AddShipping");
            // new AddShippinng().execute(u_id,rec_name,rec_address,rec_city,rec_contact,rec_email,ordid,payment);

        }
    }

    private class AddShippinng extends AsyncTask<String, Void, Void> {

        boolean isAddshipping = false;

        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ShippingActivity.this);
            pDialog.setMessage("Progress in Ordering");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(String... arg) {

            rec_name = arg[2];
            rec_address = arg[3];
            rec_city = arg[4];
            rec_contact = arg[5];
            rec_email = arg[6];
            ordid = arg[1];
            uid = arg[0];
            Log.d("u_name in Shipping", uname);
            Log.d("uid", uid);
            Log.d("ordid", ordid);

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("name", rec_name));
            params.add(new BasicNameValuePair("address", rec_address));
            params.add(new BasicNameValuePair("city", rec_city));
            params.add(new BasicNameValuePair("contact", rec_contact));
            params.add(new BasicNameValuePair("email", rec_email));
            params.add(new BasicNameValuePair("ord_id", ordid));
            params.add(new BasicNameValuePair("uid", uid));
            ServiceHandler servicehandler = new ServiceHandler();
            String json = servicehandler.makeServiceCall(URL_SHIPPING, ServiceHandler.GET, params);

            Log.d("Create Response: ", "> " + json);

            if (json != null) {
                try {
                    JSONObject user_json_Obj = new JSONObject(json);
                    if (user_json_Obj != null) {

                        JSONArray user_json_array = user_json_Obj
                                .getJSONArray("Shipping");

                        for (int i = 0; i < user_json_array.length(); i++) {
                            JSONObject user_obj_json = (JSONObject) user_json_array.get(i);
                            String ship_id = user_obj_json.getString("Shipping ID");
                            error = user_obj_json.getString("error");
                            Log.d("Shipping id_id", ship_id);
                            if (error.equals("false")) {
                                isAddshipping = true;
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

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();

            Intent i = new Intent(getApplicationContext(), ThankYouActivity.class);
            startActivity(i);
        }
    }

}


