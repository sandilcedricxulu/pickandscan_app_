package com.example.picknscan;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class changePasswordFragment extends Fragment {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    EditText current_password, new_password,confirm_password;
    Button changePasswordBtn;
    private ProgressDialog progressDialog;

    public changePasswordFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);

        current_password = view.findViewById(R.id.current_password);
        new_password = view.findViewById(R.id.new_password);
        confirm_password = view.findViewById(R.id.confirm_password);
        changePasswordBtn  = view.findViewById(R.id.changePasswordBtn);
        progressDialog = new ProgressDialog(getContext());
        changePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(current_password.getText().toString() != "" && new_password.getText().toString() != "" && confirm_password.getText().toString() != "")
                {
                    if(HttpMethods.isOnline()){
                        if(updatedPassword()){
                            if(updatePassword()){
                                progressDialog.setMessage("Updating password.....");
                                progressDialog.show();
                                Runnable progressRunnable = new Runnable() {
                                    @Override
                                    public void run() {
                                        progressDialog.cancel();
                                        pref = getContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                                        editor = pref.edit();
                                        editor.putString("password",confirm_password.getText().toString()); // store the new password local
                                        editor.commit();
                                        Toast.makeText(getContext(), "Password Updated Successful", Toast.LENGTH_SHORT).show();
                                        Fragment fragment = new AcountFragment();
                                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                        fragmentTransaction.replace(R.id.fragment_container, fragment);
                                        fragmentTransaction.addToBackStack(null);
                                        fragmentTransaction.commit();
                                    }
                                };
                                Handler pdCanceller = new Handler();
                                pdCanceller.postDelayed(progressRunnable,3000);
                            }
                    } else {
                            Toast.makeText(getContext(), "All text box must be full", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getContext(), "Check your internet connection", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });


        return view;
    }


    public boolean updatedPassword() {
        if(!TextUtils.isEmpty(current_password.getText().toString())) {
            if(!TextUtils.isEmpty(new_password.getText().toString())){
                if(new_password.getText().toString().length() >= 8){
                    if(!TextUtils.isEmpty(confirm_password.getText().toString())){
                        if(corfimCurrent()){
                            System.out.println("New password "+new_password.getText().toString());
                            System.out.println("Conf pass "+confirm_password.getText().toString());
                            if(new_password.getText().toString().equals(confirm_password.getText().toString())){
                                return true;
                            }else {
                                Toast.makeText(getActivity(), "Password does not match", Toast.LENGTH_SHORT).show();
                            }
                            }else {
                                Toast.makeText(getActivity(), "Current password is incorrect", Toast.LENGTH_SHORT).show();
                            }
                    }else{
                        confirm_password.setError( "Confirm password  is required" );
                    }

                }else {
                    new_password.setError( "Password must be 8 characters or more" );
                }
            }else {
                new_password.setError( "New password  is required" );
            }
        }else {
            current_password.setError( "Current password  is required" );
        }
        return  false;
    }
    public boolean corfimCurrent()
    {
        pref = getContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();

        String storedPassword = storedPassword();
        if(current_password.getText().toString().equals(storedPassword))
        {
            return true;
        }
        return false;
    }
    public String storedPassword()
    {
        pref = getContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();
        return pref.getString("password", null);
    }

    public String storedUsername()
    {
        pref = getContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();
        return pref.getString("username", null);
    }

    public String storedFirstName()
    {
        pref = getContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();
        return pref.getString("FirstName", null);
    }

    public String storedLastName()
    {
        pref = getContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();
        return pref.getString("LastName", null);
    }
    public String storedCellphone()
    {
        pref = getContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();
        return pref.getString("Cellphone", null);
    }
    public boolean updatePassword()
    {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            String response = HttpMethods.Put("http://mkhululi.net/picknscan/api/user/edit/",""+storedUsername(),""+storedPassword(),"{\"Firstname\": \""+storedFirstName()+"\",\"Lastname\": \""+storedLastName()+"\",\"Email\": \""+pref.getString("username", null)+"\",\"Cellphone\": \""+storedCellphone()+"\",\"Password\":\""+new_password.getText().toString()+"\"}");
            JSONObject jsonResponse = new JSONObject(response);
            if(jsonResponse.getString("status_code").equals("200"))
            {
                return true;
            }
            else
            {
                Toast.makeText(getContext(), "Cannot update password"+response,Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), ""+e, Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }


}
