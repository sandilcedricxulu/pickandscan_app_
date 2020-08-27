package com.example.picknscan;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class firstScanFragment extends Fragment {

    TextView enter_manual_barcode1,scan_with_camera_text;
    ImageButton manual_barcode_imageView;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    public firstScanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View veiw = inflater.inflate(R.layout.fragment_first_scan, container, false);
        Button switchButton = (Button) veiw.findViewById(R.id.FirsToScan);
//        enter_manual_barcode1 = veiw.findViewById(R.id.enter_manual_barcode1);
        manual_barcode_imageView = veiw.findViewById(R.id.manual_barcode_imageView);
//        scan_with_camera_text = veiw.findViewById(R.id.scan_with_camera_text);

        pref = getActivity().getApplicationContext().getSharedPreferences("MyPref",0);
        editor = pref.edit();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
//        scan_with_camera_text.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Fragment fragment = new ScanFragment();
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.fragment_container, fragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
//            }
//        });
//        enter_manual_barcode1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                LayoutInflater inflater = requireActivity().getLayoutInflater();
//                final View mView = inflater.inflate(R.layout.manual_barcode_alert_dialog,container, false);
//
//                builder.setView(mView)
//                        .setPositiveButton("Add items", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int id) {
//                                final EditText manualBarcode = mView.findViewById(R.id.manual_barcode_value);
//                                Toast.makeText(getActivity(), "Entered barcode is :"+manualBarcode.getText().toString(), Toast.LENGTH_SHORT).show();
//                            }
//                        })
//                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.dismiss();
//                                dialog.cancel();
//                            }
//                        });
//                AlertDialog alert = builder.create();
//                alert.show();
//
//                Button button = alert.getButton(DialogInterface.BUTTON_POSITIVE);
//
//                if(button != null ) {
//                    button.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_rounded));
//                    button.setTextSize(15);
//                    button.setWidth(600);
//                    button.setPadding(110,0,110,0);
//                }
//            }
//        });
        //or an image click
        manual_barcode_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = requireActivity().getLayoutInflater();
                final View mView = inflater.inflate(R.layout.manual_barcode_alert_dialog,container, false);

                builder.setView(mView)
                        .setPositiveButton("Add items", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                final EditText manualBarcode = mView.findViewById(R.id.manual_barcode_value);
                                try {
                                    if(HttpMethods.isOnline())
                                    {
                                        getProducts(manualBarcode.getText().toString());
                                        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
                                        builder.setTitle("Scanned Item");
                                        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Fragment fragment = new firstScanFragment();
                                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                fragmentTransaction.replace(R.id.fragment_container, fragment);
                                                fragmentTransaction.addToBackStack(null);
                                                fragmentTransaction.commit();
                                            }
                                        });
                                        builder.setNeutralButton("Add", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if(!prodcutName.equals("Product not found")) {
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
                                        builder.setMessage(""+prodcutName+"\n"+quantityPerUnit+"\nR "+Unitprice+"\n");

                                        android.support.v7.app.AlertDialog alert = builder.create();
                                        alert.show();

                                        Button button = alert.getButton(DialogInterface.BUTTON_NEUTRAL);
                                        Button button2 = alert.getButton(DialogInterface.BUTTON_POSITIVE);

                                        if(button != null || button2 != null) {
                                            button.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_rounded));
                                            button2.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_rounded));
                                        }
                                    }else{
                                        Toast.makeText(getContext(), "Check your internet connection", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

                Button button = alert.getButton(DialogInterface.BUTTON_POSITIVE);

                if(button != null ) {
                    button.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_rounded));
                    button.setTextSize(15);
                    button.setWidth(600);
                    button.setPadding(110,0,110,0);
                }
            }
        });

        switchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Im going to Scan Fragment");
                Fragment fragment = new ScanFragment();
                Bundle args = new Bundle();
                args.putString("from", "fistScanFragment");
                fragment.setArguments(args);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return veiw;
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
