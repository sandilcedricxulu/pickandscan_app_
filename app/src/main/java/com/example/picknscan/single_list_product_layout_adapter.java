package com.example.picknscan;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.stripe.param.PlanCreateParams;

import java.util.ArrayList;
import java.util.List;

public class single_list_product_layout_adapter extends ArrayAdapter<Products> {

    Context context;
    LayoutInflater inflter;
    Products product;

    public single_list_product_layout_adapter(Context applicationContext,ArrayList<Products> productsArrayList){
        super(applicationContext,0,productsArrayList);
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if(view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.single_list_of_product_layout, null);
        }

        product = getItem(position);
        TextView product_name = view.findViewById(R.id.product_name);
        TextView product_quantity = view.findViewById(R.id.product_quantity);
        TextView product_price = view.findViewById(R.id.product_price);

        product_name.setText(product.ProductName()+"  "+product.QuantityPerUnit());
        product_quantity.setText(String.valueOf(product.Quantity()));
        product_price.setText(String.valueOf(product.Total(product.Quantity(),product.UnitPrice())));

        return view;
    }


}
