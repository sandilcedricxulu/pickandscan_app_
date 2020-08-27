package com.example.picknscan;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;

public class Login extends AppCompatActivity {
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    private EditText Userpassword,username_email;
    private String jsonObj;
    private Button loginButton;
    private TextView logintext;
    int customerID;
    TextView signupTextView,forgotPasswordTextView;
    ProgressBar loader;
    String FirstName,LastName,Email,Cellphone;
    private ProgressDialog progressDialog;

    public Login(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        logintext = (TextView)findViewById(R.id.loginTextView);
        Userpassword = (EditText)findViewById(R.id.password);
        loginButton = (Button)findViewById(R.id.loginButton);
        forgotPasswordTextView = findViewById(R.id.Forgot_Password);
        username_email = findViewById(R.id.username_email);
        progressDialog = new ProgressDialog(this);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(HttpMethods.isOnline()) {
                    if(VerifyUser()){
                        progressDialog.setMessage("Logging in.....");
                        progressDialog.show();
                        Runnable progressRunnable = new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.cancel();
                                storeUserInformationLocal();
                                Intent intent  = new Intent(Login.this,PIN.class);
                                startActivity(intent);
                                finish();
                                Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            }
                        };
                        Handler pdCanceller = new Handler();
                        pdCanceller.postDelayed(progressRunnable,3000);
                    }
                    else {
                        Toast.makeText(Login.this, "Username or Password incorrect", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(Login.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
                }


            }
        });

        forgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toForgotPassword = new Intent(Login.this,PasswordRecoveryActivity.class);
                startActivity(toForgotPassword);
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent intent  = new Intent(Login.this,WELCOME.class);
        startActivity(intent);
        finish();
    }

    public boolean VerifyUser() {
        Gson gson = new Gson();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
            try {
                Validations validation = new Validations();
                if(!(TextUtils.isEmpty(username_email.getText().toString())))
                {
                    if(!TextUtils.isEmpty(Userpassword.getText())) {
                        if (validation.isValidEmailAddress(username_email.getText().toString())) {
                            String response = HttpMethods.Get("http://mkhululi.net/picknscan/api/user/login/", username_email.getText().toString(), Userpassword.getText().toString(), "{\"Email\":\"mxo\"}");
                            System.out.println("Response   "+response);
                            JSONObject userResponse = new JSONObject(response);
                            if(userResponse.getString("status_code").equals("200")){
                                System.out.println("Hey the response was success");
                                Email = userResponse.getJSONObject("data").getString("Email").trim();
                                System.out.println("Email is "+Email);
                                FirstName = userResponse.getJSONObject("data").getString("Firstname");
                                System.out.println("Firstname is "+FirstName);
                                customerID = Integer.parseInt(userResponse.getJSONObject("data").getString("UserID"));
                                System.out.println("CustomerID is "+customerID);
                                LastName = userResponse.getJSONObject("data").getString("Lastname");
                                System.out.println("Lastname is "+LastName);
                                Cellphone = userResponse.getJSONObject("data").getString("Cellphone");
                                System.out.println("Cellphone is "+Cellphone);
                                System.out.println("Email from texbox  is "+username_email.getText().toString());
                                String entered_email = username_email.getText().toString().trim();
                                if (Email.equals(entered_email)) {
                                    System.out.println("Hey it true you are a valid user");
                                    return true;
                                } else {
                                    Toast.makeText(this, "Incorrect Username or Password", Toast.LENGTH_SHORT).show();
                                    return false;
                                }
                            }else{
                                Toast.makeText(this, "Make sure you registered", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            username_email.setError("Invalid Email");
                        }
                    }else {
                        Userpassword.setError( "password  is required" );
                    }
                }
                else
                {
                    username_email.setError( "Username is required" );
                }


            } catch (IOException e) {
            } catch (JSONException e) {
            }
            return false;

    }

    public void storeUserInformationLocal(){
        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();
        editor.putString("username", username_email.getText().toString());
        editor.putString("password", Userpassword.getText().toString());
        editor.putInt("customerID",customerID);
        editor.putString("FirstName",FirstName);
        editor.putString("LastName", LastName);
        editor.putString("Email", Email);
        editor.putString("Cellphone", Cellphone);
        editor.commit();
        return;
    }

}