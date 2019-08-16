package com.example.ecommerce.controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ecommerce.R;
import com.example.ecommerce.model.Connect;
import com.example.ecommerce.model.Subcategory;
import com.loopj.android.image.SmartImageView;

import java.util.List;

public class AdaptorSubCategory extends BaseAdapter {

    private List<Subcategory> SubCategory_List;
    private LayoutInflater SubCategory_Inflater;
    String Path;

    public AdaptorSubCategory(List<Subcategory> subcategory_List,
                              LayoutInflater subcategory_Inflater) {
        super();
        SubCategory_List = subcategory_List;
        SubCategory_Inflater = subcategory_Inflater;
    }


    @Override
    public int getCount() {
        return SubCategory_List.size();
    }

    @Override
    public Object getItem(int position) {
        return SubCategory_List.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int arg_position, View arg_Convert_View, ViewGroup arg2_Group_Parent) {
        // TODO Auto-generated method stub
        final ViewSubCategory viewsubcat;

        if(arg_Convert_View == null)
        {
            arg_Convert_View = SubCategory_Inflater.inflate(R.layout.subcategory, null);
            viewsubcat = new ViewSubCategory();//memory allocation
            viewsubcat.SubCategoryImage = (SmartImageView) arg_Convert_View.findViewById(R.id.ImgVw_subCategory);//means add the image in the category.xml
            viewsubcat.SubCategoryName = (TextView)arg_Convert_View.findViewById(R.id.TvsubCategory_Name);//means add the category name in the category.xml
            arg_Convert_View.setTag(viewsubcat);


        }
        else
        {
            viewsubcat =(ViewSubCategory) arg_Convert_View.getTag();
        }

        Subcategory cat = SubCategory_List.get(arg_position);
        Path = Connect.Path+"images/"+cat.getSubcat_img();
        viewsubcat.SubCategoryImage.setImageUrl(Path);
        viewsubcat.SubCategoryName.setText(cat.getSubcat_name());


        return arg_Convert_View;
    }

    private class ViewSubCategory{
        public TextView SubCategoryName;
        SmartImageView SubCategoryImage;
    }



}
