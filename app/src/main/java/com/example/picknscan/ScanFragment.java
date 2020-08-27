package com.example.picknscan;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.Result;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import static android.Manifest.permission.CAMERA;


public class ScanFragment extends Fragment implements ZXingScannerView.ResultHandler {

    private static final int REQUEST_CAMERA = 100;
    private ZXingScannerView scannerView;
    SharedPreferences pref;
    String from = "";
    SharedPreferences.Editor editor;


//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        scannerView = new ZXingScannerView(getActivity());
//        Log.i("scannerView", "onCreate()");
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
//            if (CheckPermmision()) {
//                Toast.makeText(getActivity(), "Permission is granted", Toast.LENGTH_LONG).show();
//            } else {
//                requestPermission();
//            }
//        }
//    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(getArguments() != null){
            if(getArguments().getString("from").equals("fistScanFragment")) {
                from = "fistScanFragment";
                System.out.println("From is "+from);
            }
        }
        System.out.println("From is "+from);
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(getActivity());
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            if (CheckPermmision()) {
                Toast.makeText(getActivity(), "Permission is granted", Toast.LENGTH_LONG).show();
            } else {
                requestPermission();
            }
        }

        pref = getActivity().getApplicationContext().getSharedPreferences("MyPref",0);
        editor = pref.edit();


        return scannerView;
    }

    private boolean CheckPermmision() {
        return (ContextCompat.checkSelfPermission(getActivity(), CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{CAMERA}, REQUEST_CAMERA);
    }

    public void onRequestPermissionsResult(int requestCode, String permission[], int grantResults[]) {
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted) {
                        Toast.makeText(getActivity(), "Permission Granted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_LONG).show();
                        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(CAMERA)) {
                                displayAlertMessage("You need to allow access for both permissions", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {
                                        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                                            requestPermissions(new String[]{CAMERA}, REQUEST_CAMERA);
                                        }
                                    }
                                });
                                return;
                            }
                        }
                    }
                }
                break;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            if (CheckPermmision()) {
                if (scannerView == null) {
                    scannerView = new ZXingScannerView(getActivity());

                }
                scannerView.setResultHandler(this);
                scannerView.startCamera();
            } else {
                requestPermission();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        scannerView.stopCamera();
    }

    public void displayAlertMessage(String message, DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(getActivity()).setMessage(message).setPositiveButton("OK", listener).setNegativeButton("Cancel", null).create().show();
    }

    @Override
    public void handleResult(Result result) {

        final String scanResult = result.getText();
        try {
            if(HttpMethods.isOnline()){
                getProducts(scanResult);
            }else {
                Toast.makeText(getContext(), "Check your internet connection", Toast.LENGTH_SHORT).show();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Scan Barcode");
        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(from.equals("")){
                    Fragment fragment = new ShopFragment();
                    Bundle args = new Bundle();
                    args.putString("productName", "Cancel");
                    fragment.setArguments(args);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }else {
                    Fragment fragment = new firstScanFragment();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }

            }
        });
        builder.setNeutralButton(" Add item", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(from.equals("") || !(prodcutName.equals("Product not found"))){
                    System.out.println("Im going to shop fragment");
                    Fragment fragment = new ShopFragment();
                    Bundle args = new Bundle();
                    args.putString("productName", prodcutName);
                    args.putString("productID", ProductID);
                    args.putString("quantityPerUnit", quantityPerUnit);
                    args.putString("Unitprice", String.valueOf(Unitprice));
                    fragment.setArguments(args);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }else {
                    Fragment fragment = new firstScanFragment();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }


            }
        });
        builder.setMessage(""+prodcutName+"\nQty Per Units "+quantityPerUnit+"\nR "+Unitprice+"\n");

        AlertDialog alert = builder.create();
        alert.show();

        Button button = alert.getButton(DialogInterface.BUTTON_NEUTRAL);
        Button button2 = alert.getButton(DialogInterface.BUTTON_POSITIVE);

        if(button != null || button2 != null) {
            button.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_rounded));
            button2.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_rounded));
        }

    }


    String prodcutName = "Product not found",quantityPerUnit = "Please try again or enter barcode manual";
    String Unitprice = "0.00";
    String ProductID ="";

    void getProducts(String barcode) throws IOException, JSONException {
        String body = "{\"id\":1}";
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String response = HttpMethods.Get("http://mkhululi.net/picknscan/api/products/barcode/"+barcode,pref.getString("username",null),pref.getString("password",null),body);
        JSONObject jsonResponse = new JSONObject(response);
        if(jsonResponse.getString("status_code").equals(""+200) ){
            prodcutName = jsonResponse.getJSONObject("data").getString("ProductName");
            ProductID = jsonResponse.getJSONObject("data").getString("ProductID");
            quantityPerUnit = jsonResponse.getJSONObject("data").getString("QuantityPerUnit");
            Unitprice = (jsonResponse.getJSONObject("data").getString("UnitPrice"));
        }
        else
        {
            prodcutName = "Product not found";
            quantityPerUnit = "Please try again or enter barcode manual";
            Unitprice = "0.00";
        }
    }
}
