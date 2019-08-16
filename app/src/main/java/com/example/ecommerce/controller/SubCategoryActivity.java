package com.example.ecommerce.controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.ecommerce.R;
import com.example.ecommerce.model.Connect;
import com.example.ecommerce.model.Subcategory;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SubCategoryActivity extends AppCompatActivity {

    int subcat_id;
    int cat_id;
    //Button home,gotocart,category;
    ListView lstSubCat;
    ProgressDialog pDialog;
    //SmartImageView subcat_image;
    // private Adapter_Sub_Category subcat_adapter_custom;
    //TextView subcat_name,subcat_desc;
    private ArrayList<Subcategory> ArrayLstSubCat;
    AdaptorSubCategory adapter_subcat;

    private String URL_SubCategory = Connect.Path+"subcategory_get.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);
        lstSubCat = (ListView)findViewById(R.id.lstSubCat);
        ArrayLstSubCat = new ArrayList<Subcategory>();
        Intent i = getIntent();
        cat_id = i.getIntExtra("cat_id",0);
        new GetSubCategory().execute(cat_id);


    }
    private void populateList() {
       /* List<String> sub_cat_list = new ArrayList<String>();

        for (int i = 0; i < ArrayLstSubCat.size(); i++) {
            sub_cat_list.add(ArrayLstSubCat.get(i).getSubcat_name());

        }

        ArrayAdapter<String> subcatadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, sub_cat_list);
        lstSubCat.setAdapter(subcatadapter);*/
        adapter_subcat = new AdaptorSubCategory(ArrayLstSubCat,getLayoutInflater());
        lstSubCat.setAdapter(adapter_subcat);


        lstSubCat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int pos = position;
                subcat_id = ArrayLstSubCat.get(position).getSubcat_id();
                Log.d("subcat_id",Integer.toString(subcat_id));
                Intent i = new Intent(getApplicationContext(),ProductActivity.class);
                i.putExtra("subcat_id",subcat_id);
                startActivity(i);

            }
        });

    }
    public class GetSubCategory extends AsyncTask<Integer, Void, Void> {

        protected void onPreExecute(){
            super.onPreExecute();
            pDialog = new ProgressDialog(SubCategoryActivity.this);
            pDialog.setMessage("fetching Sub Category Data");
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
        protected Void doInBackground(Integer... arg) {
            String cat_id = arg[0].toString();


            // Preparing get params
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("cat_id", cat_id));//get the url parameter name

            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_SubCategory, ServiceHandler.GET,params);

            Log.d("Response: Sub Category", "> " + json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray Sub_Category_json = jsonObj
                                .getJSONArray("Subcategory");

                        for (int i = 0; i < Sub_Category_json.length(); i++) {
                            JSONObject Sub_Category_json_obj = (JSONObject) Sub_Category_json.get(i);

                            Subcategory subcat = new Subcategory(Sub_Category_json_obj.getInt("subcat_id"),Sub_Category_json_obj.getInt("cat_id"),Sub_Category_json_obj.getString("subcat_name"),Sub_Category_json_obj.getString("subcat_img"));
                            ArrayLstSubCat.add(subcat);
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
