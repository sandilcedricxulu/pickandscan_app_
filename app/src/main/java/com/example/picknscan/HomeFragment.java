package com.example.picknscan;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    TextView curr_date;
    TextView curr_time;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        DateFormat dateFormat1 = new SimpleDateFormat(" HH:mm:ss");
        Date date = new Date();
        String currentDate = dateFormat.format(date);
        String currentTime = dateFormat1.format(date);


        return  view;
    }

    private void customerView(View view){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("About Our Clients");
            builder.setIcon(R.drawable.modaluser);
            builder.setMessage("Customer\n" +
                    "Everyone thinks that money is the lifeblood of every business but the truth is," +
                    "the customers are the ones who contribute a lot to the growth of any business.")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
        AlertDialog alert = builder.create();
        alert.show();

        Button button = alert.getButton(DialogInterface.BUTTON_POSITIVE);

        if(button != null ) {
            button.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_rounded));
        }
    }

    private void securityView(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Money Security");
        builder.setIcon(R.drawable.modalmoney);
        builder.setMessage("Our clients online payment is very secure since we comply with the PCI rule" +
                "which ensures that our customer confidential details is transmitted in an encrypted " +
                "manner via the HTTPS protocol in the internet")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

        Button button = alert.getButton(DialogInterface.BUTTON_POSITIVE);

        if(button != null ) {
            button.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_rounded));
        }
    }

    private void cloudView(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Cloud Storage ");
        builder.setIcon(R.drawable.modalcloud);
        builder.setMessage("Users can access data stored on the cloud from anywhere with internet access, " +
                "from many different types of devices.")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

        Button button = alert.getButton(DialogInterface.BUTTON_POSITIVE);

        if(button != null ) {
            button.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_rounded));
        }
    }

    private void benefitView(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Benefit Of PicknScan");
        builder.setIcon(R.drawable.modalstar);
        builder.setMessage("You wont have to Stand queues from the shops when you want to pay" +
                "You will simply pay online")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

        Button button = alert.getButton(DialogInterface.BUTTON_POSITIVE);

        if(button != null ) {
            button.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_rounded));
        }
    }

}
