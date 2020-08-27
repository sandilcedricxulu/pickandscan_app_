package com.example.picknscan;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class receipt_view_layout extends Fragment {

    public receipt_view_layout() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_receipt_view_layout, container, false);

        ListView list = (ListView) view.findViewById(R.id.card_list_view);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "Item : "+position+" is clicked", Toast.LENGTH_SHORT).show();
                System.out.println("---------------------------------------------------------------");
                System.out.println("---------------------------------------------------------------");
                System.out.println("---------------------------------------------------------------");
                System.out.println("---------------------------------------------------------------");
                System.out.println("---------------------------------------------------------------");
                System.out.println("---------------------------------------------------------------");
            }
        });

        return view;
    }


}
