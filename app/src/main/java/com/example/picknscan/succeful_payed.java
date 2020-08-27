package com.example.picknscan;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;

public class succeful_payed extends Fragment {

    double amount = 0;
    public succeful_payed() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.webview_online_payment, container, false);
        if(getArguments() != null)
        {
            amount = Double.parseDouble(getArguments().getString("amount"));
        }
        System.out.println("Hey amount is "+amount);

        WebView onlinePayment = view.findViewById(R.id.webview_layout);
        onlinePayment.setWebViewClient(new WebViewClient());
        WebSettings settings = onlinePayment.getSettings();
        settings.setJavaScriptEnabled(true);
        onlinePayment.addJavascriptInterface(new WebAppInterface(getActivity()), "Android");
        onlinePayment.loadUrl("http://mkhululi.net/web/pay-success.html");
        return  view;
    }
    public class WebAppInterface {

        Context mContext;

        WebAppInterface(Context c) {
            mContext = c;
        }
        @JavascriptInterface
        public void CreateInvoice() throws IOException, JSONException {
            System.out.println("Hey i got the call from javascript what do you want me to do");
            final int invoiceID = CreateInvoiceForOnlinePurchase.createInvoice(getContext());
            System.out.println("The invoice id is "+invoiceID);
            System.out.println("I need to display slip now");
            Fragment fragment = new singlereceiptlayout();
            Bundle args = new Bundle();
            args.putString("InvoiceID", String.valueOf(invoiceID));
            args.putString("InvoiceDate", CreateInvoiceForOnlinePurchase.getInvoiceDate());
            args.putString("InvoiceTime", CreateInvoiceForOnlinePurchase.getInvoiceTime());
            fragment.setArguments(args);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }

    }
}
