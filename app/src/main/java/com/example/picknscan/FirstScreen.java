package com.example.picknscan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class FirstScreen extends AppCompatActivity {
    public static int quanity = 1;

    SharedPreferences pref ;
    SharedPreferences.Editor editor ;

//    public  int getQuanity() {
//        return quanity;
//    }
//
//    public  void setQuanity(int qty) {
//        this.quanity = qty;
//    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_first_screen);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();
        boolean changingPin = pref.getBoolean("changingPin",false);
        if(changingPin)
        {
            editor.putBoolean("changingPin",false);
            Intent createPin = new Intent(FirstScreen.this,CREATE_PIN.class);
            startActivity(createPin);
            editor.putBoolean("gotosettings",true);
            finish();
            editor.commit();
            return;
        }
        boolean gotosettings = pref.getBoolean("gotosettings",false);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        if(gotosettings)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new settings()).commit();
            bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
            editor.putBoolean("gotosettings",false);
            editor.commit();
            return;
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();
    }



    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment  = null;

            switch (menuItem.getItemId()){
                case R.id.nav_home:
                    selectedFragment = new HomeFragment();
                    break;

                case R.id.nav_shop:
                    selectedFragment = new firstScanFragment();
                    break;

                case R.id.nav_settings:
                    selectedFragment = new settings();
                    break;

                case R.id.nav_account:
                    selectedFragment = new AcountFragment();
                    break;

                case R.id.toListEditor:
                    selectedFragment = new listEditor();
                    break;

            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
            return true;
        }
    };
}
