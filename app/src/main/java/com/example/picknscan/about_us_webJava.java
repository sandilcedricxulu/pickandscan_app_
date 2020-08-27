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

public class about_us_webJava extends Fragment {

    double amount = 0;
    public about_us_webJava() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.about_us_webview, container, false);

        WebView onlinePyament = view.findViewById(R.id.about_us_web);
        onlinePyament.setWebViewClient(new WebViewClient());
        onlinePyament.loadUrl("file:///android_asset/about-us.html");

        WebSettings settings = onlinePyament.getSettings();
        settings.setJavaScriptEnabled(true);
//        WebView onlinePayment = view.findViewById(R.id.about_us_web);
//        onlinePayment.setWebViewClient(new WebViewClient());
//        WebSettings settings = onlinePayment.getSettings();
//        settings.setJavaScriptEnabled(true);
//        onlinePayment.loadUrl("https://picknscan.000webhostapp.com/pickscan/web/about-us.html");
        return  view;
    }
}
