package com.example.picknscan;

import android.accounts.Account;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.hanks.passcodeview.PasscodeView;

public class PIN extends AppCompatActivity {
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        init();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passcode2);

        final PasscodeView passcodeView = (PasscodeView) findViewById(R.id.passcodeView);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        String pin = pref.getString("pin_hash", "");
        passcodeView.setLocalPasscode(pin);
        passcodeView.setFirstInputTip("Enter your PIN");
        passcodeView.setPasscodeLength(4);
        passcodeView.setListener(new PasscodeView.PasscodeViewListener() {

            @Override
            public void onFail() {
                Toast.makeText(getApplication(),"Wrong PIN!",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(String number) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                String passcode = passcodeView.getLocalPasscode();
                editor.putString("pin_hash", passcode);
                editor.commit();
                Intent intent = new Intent(PIN.this, FirstScreen.class);
                startActivity(intent);
                finish();
            }
        });

    }

    void init() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();

        String username = pref.getString("username", null);
        String password = pref.getString("password", null);
        String pin = pref.getString("pin_hash", null);

        if(username == null || password == null){
            System.out.println("------------------------------------------------------------------------------------------------------");
            System.out.println("am here");
            System.out.println("------------------------------------------------------------------------------------------------------");
            //GOTO WELCOME PAGE
            Intent intent = new Intent(PIN.this, WELCOME.class);
            startActivity(intent);
            finish();
            return;
        }

        if(pin == null){
            //GOTO CREATE PIN PAGE
            Intent intent = new Intent(PIN.this, CREATE_PIN.class);
            startActivity(intent);
            finish();
            return;
        }

    }


}
