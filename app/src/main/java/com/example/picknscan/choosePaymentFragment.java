package com.example.picknscan;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import  com.google.zxing.MultiFormatWriter;
import com.google.zxing.Writer;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Hashtable;

import static com.example.picknscan.PayWithCash.sharedPreferences;


public class choosePaymentFragment extends Fragment {

    Button buttonPayOnline;
    Button buttonPayCash;
    ImageView imageViewCode;
    SharedPreferences pref;
    SharedPreferences sharedPreferences2;
    String totalAmount;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.choosepaymentfragment, container, false);
        buttonPayOnline = view.findViewById(R.id.pay_online);
        buttonPayCash = view.findViewById(R.id.pay_cash);
        pref = getContext().getSharedPreferences("MyPref", 0);
        sharedPreferences2 = getContext().getSharedPreferences("save_my_products", 0);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        if(getArguments() != null){
            totalAmount = getArguments().getString("amount");
        }


        buttonPayOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(HttpMethods.isOnline()){
                    Fragment fragment = new webview_online_payment();
                    Bundle args = new Bundle();
                    args.putString("amount",totalAmount);
                    args.putString("email",pref.getString("Email",null));
                    args.putString("cell",pref.getString("Cellphone",null));
                    args.putString("name",pref.getString("FirstName",null));
                    args.putString("surname",pref.getString("LastName",null));
                    fragment.setArguments(args);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }else {
                    Toast.makeText(getContext(), "Check your internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonPayCash.setOnClickListener(new View.OnClickListener() {
            Bitmap bitmapSupreme;
            int overrallInvoice = 0;
            @Override
            public void onClick(View v) {
                try {
                    loadData();
                    if(HttpMethods.isOnline()){
                        overrallInvoice =  PayWithCash.InsertInvoice(products,getContext());
                    }else {
                        Toast.makeText(getContext(), "Check your internet connection", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (IOException e) {
                    Toast.makeText(getContext(), ""+e, Toast.LENGTH_SHORT).show();
                }
                catch (JSONException e) {
                    Toast.makeText(getContext(), ""+e, Toast.LENGTH_SHORT).show();
                }
                try{
                    String value = String.valueOf(overrallInvoice);
                    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                    BitMatrix bitMatrix = multiFormatWriter.encode(value, BarcodeFormat.QR_CODE,200,200);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    bitmapSupreme = bitmap;
                }
                catch (Exception e){
                    Toast.makeText(getActivity().getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                ImageView image = new ImageView(getActivity());
                image.setImageBitmap(bitmapSupreme);
                builder.setTitle("Show this Barcode to the cashier");
                builder.setMessage(""+overrallInvoice)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        }).setView(image);
                AlertDialog alert = builder.create();
                alert.show();
                Button button = alert.getButton(DialogInterface.BUTTON_POSITIVE);

                if(button != null ) {
                    button.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_rounded));
                }
            }
        });
        return view;
    }

    public static ArrayList<Products> products = new ArrayList<>();

    private void loadData() {
        Toast.makeText(getContext(), "Executed", Toast.LENGTH_LONG).show();
        Gson gson = new Gson();
        String json = sharedPreferences2.getString("myProductList", null);
        Type type = new TypeToken<ArrayList<Products>>() {}.getType();
        products = gson.fromJson(json, type);

        if (products == null) {
            products = new ArrayList<>();
            System.out.println("Hey Mxo Im null");
        }
        Toast.makeText(getContext(), "Hey Mxo I got "+products.size()+" product while i was reading", Toast.LENGTH_SHORT).show();
        return;
    }
}
