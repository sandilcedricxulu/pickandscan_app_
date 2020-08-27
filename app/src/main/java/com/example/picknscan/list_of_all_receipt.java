package com.example.picknscan;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class list_of_all_receipt extends Fragment {
    TextView selected_receipt_number;


    public list_of_all_receipt() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_of_all_receipt, container, false);
        selected_receipt_number = view.findViewById(R.id.selected_receipt_number);

        if(getArguments() != null){
            selected_receipt_number.setText(getArguments().getString("selected_receipt_number"));
        }
        else {
            selected_receipt_number.setText("am here and angtholanga lutho");
        }

        return  view;
    }
}
