package com.example.picknscan;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FindReceipt {

    public static SharedPreferences sharedPreferences;
    boolean oneFound = false;
    static  ArrayList<Products> all_receipts = new ArrayList<>();

    public static List<Invoice> getAllInvoices(Context context) throws IOException, JSONException {
        Gson gson = new Gson();
        sharedPreferences = context.getSharedPreferences("MyPref", 0);

        String username = sharedPreferences.getString("username",null);
        int customerID = sharedPreferences.getInt("customerID",0);
        String password = sharedPreferences.getString("password",null);

        String response = HttpMethods.Get("http://mkhululi.net/picknscan/api/invoices/",""+username,""+password,"{\"isGET\":1}");
        System.out.println("----------------------------------------------------------------------------"+response);
        System.out.println(response);
        JSONObject jsonResponse = new JSONObject(response);
        InvoiceResponse invoiceResponse = gson.fromJson(response,InvoiceResponse.class);

        if(jsonResponse.getString("status_code").equals(""+200) ){
            List<Invoice> customerInvoice = new ArrayList<Invoice>();
            for(int i = 0;i < invoiceResponse.getData().size();i++)
            {
                System.out.println("Hey im in the for loop");
                if(invoiceResponse.getData().get(i).getCustomerID() == customerID){
                    System.out.println("Customer id from api"+invoiceResponse.getData().get(i).CustomerID);
                    System.out.println("Customer id from application"+customerID);
                    System.out.println("Receipt found");
                    customerInvoice.add(invoiceResponse.getData().get(i));
                }
            }
            return customerInvoice;
        }
        else {
            return null;
        }
    }

    private void saveData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("myReceipts");
        editor.commit();

        Gson gson = new Gson();
        String json = gson.toJson(all_receipts);
        editor.putString("myReceipts", json);
        editor.commit();

    }

    private void loadData() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString("myReceipts", null);
        Type type = new TypeToken<ArrayList<Products>>() {}.getType();
        all_receipts = gson.fromJson(json, type);
        if (all_receipts == null) {
            all_receipts = new ArrayList<>();
        }
        return;
    }


    public static List<Products> findProductID(Context context,int invoiceID) throws IOException, JSONException {
        List<Products> products = new ArrayList<>();
        Gson gson = new Gson();
        sharedPreferences = context.getSharedPreferences("MyPref", 0);

        String username = sharedPreferences.getString("username",null);
        String password = sharedPreferences.getString("password",null);

        System.out.println("Usename "+username+"  password "+password);

        String response = HttpMethods.Get("http://mkhululi.net/picknscan/api/invoice_items/"+invoiceID,""+username,""+password,"{\"isGET\":1}");
        System.out.println("-----------------------------------------");
        System.out.println("Response"+response);

        JSONObject jsonResponse = new JSONObject(response);

        InvoiceItemsResponse invoiceItemsResponse = gson.fromJson(response,InvoiceItemsResponse.class);

        if(jsonResponse.getString("status_code").equals(""+200) ){
            System.out.println("Hey");
            for(int i = 0;i < invoiceItemsResponse.data.size();i++)
            {
                System.out.println("***************************"+invoiceItemsResponse.data.get(i).ProductID());
                response = HttpMethods.Get("http://mkhululi.net/picknscan/api/products/"+invoiceItemsResponse.data.get(i).ProductID(),""+username,""+password,"{\"isGET\":1}");
                System.out.println("---------------------------");
                System.out.println(""+response);
                jsonResponse = new JSONObject(response);

                if(jsonResponse.getString("status_code").equals(""+200) ) {
                    ProductResponse productResponse = gson.fromJson(response, ProductResponse.class);
                    products.add(productResponse.data);
                    if (products.get(i).ProductID() == invoiceItemsResponse.data.get(i).ProductID()) {
                        double total = invoiceItemsResponse.data.get(i).Price() * invoiceItemsResponse.data.get(i).Quantity();
                        products.set(i, new Products(products.get(i).ProductID(), products.get(i).ProductName(), invoiceItemsResponse.data.get(i).Price(), products.get(i).QuantityPerUnit(), invoiceItemsResponse.data.get(i).Quantity(),total ));
                    }
                }
            }
        }
        return products;
    }

}
