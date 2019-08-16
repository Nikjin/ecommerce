package com.example.ecommerce.model;

public class Subcategory {
    int subcat_id;
    int cat_id;
    String subcat_name;
    String subcat_img;

    public Subcategory(int subcat_id, int cat_id, String subcat_name, String subcat_img) {
        this.subcat_id = subcat_id;
        this.cat_id = cat_id;
        this.subcat_name = subcat_name;
        this.subcat_img = subcat_img;
    }

    public int getSubcat_id() {
        return subcat_id;
    }

    public void setSubcat_id(int subcat_id) {
        this.subcat_id = subcat_id;
    }

    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }

    public String getSubcat_name() {
        return subcat_name;
    }

    public void setSubcat_name(String subcat_name) {
        this.subcat_name = subcat_name;
    }

    public String getSubcat_img() {
        return subcat_img;
    }

    public void setSubcat_img(String subcat_img) {
        this.subcat_img = subcat_img;
    }
}
