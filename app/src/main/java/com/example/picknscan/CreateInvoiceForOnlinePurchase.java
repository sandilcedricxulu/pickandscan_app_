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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CreateInvoiceForOnlinePurchase {
    public static SharedPreferences sharedPreferences,sharedPreferences2;
    static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    static DateFormat dateFormat1 = new SimpleDateFormat(" HH:mm:ss");
    static Date date = new Date();
    static String currentDate = dateFormat.format(date);
    static  String currentTime = dateFormat1.format(date);
    static  String invoice_date = "",invoice_time ="";

    public static int createInvoice(Context context) throws IOException, JSONException {
        sharedPreferences = context.getSharedPreferences("MyPref", 0);
        sharedPreferences2 = context.getSharedPreferences("save_my_products", 0);
        int customerID = sharedPreferences.getInt("customerID",-1);
        String username = sharedPreferences.getString("username",null);
        String password = sharedPreferences.getString("password",null);

        String response = HttpMethods.Post("http://mkhululi.net/picknscan/api/invoices/",username,password,"{\"InvoiceDate\":\""+currentDate+"\",\"InvoiceTime\":\""+currentTime+"\",\"CustomerID\":"+customerID+",\"EmployeeID\":"+0+"}");
        System.out.println("----------------------------------------------------------------------------"+response);
        System.out.println(response);
        JSONObject jsonResponse = new JSONObject(response);
        int lastInvoice = 0;
        if(jsonResponse.getString("status_code").equals(""+200) ){
            System.out.println("Im calling insert now ");
            lastInvoice = insertInvoiceItems();
        }
        return lastInvoice;
    }
    public static int getLastInvoice() throws IOException {

        System.out.println("Get Last invoice Executed");
        Gson gson = new Gson();
        int lastInvoiceId = 0;
        int customerID = sharedPreferences.getInt("customerID",-1);
        String username = sharedPreferences.getString("username",null);
        String password = sharedPreferences.getString("password",null);
        String response = HttpMethods.Get("http://mkhululi.net/picknscan/api/invoices/",username,password,"{\"CustomerID\":"+customerID+"}");

        InvoiceResponse invoiceResponse = gson.fromJson(response,InvoiceResponse.class);

        for(int i = 0;i < invoiceResponse.getData().size();i++)
        {
            if(invoiceResponse.getData().get(i).getCustomerID() == customerID)
            {
                System.out.println("Same customer id");
                lastInvoiceId = Integer.parseInt(invoiceResponse.getData().get(i).getInvoiceID());
                invoice_date = invoiceResponse.getData().get(i).getInvoiceDate();
                invoice_time = invoiceResponse.getData().get(i).getInvoiceTime();
                i = invoiceResponse.getData().size();
            }
        }
        return lastInvoiceId;
    }

    public static String getInvoiceDate() {
        return invoice_date;
    }
    public static String getInvoiceTime() {
        return invoice_time;
    }


    public static int insertInvoiceItems() throws IOException {
        loadData();
        System.out.println("InsertInvoiceItems executed");
        int lastInvoice = getLastInvoice();
        System.out.println("This is the last invoice "+lastInvoice);
        String response = "";
        String username = sharedPreferences.getString("username",null);
        String password = sharedPreferences.getString("password",null);
        System.out.println("The product size is "+products.size());
        for(int i = 0;i < products.size();i++)
        {
            System.out.println("Hey im in the for loop");
            response = HttpMethods.Post("http://mkhululi.net/picknscan/api/invoice_items/",username,password,"{\"InvoiceID\":\""+lastInvoice+"\",\"ProductID\":\""+products.get(i).ProductID()+"\",\"Quantity\":"+products.get(i).Quantity()+",\"Price\":\""+products.get(i).UnitPrice()+"\"}");
            System.out.println("-------------------------------------------------------------");
            System.out.println("Response is "+response);
        }
        return lastInvoice;
    }

    public static ArrayList<Products> products = new ArrayList<>();

    public static void loadData() {
        System.out.println("Hey im loading the data");
        Gson gson = new Gson();
        String json = sharedPreferences2.getString("myProductList", null);
        Type type = new TypeToken<ArrayList<Products>>() {}.getType();
        products = gson.fromJson(json, type);
        if (products == null) {
            products = new ArrayList<>();
            System.out.println("Yes products is null");
        }
        System.out.println("in load the products size is "+products.size());
        return;
    }
}
