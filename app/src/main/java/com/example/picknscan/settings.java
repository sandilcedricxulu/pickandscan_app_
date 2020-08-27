package com.example.picknscan;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class settings extends Fragment {

    TextView aboutUS,askedQuestions,help,change_pin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        aboutUS = view.findViewById(R.id.about_us);
        askedQuestions = view.findViewById(R.id.asked_questions);
        help = view.findViewById(R.id.help);
        change_pin = view.findViewById(R.id.change_pin);

        change_pin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("changingPin", true);
                editor.commit();

                Intent intent = new Intent(getActivity().getApplicationContext(),PIN.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        aboutUS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                builder.setTitle("About PicknScan");
//                builder.setIcon(R.drawable.aboutus);
//                builder.setMessage("You wont have to Stand queues from the shops when you want to pay" +
//                        "You will simply pay online")
//                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//
//                            }
//                        });
//                AlertDialog alert = builder.create();
//                alert.show();
//
//                Button button = alert.getButton(DialogInterface.BUTTON_POSITIVE);
//
//                if(button != null ) {
//                    button.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_rounded));
//                }
                Fragment fragment = new about_us_webJava();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        askedQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                builder.setTitle("Frequently Asked Questions");
//                builder.setIcon(R.drawable.cedfaq);
//                builder.setMessage("You wont have to Stand queues from the shops when you want to pay" +
//                        "You will simply pay online")
//                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//
//                            }
//                        });
//                AlertDialog alert = builder.create();
//                alert.show();
//
//                Button button = alert.getButton(DialogInterface.BUTTON_POSITIVE);
//
//                if(button != null ) {
//                    button.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_rounded));
//                }
                Fragment fragment = new faqJava();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                builder.setTitle("Any Help regarding PicknScan");
//                builder.setIcon(R.drawable.help);
//                builder.setMessage("You wont have to Stand queues from the shops when you want to pay" +
//                        "You will simply pay online")
//                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//
//                            }
//                        });
//                AlertDialog alert = builder.create();
//                alert.show();
//
//                Button button = alert.getButton(DialogInterface.BUTTON_POSITIVE);
//
//                if(button != null ) {
//                    button.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_rounded));
//                }
                Fragment fragment = new helpJava();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return  view;
    }

    public void pin_verification_email(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Verify Your One Time PIN sent to your email");
        final EditText input = new EditText(getActivity());
        input.setBackgroundDrawable(getResources().getDrawable(R.drawable.textfield_rounded));
        builder.setMessage("")
                .setPositiveButton("Verify OTP", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //confirming password
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
        Button button = alert.getButton(DialogInterface.BUTTON_POSITIVE);

        if(button != null ) {
            button.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_rounded));
        }
    }

    public void sendEmail(){

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, "ohmmahoba@gmail.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Verification Code");
        intent.putExtra(Intent.EXTRA_TEXT, "10111");

        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Choose an email client"));
    }


}
