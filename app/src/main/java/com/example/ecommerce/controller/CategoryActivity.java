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
import com.example.ecommerce.model.Category;
import com.example.ecommerce.model.Connect;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {

    ListView lstCat;
    ProgressDialog pDialog;
    ArrayList<Category> cat_array_list;
    int cat_id;
    AdaptorCategory AdaptorCat;

    private String URL_Category = Connect.Path + "category_get.php";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        lstCat = (ListView) findViewById(R.id.lstCat);
        cat_array_list = new ArrayList<Category>();
        new GetCategory().execute();



    }

    private void populateList() {
        AdaptorCat = new AdaptorCategory(cat_array_list, getLayoutInflater());
        lstCat.setAdapter(AdaptorCat);
        lstCat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int pos;
                pos = position;
                cat_id = cat_array_list.get(pos).getCat_id();
                Log.d("cat_id =", Integer.toString(cat_id));

                Intent i = new Intent(getApplicationContext(), SubCategoryActivity.class);
                i.putExtra("cat_id", cat_id);
                startActivity(i);

            }
        });
    }


    public class GetCategory extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(CategoryActivity.this);
            pDialog.setMessage("Fetching Category Data");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... voids) {

            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_Category, ServiceHandler.GET);

            Log.d("Response", json);


            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray Category_json = jsonObj.getJSONArray("Category");

                        for (int i = 0; i < Category_json.length(); i++) {
                            JSONObject Category_json_obj = (JSONObject) Category_json.get(i);
                            Category cat = new Category(Category_json_obj.getInt("cat_id"), Category_json_obj.getString("cat_name"), Category_json_obj.getString("cat_img"));
                            cat_array_list.add(cat);
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
            populateList();
        }
    }
}