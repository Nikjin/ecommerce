package com.example.ecommerce.controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ecommerce.R;
import com.example.ecommerce.model.Connect;
import com.example.ecommerce.model.Product;
import com.loopj.android.image.SmartImageView;

import java.util.List;

public class AdaptorProduct extends BaseAdapter {

    private List<Product> Product_List;
    private LayoutInflater Product_Inflater;
    String Path;
    public AdaptorProduct(List<Product> product_List,LayoutInflater product_Inflater)
    {

        Product_List=product_List;
        Product_Inflater=product_Inflater;

    }

    @Override
    public int getCount() {
        return Product_List.size();
    }

    @Override
    public Object getItem(int position) {
        return Product_List.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewProduct viewproduct;
        if (convertView==null)
        {
            convertView=Product_Inflater.inflate(R.layout.product,null);
            viewproduct=new ViewProduct();
            viewproduct.ProductName=(TextView)convertView.findViewById(R.id.Tvproduct_Name);
            viewproduct.ProductImage=(SmartImageView)convertView.findViewById(R.id.ImgVw_product);
            convertView.setTag(viewproduct);
        }
        else
            {

                viewproduct =(ViewProduct) convertView.getTag();
        }

        Product product=Product_List.get(position);
        Path = Connect.Path+"images/"+product.getPro_img();

        viewproduct.ProductImage.setImageUrl(Path);
        viewproduct.ProductName.setText(product.getPro_name());


        return convertView;


    }

    private class ViewProduct {
        public TextView ProductName;
        SmartImageView ProductImage;
    }
}
