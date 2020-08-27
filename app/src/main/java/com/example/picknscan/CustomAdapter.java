package com.example.picknscan;
import android.content.Context;
import android.database.DataSetObserver;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.zip.Inflater;

public class CustomAdapter implements ListAdapter {
    Context context;
    String availableCards[];
    String exp_date[];
    LayoutInflater inflter;

    public CustomAdapter(Context applicationContext, String[] availableCards,String[] exp_date) {
        this.context = context;
        this.availableCards = availableCards;
        this.exp_date = exp_date;
        inflter = (LayoutInflater.from(applicationContext));
    }

    public int getCount_availableCards() {
        return availableCards.length;
    }

    public int getCount_exp_date() {
        return availableCards.length;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return availableCards.length;
    }

    public Object getItem(int i) {
        return null;
    }

    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.fragment_card_list_items, null);
        TextView cardType = (TextView)view.findViewById(R.id.card_type_n_number);
        TextView expDate = (TextView)view.findViewById(R.id.exp_date);
        //ImageView icon = (ImageView) view.findViewById(R.id.icon);
        cardType.setText(availableCards[i]);
        expDate.setText(exp_date[i]);
        return view;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }
}
