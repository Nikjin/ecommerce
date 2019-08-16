package com.example.ecommerce.model;

public class Order {
    int pro_id;
    int subcat_id;
    String pro_name;
    String pro_img;
    int pro_price;
    String pro_desc;
    String pro_comp;
    int qty;
    int tot;
    public boolean SelectedCheckBox;



    public Order(int pro_id, String pro_name, String pro_img, int pro_price, String pro_desc, String pro_comp, int qty, int tot) {
        this.pro_id = pro_id;

        this.pro_name = pro_name;
        this.pro_img = pro_img;
        this.pro_price = pro_price;
        this.pro_desc = pro_desc;
        this.pro_comp = pro_comp;
        this.qty = qty;
        this.tot = tot;
    }

    public int getPro_id() {
        return pro_id;
    }

    public void setPro_id(int pro_id) {
        this.pro_id = pro_id;
    }

    public int getSubcat_id() {
        return subcat_id;
    }

    public void setSubcat_id(int subcat_id) {
        this.subcat_id = subcat_id;
    }

    public String getPro_name() {
        return pro_name;
    }

    public void setPro_name(String pro_name) {
        this.pro_name = pro_name;
    }

    public String getPro_img() {
        return pro_img;
    }

    public void setPro_img(String pro_img) {
        this.pro_img = pro_img;
    }

    public int getPro_price() {
        return pro_price;
    }

    public void setPro_price(int pro_price) {
        this.pro_price = pro_price;
    }

    public String getPro_desc() {
        return pro_desc;
    }

    public void setPro_desc(String pro_desc) {
        this.pro_desc = pro_desc;
    }

    public String getPro_comp() {
        return pro_comp;
    }

    public void setPro_comp(String pro_comp) {
        this.pro_comp = pro_comp;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getTot() {
        return tot;
    }

    public void setTot(int tot) {
        this.tot = tot;
    }
}
