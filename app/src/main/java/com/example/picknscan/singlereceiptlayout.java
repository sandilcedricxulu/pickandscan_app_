package com.example.picknscan;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class singlereceiptlayout extends Fragment {

    ListView product_items_list_view;
    TextView invoice_number,invoice_date,invoice_time,invoice_quantity,invoice_subtotal,invoice_vat,invoice_total;

    public singlereceiptlayout() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.singlereceiptlayout, container, false);
        product_items_list_view = view.findViewById(R.id.purchasedProducts);
        invoice_number = view.findViewById(R.id.invoice_number);
        invoice_date = view.findViewById(R.id.invoice_date);
        invoice_time = view.findViewById(R.id.invoice_time);
        invoice_quantity = view.findViewById(R.id.invoice_quantity);
        invoice_subtotal = view.findViewById(R.id.invoice_subtotal);
        invoice_vat = view.findViewById(R.id.invoice_vat);
        invoice_total = view.findViewById(R.id.invoice_total);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            returnedValues();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  view;
    }


    private void returnedValues() throws IOException, JSONException {
        double subTotal = 0,vat = 0.0,total = 0;
        if(getArguments() != null) {
            int invoiceID = Integer.parseInt(getArguments().getString("InvoiceID"));
            invoice_number.setText("Invoice #"+getArguments().getString("InvoiceID"));
            invoice_date.setText(getArguments().getString("InvoiceDate"));
            invoice_time.setText(getArguments().getString("InvoiceTime"));
            if(HttpMethods.isOnline()){
                List<Products> productsList = FindReceipt.findProductID(getContext(),invoiceID);
                invoice_quantity.setText(String.valueOf(productsList.size()) +" Items");

                for(int i = 0;i < productsList.size();i++){
                    subTotal = subTotal + productsList.get(i).Total(productsList.get(i).Quantity(),productsList.get(i).UnitPrice());
                }
                vat = subTotal * 0.15;
                total = subTotal + vat;

                invoice_subtotal.setText("Subtotal Exc. VAT R"+Math.round(subTotal*100.0)/100.0);
                invoice_vat.setText("VAT R"+Math.round(vat*100.0)/100.0);
                invoice_total.setText("Total Inc. VAT R"+Math.round(total*100.0)/100.0);
                single_list_product_layout_adapter adapter;
                adapter = new single_list_product_layout_adapter(getActivity().getApplicationContext(), (ArrayList<Products>) productsList);
                product_items_list_view.setAdapter(adapter);
            }else {
                Toast.makeText(getContext(), "Check your internet connection", Toast.LENGTH_SHORT).show();
            }

        }

    }
}
