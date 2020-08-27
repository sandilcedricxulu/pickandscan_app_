package com.example.picknscan;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ListView;

public class webview_online_payment extends Fragment {

    double amount = 0;
    String name = "",surname = "",cellnumber = "",email="";
    public webview_online_payment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.webview_online_payment, container, false);

        if(getArguments() != null)
        {
            amount = Double.parseDouble(getArguments().getString("amount"));
            name = getArguments().getString("name");
            surname = getArguments().getString("surname");
            email = getArguments().getString("email");
            cellnumber = getArguments().getString("cell");
            System.out.println("Hey amount is "+amount);
        }
        System.out.println("Hey amount is "+amount);

        WebView onlinePayment = view.findViewById(R.id.webview_layout);
        onlinePayment.setWebViewClient(new WebViewClient());
        WebSettings settings = onlinePayment.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);// Add this
        settings.setDatabaseEnabled(true);// Add this
        onlinePayment.addJavascriptInterface(new WebAppInterface(getActivity()), "Android");
        onlinePayment.loadUrl("http://mkhululi.net/web/payonline.html");
        return  view;
    }
    public class WebAppInterface {

        Context mContext;

        WebAppInterface(Context c) {
            mContext = c;
        }
        @JavascriptInterface
        public double getPayAmount(){
            return amount;
        }
        @JavascriptInterface
        public String getFName(){
            return name;
        }
        @JavascriptInterface
        public String getSurname(){
            return surname;
        }
        @JavascriptInterface
        public String getEmailAddress(){
            return email;
        }
        @JavascriptInterface
        public String getCellNumber(){
            return cellnumber;
        }

    }
}
