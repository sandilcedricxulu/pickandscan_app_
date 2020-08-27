package com.example.picknscan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.picknscan.FirstScreen;
import com.example.picknscan.R;
import com.hanks.passcodeview.PasscodeView;

public class CREATE_PIN extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passcode);
        final PasscodeView passcodeView = findViewById(R.id.passcodeView);
        passcodeView.setListener(new PasscodeView.PasscodeViewListener() {
            @Override
            public void onFail() {
                //The passcodes do not match
            }

            @Override
            public void onSuccess(String number) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                String passcode = passcodeView.getLocalPasscode();
                editor.putString("pin_hash", passcode);
                editor.commit();
                Intent intent = new Intent(CREATE_PIN.this, FirstScreen.class);
                startActivity(intent);
                finish();
            }
        });
    }




}
