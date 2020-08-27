package com.example.picknscan;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class receiptListClass implements ListAdapter {

    Context context;
    String receiptNumber[];
    String dates[];
    String price[];

    LayoutInflater inflter;

    public receiptListClass(Context applicationContext, String[] receiptNumber,String[] dates,String[] price){
        this.context = applicationContext;
        this.dates = dates;
        this.receiptNumber = receiptNumber;
        this.price = price;

        inflter = (LayoutInflater.from(applicationContext));
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.fragment_receipt_view_layout, null);
        TextView receiptNumbers = (TextView)view.findViewById(R.id.receipt_number);
        TextView date = (TextView)view.findViewById(R.id.receipt_date);
        TextView receiptTotalPrice = (TextView)view.findViewById(R.id.receipt_time);

        receiptNumbers.setText(receiptNumber[i]);
        date.setText(dates[i]);
        receiptTotalPrice.setText(price[i]);

        return view;
    }


    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }
    @Override
    public int getCount() {
        return receiptNumber.length;
    }

    List<receiptListClass> receiptList = new ArrayList<>();
    @Override
    public receiptListClass getItem(int position) {
        return receiptList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
/*    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }*/

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
