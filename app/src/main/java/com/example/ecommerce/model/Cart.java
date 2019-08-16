package com.example.ecommerce.model;

import java.util.ArrayList;

public class Cart {
    public ArrayList<Order> Order_Cart_List = new ArrayList<Order>();

    public int final_Total;


    public Cart() {
        super();
        final_Total = 0;
    }

    public int getFinal_Total() {
        return final_Total;
    }

    public void setFinal_Total(int final_Total) {
        this.final_Total = final_Total;
    }

    public void addfinal_Total(int total){
        final_Total +=total;
    }

    public void substract_final_Total(int total){
        final_Total -=total;
    }


    public Order getCartItem(int position)
    {
        return Order_Cart_List.get(position);
    }

    public void setCartItem(Order cartitm)
    {
        Order_Cart_List.add(cartitm);
    }

    public int getCart_Size()
    {
        return Order_Cart_List.size();
    }

    public boolean checkProductInCart(Order product) {

        return Order_Cart_List.contains(product);

    }

}
