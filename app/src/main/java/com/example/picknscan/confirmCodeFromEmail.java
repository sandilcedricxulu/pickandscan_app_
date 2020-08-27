package com.example.picknscan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Interceptor;

public class confirmCodeFromEmail extends AppCompatActivity {
    public confirmCodeFromEmail() {
        // Required empty public constructor
    }

    String firstName,lastName,phone,email,password,verificationCode;
    TextView get_code_from_email;
    Button verifyCodeFromEmail;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmcodefromemail);

        firstName = getIntent().getStringExtra("FirstName");
        lastName = getIntent().getStringExtra("LastName");
        phone = getIntent().getStringExtra("Phone");
        email = getIntent().getStringExtra("Email");
        password = getIntent().getStringExtra("password");
        verificationCode = getIntent().getStringExtra("verificationCode");

        get_code_from_email = findViewById(R.id.get_code_from_email);
        verifyCodeFromEmail = findViewById(R.id.verifyCodeFromEmail);

        verifyCodeFromEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    try {
                        if(get_code_from_email.getText().toString().equals(verificationCode)){
                            System.out.println("Hey 1");
                            registerNewUser();
                            System.out.println("Hey 2");
                            Toast.makeText(confirmCodeFromEmail.this, "Pin verified", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(confirmCodeFromEmail.this,PIN.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(confirmCodeFromEmail.this, "Pin incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (JSONException e) {
                        Toast.makeText(confirmCodeFromEmail.this, "JSONException**"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
            }
        });

    }


    // function to be called
    public void registerNewUser() throws JSONException {
        String body = "{\"Firstname\": \""+firstName+"\",\"Lastname\": \""+lastName+"\",\"Cellphone\": \""+phone+"\",\"Email\": \""+email+"\",\"Username\":\""+email+"\", \"Password\":\""+password+"\"}";
        Gson gson = new Gson();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            String response = HttpMethods.Post("http://mkhululi.net/picknscan/api/user/register/","","",body+"");
            System.out.println("This is the response "+response);
            JSONObject response_json = new JSONObject(response);
            System.out.println("Hey");
            if(Integer.parseInt( response_json.getString("status_code")) == 200) {
                System.out.println("Hey it was success on me ");
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                editor.remove("customerID");
                System.out.println("Pass remove");
                editor.putString("username",email);
                editor.putString("password", password);
                editor.putString("FirstName",firstName);
                editor.putString("LastName", lastName);
                editor.putString("Email", email);
                editor.putString("Cellphone", phone);
                editor.commit();
                loginTogetCustomerID();
            }
            else{
                Toast.makeText(this, "Fail to Register make sure you put correct information ", Toast.LENGTH_SHORT).show();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loginTogetCustomerID() throws JSONException, IOException {
        String response = HttpMethods.Get("http://mkhululi.net/picknscan/api/user/login/", email, password, "{\"Email\":\"mxo\"}");
        System.out.println("Response   " + response+" from login");
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        JSONObject userResponse = new JSONObject(response);
        if (userResponse.getString("status_code").equals("200")) {
            int customerID = Integer.parseInt(userResponse.getJSONObject("data").getString("UserID"));
            editor.putInt("customerID", customerID);
            editor.commit();
            return;
        }
    }

}
