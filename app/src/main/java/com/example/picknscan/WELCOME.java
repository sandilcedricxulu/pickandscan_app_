package com.example.picknscan;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.picknscan.FirstScreen;
import com.example.picknscan.Login;
import com.example.picknscan.R;
import com.example.picknscan.Signup;
import com.google.gson.Gson;

public class WELCOME extends AppCompatActivity {
    private Button signup_button;
    private Button login_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        signup_button = findViewById(R.id.signup_button);
        login_button = findViewById(R.id.login_button);

        //Event Listeners
        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(WELCOME.this,Signup.class);
                startActivity(intent);
                finish();
            }
        });
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
             public void onClick(View v) {
                Intent intent  = new Intent(WELCOME.this,Login.class);
                startActivity(intent);
                finish();
            }
        });
    }
    @Override
    public void onBackPressed(){
        System.out.println("Finished executed");
        finish();
    }


}
