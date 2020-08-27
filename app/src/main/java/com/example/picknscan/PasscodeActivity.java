package com.example.picknscan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.picknscan.R;
import com.hanks.passcodeview.PasscodeView;

/**
 * Created by hanks on 2017/4/17.
 */

public class PasscodeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passcode);
        PasscodeView passcodeView = (PasscodeView) findViewById(R.id.passcodeView);
        passcodeView.setListener(new PasscodeView.PasscodeViewListener() {
            @Override
            public void onFail() {
                Toast.makeText(getApplication(),"Wrong Pin!!",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(String number) {
                Toast.makeText(getApplication(),"finish",Toast.LENGTH_SHORT).show();
                //Move to the next activity

            }
        });
    }

}