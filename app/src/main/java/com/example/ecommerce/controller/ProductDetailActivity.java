package com.example.ecommerce.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerce.R;
import com.example.ecommerce.model.Connect;
import com.example.ecommerce.model.Order;
import com.loopj.android.image.SmartImageView;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailActivity extends AppCompatActivity {

    int pro_id;
    String Path;
    String pro_name;
    String pro_img;
    int pro_price;
    String pro_desc;
    String pro_comp;
    SmartImageView imgVw_p_detail;
    TextView tv_p_name,tv_p_desc,tv_p_qty,tv_p_price,tv_p_comp;
    Button butCart;
    public static List<Order> orderList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        tv_p_comp = (TextView)findViewById(R.id.tv_p_comp);
        tv_p_desc = (TextView)findViewById(R.id.tv_p_desc);

        tv_p_price = (TextView)findViewById(R.id.tv_p_price);
        tv_p_name = (TextView)findViewById(R.id.tv_p_name);
        tv_p_qty = (TextView)findViewById(R.id.edt_p_qty);
        imgVw_p_detail=(SmartImageView)findViewById(R.id.imgVw_p_detail);
        butCart = (Button)findViewById(R.id.butCart);


        Intent i = getIntent();
        pro_comp= i.getStringExtra("pro_comp");
        pro_img= i.getStringExtra("pro_img");
        pro_name= i.getStringExtra("pro_name");
        pro_desc= i.getStringExtra("pro_desc");
        pro_price=i.getIntExtra("pro_price",0);
        pro_id=i.getIntExtra("pro_id",0);

        tv_p_comp.setText(pro_comp.toString());
        tv_p_name.setText(pro_name.toString());
        tv_p_desc.setText(pro_desc.toString());
        tv_p_price.setText(Integer.toString(pro_price));

        Path= Connect.Path+"images/"+pro_img;
        imgVw_p_detail.setImageUrl(Path);

        orderList=getCart();
        butCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCart();
                Toast.makeText(getApplicationContext(),"Item Added in to Cart",Toast.LENGTH_LONG).show();
                finish();
            }


        });
    }

    public void addCart() {
        int qty,tot;
        String q = tv_p_qty.getText().toString();
        qty=Integer.parseInt(q);
        tot=qty*pro_price;

        Order ord=new Order( pro_id,  pro_name,  pro_img,  pro_price,  pro_desc,  pro_comp,  qty,  tot);
        orderList.add(ord);

    }

    public static List<Order> getCart() {
        if (orderList==null)
        {
            orderList = new ArrayList<Order>();
        }
        return orderList;

    }
    public static void setCart(List<Order> ordList) {

        if (orderList == null) {
            orderList = new ArrayList<Order>();
        } else {
            orderList = ordList;
        }
    }

}
