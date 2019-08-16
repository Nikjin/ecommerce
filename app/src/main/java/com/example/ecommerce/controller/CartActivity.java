package com.example.ecommerce.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ecommerce.R;
import com.example.ecommerce.model.Order;

import java.util.List;

public class CartActivity extends AppCompatActivity {

    public List<Order> listordercart;
    private AdaptorCart cartadaptercustom;
    int final_total=0;
    TextView txtshowtotal;
    Button edit,remove,checkout;
    ListView lstCart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        txtshowtotal=(TextView)findViewById(R.id.tvgtot);
        remove=(Button)findViewById(R.id.butremove);
        edit=(Button)findViewById(R.id.butedit);

        checkout=(Button)findViewById(R.id.butchkout);
        lstCart=(ListView)findViewById(R.id.lstcart);
        listordercart=(List<Order>) ProductDetailActivity.getCart();

        for (int i=0;i<listordercart.size();i++){
            listordercart.get(i).SelectedCheckBox=false;
            final_total+=listordercart.get(i).getTot();
            Log.d("Total in cart",Integer.toString(listordercart.get(i).getTot()));

        }

            cartadaptercustom=new AdaptorCart(listordercart,getLayoutInflater());

            txtshowtotal.setText(Integer.toString(final_total));
            Log.d("final total",Integer.toString(final_total));
            lstCart.setAdapter(cartadaptercustom);


            lstCart.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.d("lstCart","lstCart clicked");

                    Order selectedProduct=listordercart.get(position);

                    if (selectedProduct.SelectedCheckBox == true) {
                        selectedProduct.SelectedCheckBox = false;
                        Log.d("lstCart","lstCart if");
                    }

                    else {
                        selectedProduct.SelectedCheckBox = true;
                        Log.d("lstCart","lstCart else");
                    }
                    cartadaptercustom.notifyDataSetInvalidated();
                }
            });

          /*  listView_cart.setOnItemClickListener(new AdapterView.OnItemClickListener() {
               @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Order selectedProduct=listordercart.get(position);

                    if (selectedProduct.SelectedCheckBox=true)
                        selectedProduct.SelectedCheckBox=false;

                    else
                        selectedProduct.SelectedCheckBox=true;

                    cartadaptercustom.notifyDataSetInvalidated();
                }
            });*/

            remove.setOnClickListener(new View.OnClickListener() {
                int Total;
                @Override
                public void onClick(View v) {

                    for (int i=0;i<listordercart.size();i++){
                        if (listordercart.get(i).SelectedCheckBox)
                        {
                            Total=listordercart.get(i).getTot();
                            listordercart.remove(i);
                        }
                        cartadaptercustom.notifyDataSetChanged();
                        final_total-=Total;
                        txtshowtotal.setText(Integer.toString(final_total));
                    }


                }
            });

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i=0;i<listordercart.size();i++)
                    {
                        if (listordercart.get(i).SelectedCheckBox)
                        {
                            int price;
                            int position=i;
                            Intent intent_i = new Intent(getApplicationContext(),EditCartActivity.class);
                            intent_i.putExtra("pro_id", listordercart.get(position).getPro_id());
                            intent_i.putExtra("pro_name", listordercart.get(position).getPro_name());
                            intent_i.putExtra("pro_desc", listordercart.get(position).getPro_desc());
                            intent_i.putExtra("pro_price",listordercart.get(position).getPro_price());
                            intent_i.putExtra("pro_img", listordercart.get(position).getPro_img());
                            intent_i.putExtra("pro_qty", listordercart.get(position).getQty());
                            intent_i.putExtra("final_total", final_total);
                            startActivity(intent_i);

                        }
                    }
                }
            });

            checkout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent ii = new Intent(getApplicationContext(),LoginActivity.class);
                    ii.putExtra("from", "Cart");
                    ii.putExtra("final_total", final_total);
                    startActivity(ii);
                }
            });
    }
}
