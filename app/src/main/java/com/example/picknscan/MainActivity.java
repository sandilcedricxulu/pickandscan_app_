package com.example.picknscan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class MainActivity extends AppCompatActivity {

    ViewFlipper v_flipper;
    //ImageButton goToLogin = (ImageButton)findViewById(R.id.btnReg);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int images [] = {R.drawable.cart,R.drawable.thumb,R.drawable.verified};

        v_flipper = findViewById(R.id.v_flipper);

        for(int img: images){
            flipperImage(img);
        }

    }

    public void flipperImage(int image){
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image);

        v_flipper.addView(imageView);
        v_flipper.setFlipInterval(4000);
        v_flipper.setAutoStart(true);

        v_flipper.setInAnimation(this, android.R.anim.slide_in_left);
        v_flipper.setOutAnimation(this, android.R.anim.slide_out_right);


    }

    public void goToLoginClick(View view){
        Intent intent = new Intent(MainActivity.this,Login.class);
        startActivity(intent);
    }

    public void goToSignupClick(View view){
        Intent intent = new Intent(MainActivity.this,Signup.class);
        startActivity(intent);
    }
}
