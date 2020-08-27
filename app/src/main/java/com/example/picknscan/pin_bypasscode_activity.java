package com.example.picknscan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.hanks.passcodeview.PasscodeView;


public class pin_bypasscode_activity extends  AppCompatActivity {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_bypasscode_activity);

        final PasscodeView passcodeView = (PasscodeView) findViewById(R.id.passcodeView);

        passcodeView.setListener(new PasscodeView.PasscodeViewListener() {
            @Override
            public void onFail() {
                Toast.makeText(pin_bypasscode_activity.this, "PIN not match", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(String number) {
                String pin = passcodeView.getLocalPasscode();
                Toast.makeText(pin_bypasscode_activity.this, "pp :"+pin, Toast.LENGTH_SHORT).show();

                pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                editor = pref.edit();
                editor.putString("Pin", pin);
                editor.commit();


                Intent intent = new Intent(pin_bypasscode_activity.this,FirstScreen.class);
                startActivity(intent);
            }
        });

    }
}
