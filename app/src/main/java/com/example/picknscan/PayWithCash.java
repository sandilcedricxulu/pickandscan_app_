package com.example.picknscan;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PayWithCash extends AppCompatActivity
{
    public static  SharedPreferences sharedPreferences;

    static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    static DateFormat dateFormat1 = new SimpleDateFormat(" HH:mm:ss");
    static Date date = new Date();
    static String currentDate = dateFormat.format(date);
    static  String currentTime = dateFormat1.format(date);


    public static int InsertInvoice(ArrayList<Products> products ,Context context) throws IOException, JSONException {
        sharedPreferences = context.getSharedPreferences("MyPref", 0);
        int customerID = sharedPreferences.getInt("customerID",-1);
        String username = sharedPreferences.getString("username",null);
        String password = sharedPreferences.getString("password",null);

        String response = HttpMethods.Post("http://mkhululi.net/picknscan/api/temp_invoices/",username,password,"{\"InvoiceDate\":\""+currentDate+"\",\"InvoiceTime\":\""+currentTime+"\",\"CustomerID\":"+customerID+"}");
        System.out.println("----------------------------------------------------------------------------"+response);
        System.out.println(response);
        JSONObject jsonResponse = new JSONObject(response);
        int lastInvoice = 0;
        if(jsonResponse.getString("status_code").equals(""+200) ){
            System.out.println("Im calling insert now ");
            lastInvoice = insertInvoiceItems(products);
        }
        return lastInvoice;
    }

    public static int insertInvoiceItems(ArrayList<Products> products) throws IOException {
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
            response = HttpMethods.Post("http://mkhululi.net/picknscan/api/temp_invoice_items/",username,password,"{\"InvoiceID\":\""+lastInvoice+"\",\"ProductID\":\""+products.get(i).ProductID()+"\",\"Quantity\":"+products.get(i).Quantity()+",\"Price\":\""+products.get(i).UnitPrice()+"\"}");
            System.out.println("-------------------------------------------------------------");
            System.out.println("Response is "+response);
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
        String response = HttpMethods.Get("http://mkhululi.net/picknscan/api/temp_invoices/",username,password,"{\"CustomerID\":"+customerID+"}");
        System.out.println("Hey the response of invoices is "+response);
        TempInvoiceItemsResponse tempInvoiceItemsResponse = gson.fromJson(response,TempInvoiceItemsResponse.class);

        for(int i = 0;i < tempInvoiceItemsResponse.data.size();i++)
        {
            System.out.println("I found invoice now about to check your last invoice");
            if(tempInvoiceItemsResponse.data.get(i).CustomerID() == customerID)
            {
                System.out.println("Same customer id");
                lastInvoiceId = tempInvoiceItemsResponse.data.get(i).InvoiceID();
                i = tempInvoiceItemsResponse.data.size();
            }
        }
        return lastInvoiceId;
    }
}
