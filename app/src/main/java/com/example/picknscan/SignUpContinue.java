package com.example.picknscan;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.util.*;

public class SignUpContinue extends AppCompatActivity {
    private EditText email,password,verify_password;
    String firstName,lastName,phone;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    String verificationCode;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_continue);

        pref = this.getApplicationContext().getSharedPreferences("MyPref", 0);
        editor = pref.edit();

        firstName = getIntent().getStringExtra("FirstName");
        lastName = getIntent().getStringExtra("LastName");
        phone = getIntent().getStringExtra("Phone");

        email = findViewById(R.id.email);
        password = findViewById(R.id.Password_continue);
        verify_password = findViewById(R.id.VerifyPassword);

        progressDialog = new ProgressDialog(this);

    }

    public void goToPrev(View view){
        Intent intent = new Intent(this,Signup.class);
        startActivity(intent);
        finish();
    }

    //register
    public void insertNewUser(View view) {
        Validations validation = new Validations();
        if(!(TextUtils.isEmpty(email.getText().toString()))){
            if (validation.isValidEmailAddress(email.getText().toString())) {
                if (password.getText().toString().length() >= 8) {
                    if (password.getText().toString().equals(verify_password.getText().toString())) {
                        progressDialog.setMessage("Registering user.....");
                        progressDialog.show();
                        Runnable progressRunnable = new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.cancel();
                                SendEmail sendEmail = new SendEmail();
                                Random randomGenerator = new Random();
                                verificationCode = ""+(randomGenerator.nextInt(900000) + 100000);
                                sendEmail.sendMail(""+email.getText().toString(),""+verificationCode,""+firstName);
                                Intent intent = new Intent(getApplicationContext(),confirmCodeFromEmail.class);
                                intent.putExtra("username",email.getText().toString());
                                intent.putExtra("password",password.getText().toString());
                                intent.putExtra("FirstName",getIntent().getStringExtra("FirstName"));
                                intent.putExtra("LastName",getIntent().getStringExtra("LastName"));
                                intent.putExtra("Phone",getIntent().getStringExtra("Phone"));
                                intent.putExtra("Email",email.getText().toString());
                                intent.putExtra("verificationCode",verificationCode);
                                startActivity(intent);
                                finish();
                            }
                        };
                        Handler pdCanceller = new Handler();
                        pdCanceller.postDelayed(progressRunnable,3000);
                    } else {
                        Toast.makeText(this, "Password don't match", Toast.LENGTH_SHORT).show();
                        password.setText("");
                        verify_password.setText("");
                    }
                } else {
                    Toast.makeText(this, "Password is too short", Toast.LENGTH_SHORT).show();
                    password.setError("Minimum password length is 8");
                }
            } else {
                email.setError("Invalid email address");
            }
        }else{
            email.setError( "Email is required" );
        }

    }


    public boolean validateEmail(String email){
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (email.matches(emailPattern)) {
            return true;
        }
        else {
            return false;
        }
    }

    boolean sendEmailVerification(){
        return true;
    }
}