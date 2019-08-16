package com.example.ecommerce.controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ecommerce.R;
import com.example.ecommerce.model.Category;
import com.example.ecommerce.model.Connect;
import com.loopj.android.image.SmartImageView;

import java.util.List;

public class AdaptorCategory extends BaseAdapter {


    private List<Category> Category_List;
    private LayoutInflater Category_Inflater;
    String Path;

    public AdaptorCategory(List<Category> category_List,LayoutInflater category_Inflater)
    {
        super();
        Category_List=category_List;
        Category_Inflater=category_Inflater;

    }



    @Override
    public int getCount() {
        return Category_List.size();
    }

    @Override
    public Object getItem(int position) {
        return Category_List.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int arg_position, View arg_Convert_View, ViewGroup arg2_Group_Parent) {

        final ViewCategory viewcat;

        if (arg_Convert_View == null) {
            arg_Convert_View = Category_Inflater.inflate(R.layout.category, null);

            viewcat = new ViewCategory();
            viewcat.CategoryName=(TextView)arg_Convert_View.findViewById(R.id.TvCategory_Name);
            viewcat.CategoryImage=(SmartImageView)arg_Convert_View.findViewById(R.id.ImgVw_Category);
            arg_Convert_View.setTag(viewcat);
        }


        else
        {
            viewcat=(ViewCategory)arg_Convert_View.getTag();
        }

        Category cat=Category_List.get(arg_position);
        Path = Connect.Path+"images/"+cat.getCat_img();
        viewcat.CategoryImage.setImageUrl(Path);
        viewcat.CategoryName.setText(cat.getCat_name());

        return arg_Convert_View;

    }

        private class ViewCategory{

        public TextView CategoryName;
        SmartImageView CategoryImage;

        }
}
