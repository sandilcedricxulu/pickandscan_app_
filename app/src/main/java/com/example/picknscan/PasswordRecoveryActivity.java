package com.example.picknscan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class PasswordRecoveryActivity extends AppCompatActivity {

    private Button resetPassword;
    private EditText recoverEmail;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_recovery);
        resetPassword = findViewById(R.id.resetPassword);
        recoverEmail = findViewById(R.id.recovery_email);
        progressDialog = new ProgressDialog(this);

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(forgotPassword()){

                        progressDialog.setMessage("Sending reset link to your email....");
                        progressDialog.show();Runnable progressRunnable = new Runnable() {
                            @Override
                            public void run() {
                        progressDialog.cancel();
                        Toast.makeText(getApplicationContext(), "Check your email to reset password", Toast.LENGTH_SHORT).show();
                        Intent goToLogin = new Intent(PasswordRecoveryActivity.this,Login.class);
                        startActivity(goToLogin);
                            }
                        };
                        Handler pdCanceller = new Handler();
                        pdCanceller.postDelayed(progressRunnable,3000);
                    }else {
                        Toast.makeText(getApplicationContext(), "Could not send email", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


    }
    public boolean forgotPassword() throws IOException, JSONException {
        Gson gson = new Gson();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String response = HttpMethods.Post("http://mkhululi.net/picknscan/api/user/forgot-password/","","","{\"Email\":\""+recoverEmail.getText().toString()+"\"}");

        JSONObject userResponse = new JSONObject(response);

        if(userResponse.getString("status_code").equals("200"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
