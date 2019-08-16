package com.example.ecommerce.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerce.R;
import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.Connect;
import com.example.ecommerce.model.Order;
import com.loopj.android.image.SmartImageView;

import java.util.List;

public class EditCartActivity extends AppCompatActivity {

    String p_name,p_desc,p_img,Path;
    int p_price,p_qty,p_id,final_total;
    TextView editquantity,txtname,txtdes,txtprice,txtquantity;
    Button butupdate;
    SmartImageView ImgVw_Edt_cart;
    public static List<Order> orderList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_cart);

        editquantity=(TextView)findViewById(R.id.editquantity);
        txtname=(TextView)findViewById(R.id.txteditname);
        txtquantity=(TextView)findViewById(R.id.txteditquantity);

        txtdes=(TextView)findViewById(R.id.txteditdes);
        txtprice=(TextView)findViewById(R.id.txteditprice);
        ImgVw_Edt_cart=(SmartImageView)findViewById(R.id.ImgVw_Edt_cart);
        butupdate=(Button)findViewById(R.id.butupdate);

        Intent i= getIntent();
        p_id=i.getIntExtra("pro_id",0);
        p_desc=i.getStringExtra("pro_desc");
        p_name=i.getStringExtra("pro_name");
        p_price=i.getIntExtra("pro_price",0);
        p_qty =i.getIntExtra("pro_qty",0);
        final_total=i.getIntExtra("final_total",0);
        p_img=i.getStringExtra("pro_img");


        txtname.setText(p_name.toString());
        txtdes.setText(p_desc.toString());
        editquantity.setText(Integer.toString(p_qty));
        txtprice.setText(Integer.toString(p_price));
        Path = Connect.Path+"images/";
        ImgVw_Edt_cart.setImageUrl(Path+p_img);
        orderList=ProductDetailActivity.getCart();
        butupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Update","update called");

                for (int i=0;i<orderList.size();i++)
                {
                    Log.d("itd",Integer.toString(p_id));
                    if (orderList.get(i).getPro_id()==p_id)
                    {
                        try {
                            String Qty = editquantity.getText().toString();
                            Log.d("Qty...", editquantity.getText().toString());
                            int Quantity = Integer.parseInt(Qty);

                            int Price = orderList.get(i).getPro_price();

                            if (Quantity <= 0) {
                                Toast.makeText(getBaseContext(), "please enter a Quantity higher than 0", Toast.LENGTH_SHORT).show();
                                return;
                            } else {
                                Cart cart = new Cart();
                                int Total = orderList.get(i).getTot();

                                final_total -= Total;
                                int Tot = Quantity * Price;
                                Log.d("Tot...", Integer.toString(Tot));
                                final_total += Tot;
                                Log.d("Final Total", Integer.toString(final_total));
                                orderList.get(i).setTot(Tot);
                                orderList.get(i).setQty(Quantity);
                                cart.setFinal_Total(final_total);
                                ProductDetailActivity.setCart(orderList);

                                Intent move = new Intent(getApplicationContext(), CartActivity.class);
                                startActivity(move);


                            }
                        }
                        catch (NumberFormatException e) {
                            Toast.makeText(getBaseContext(),
                                    "Please Enter the Numeric Quantity",
                                    Toast.LENGTH_SHORT).show();
                            return;

                        }


                    }
                }

            }
        });

    }
}
