package com.example.picknscan;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class faqJava extends Fragment {

    double amount = 0;
    public faqJava() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.faq_webview, container, false);

        WebView onlinePyament = view.findViewById(R.id.faq_web);
        onlinePyament.setWebViewClient(new WebViewClient());
        onlinePyament.loadUrl("file:///android_asset/faq.html");

        WebSettings settings = onlinePyament.getSettings();
        settings.setJavaScriptEnabled(true);
//
//        WebView onlinePayment = view.findViewById(R.id.faq_web);
//        onlinePayment.setWebViewClient(new WebViewClient());
//        WebSettings settings = onlinePayment.getSettings();
//        settings.setJavaScriptEnabled(true);
//        onlinePayment.loadUrl("https://picknscan.000webhostapp.com/pickscan/web/faq.html");
        return  view;
    }
}
