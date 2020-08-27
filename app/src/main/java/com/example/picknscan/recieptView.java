package com.example.picknscan;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class recieptView extends Fragment {

    ListView card_list_view;
    private ProgressDialog progressDialog;
    private receiptListAdapter adapter;
    List<Invoice> invoices;
    public recieptView() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reciept_view, container, false);
        card_list_view = view.findViewById(R.id.card_list_view);
        progressDialog = new ProgressDialog(getContext());
        adapter = null;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            if(HttpMethods.isOnline()){
                invoices = FindReceipt.getAllInvoices(getContext());
                progressDialog.setMessage("Loading invoices.....");
                progressDialog.show();
                Runnable progressRunnable = new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.cancel();
                        if(invoices.size() == 0)
                        {
                            Toast.makeText(getContext(), "You dont have receipts", Toast.LENGTH_SHORT).show();
                            Fragment fragment = new AcountFragment();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_container, fragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }
                        else {
                            adapter = new receiptListAdapter(getActivity().getApplicationContext(),invoices );
                            card_list_view.setAdapter(adapter);
                        }
                    }
                };
                Handler pdCanceller = new Handler();
                pdCanceller.postDelayed(progressRunnable,3000);
            }else {
                Toast.makeText(getContext(), "Check your internet connection", Toast.LENGTH_SHORT).show();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }



        card_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "Item "+position+" has been clicked", Toast.LENGTH_SHORT).show();
                String invoiceID = invoices.get(position).getInvoiceID();
                String invoiceDate = invoices.get(position).getInvoiceDate();
                String invoiceTime = invoices.get(position).getInvoiceTime();
                Fragment fragment = new singlereceiptlayout();
                Bundle args = new Bundle();
                args.putString("InvoiceID", invoiceID);
                args.putString("InvoiceDate", invoiceDate);
                args.putString("InvoiceTime", invoiceTime);
                fragment.setArguments(args);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();


                for (int i = 0; i < card_list_view.getChildCount(); i++) {
                    if(position == i ){
                        card_list_view.getChildAt(i).setBackgroundColor(Color.rgb(245,245,245));
                    }
                    else{
                        card_list_view.getChildAt(i).setBackgroundColor(Color.rgb(255,255,255));
                    }
                }

            }
        });

        return view;
    }

    //internal adapter class
    class receiptListAdapter extends ArrayAdapter<Invoice> {

        Context context;
        Invoice customerReciept;

        public receiptListAdapter(Context context,List<Invoice> customerReciept) {
            super(context,0, customerReciept);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if(view == null)
            {
                view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_receipt_view_layout, parent, false);
            }
             customerReciept = getItem(position);
            TextView receiptNumbers = view.findViewById(R.id.receipt_number);
            TextView date = view.findViewById(R.id.receipt_date);
            TextView receiptTime = view.findViewById(R.id.receipt_time);

            receiptNumbers.setText("Receipt #"+customerReciept.getInvoiceID());
            date.setText(customerReciept.getInvoiceDate());
            receiptTime.setText(customerReciept.getInvoiceTime());

            return view;
        }
    }
}
