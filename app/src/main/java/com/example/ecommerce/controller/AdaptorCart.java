package com.example.ecommerce.controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.ecommerce.R;
import com.example.ecommerce.model.Connect;
import com.example.ecommerce.model.Order;
import com.loopj.android.image.SmartImageView;

import java.util.List;

public class AdaptorCart extends BaseAdapter {

    private List<Order> productlist;
    private LayoutInflater ordcartlayoutInflater;
    boolean mShowCheckbox;
    String Path;

    public AdaptorCart(List<Order> list,LayoutInflater inflater){
        productlist=list;
        ordcartlayoutInflater=inflater;
        mShowCheckbox=true;
    }


    @Override
    public int getCount() {
        return productlist.size();
    }

    @Override
    public Object getItem(int position) {
        return productlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewCart order;

        if (convertView==null)
        {
            convertView=ordcartlayoutInflater.inflate(R.layout.cart,null);
            order=new ViewCart();
            order.ItemImage=(SmartImageView)convertView.findViewById(R.id.ImageViewCategory);
            order.ProductName=(TextView)convertView.findViewById(R.id.txtprdname);
            order.Price=(TextView)convertView.findViewById(R.id.txtprice);
            order.qty=(TextView)convertView.findViewById(R.id.txtqnt);
            order.totalPrice = (TextView)convertView.findViewById(R.id.txttotprice);
            order.chkbox = (CheckBox)convertView.findViewById(R.id.chkSel);
            convertView.setTag(order);
        }

        else
        {
            order=(ViewCart)convertView.getTag();
        }

        Order curProduct=productlist.get(position);
        Path= Connect.Path+"images/"+curProduct.getPro_img();
        order.ItemImage.setImageUrl(Path);
        order.qty.setText(Integer.toString(curProduct.getQty()));
        order.Price.setText(Integer.toString(curProduct.getPro_price()));
        order.totalPrice.setText(Integer.toString(curProduct.getTot()));
        order.ProductName.setText(curProduct.getPro_name());



        if(!mShowCheckbox) {
            order.chkbox.setVisibility(View.GONE);}
        if (curProduct.SelectedCheckBox==true)
            order.chkbox.setChecked(true);

        else
            order.chkbox.setChecked(false);

        return convertView;



    }

    public class ViewCart {
        SmartImageView ItemImage;
        TextView ProductName;
        TextView Price;
        TextView totalPrice;
        TextView qty;
        CheckBox chkbox;

    }
}
