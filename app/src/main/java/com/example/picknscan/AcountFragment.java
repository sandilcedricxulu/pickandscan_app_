package com.example.picknscan;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class AcountFragment extends Fragment {
    TextView changePasswordLabel;
    TextView viewPaymentsLabel;
    TextView viewRecieptLabel;
    TextView updateProfile;
    TextView logoutLabel;

    TextView name,email,cell;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    public AcountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_acount, container, false);
        changePasswordLabel = view.findViewById(R.id.changePassword);
        viewRecieptLabel = view.findViewById(R.id.reciept);
        logoutLabel = view.findViewById(R.id.logout);
        updateProfile = view.findViewById(R.id.changeProfileInfo);
        name = view.findViewById(R.id.account_names);
        email = view.findViewById(R.id.account_email);
        cell = view.findViewById(R.id.account_cell_number);

        loadUserAccountInfo();

        changePasswordLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new changePasswordFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        viewRecieptLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new recieptView();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        logoutLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removePreferences();
                Intent i = new Intent(getActivity(), Login.class);
                startActivity(i);
                ((Activity) getActivity()).overridePendingTransition(0, 0);
            }
        });

        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new UpdateProfile();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        return view;
    }

    void removePreferences(){
        pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();
        editor.remove("username");
        editor.remove("password");
        editor.remove("FirstName");
        editor.remove("LastName");
        editor.remove("Email");
        editor.remove("Cellphone");
        editor.remove("pin_hash");

        editor.commit();
        return;
    }

    void loadUserAccountInfo(){
        pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();

        name.setText(""+pref.getString("FirstName", null) +" "+ pref.getString("LastName", null));
        email.setText(""+pref.getString("username",null));
        cell.setText( ""+pref.getString("Cellphone", null));

        return;
    }


}
