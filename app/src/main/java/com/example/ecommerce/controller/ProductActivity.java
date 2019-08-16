package com.example.ecommerce.controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.example.ecommerce.R;
import com.example.ecommerce.model.Connect;
import com.example.ecommerce.model.Product;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity {

    int subcat_id;
    int pro_id;
    String pro_name;
    String pro_img;
    int pro_price;
    String pro_desc;
    String pro_comp;
    Button but_View_Cart;

    GridView gv_product;
    ProgressDialog pDialog;

    private ArrayList<Product> ArrayLstProduct;
    AdaptorProduct adapter_product;

    private String URL_Product= Connect.Path+"product_get.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        gv_product = (GridView) findViewById(R.id.gv_product);
        but_View_Cart=(Button) findViewById(R.id.but_View_Cart);
        ArrayLstProduct = new ArrayList<Product>();
        Intent i = getIntent();
        subcat_id = i.getIntExtra("subcat_id",0);
        new GetProduct().execute(subcat_id);
        but_View_Cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),CartActivity.class);
                startActivity(i);
            }
        });

    }

    private void populateList()
    {
        adapter_product=new AdaptorProduct(ArrayLstProduct,getLayoutInflater());
        gv_product.setAdapter(adapter_product);

        gv_product.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int pos=position;
                pro_id=ArrayLstProduct.get(position).getPro_id();
                Log.d("product_id",Integer.toString(pro_id));
                pro_id = ArrayLstProduct.get(position).getPro_id();
                pro_name = ArrayLstProduct.get(position).getPro_name();
                pro_desc = ArrayLstProduct.get(position).getPro_desc();
                pro_comp = ArrayLstProduct.get(position).getPro_comp();
                pro_img = ArrayLstProduct.get(position).getPro_img();
                pro_price = ArrayLstProduct.get(position).getPro_price();


                Intent i = new Intent(getApplicationContext(),ProductDetailActivity.class);
                i.putExtra("pro_id",pro_id);
                i.putExtra("pro_name",pro_name);
                i.putExtra("pro_desc",pro_desc);
                i.putExtra("pro_comp",pro_comp);
                i.putExtra("pro_price",pro_price);
                i.putExtra("pro_img",pro_img);

                startActivity(i);
            }
        });
    }


    public class GetProduct extends AsyncTask<Integer, Void, Void>  {



        protected void onPreExecute(){
            super.onPreExecute();
            pDialog = new ProgressDialog(ProductActivity.this);
            pDialog.setMessage("fetching Product Data");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected void onPostExecute(Void result){
            super.onPostExecute(result);
            if(pDialog.isShowing())
                pDialog.dismiss();
            populateList();
        }

        @Override
        protected Void doInBackground(Integer...arg) {
            String subcat_id=arg[0].toString();

            List<NameValuePair>params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("subcat_id",subcat_id));

            ServiceHandler jsonParser= new ServiceHandler();
            String json= jsonParser.makeServiceCall(URL_Product,ServiceHandler.GET,params);

            Log.d("Response: Product",">"+json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray Product_json = jsonObj
                                .getJSONArray("Product");

                        for (int i = 0; i < Product_json.length(); i++) {
                            JSONObject Product_json_obj = (JSONObject) Product_json.get(i);

                            Product product = new Product(Product_json_obj.getInt("subcat_id"),Product_json_obj.getInt("pro_id"),Product_json_obj.getString("pro_name"),Product_json_obj.getString("pro_img"),Product_json_obj.getInt("pro_price"),Product_json_obj.getString("pro_desc"),Product_json_obj.getString("pro_comp"));
                            ArrayLstProduct.add(product);
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
    }
}
