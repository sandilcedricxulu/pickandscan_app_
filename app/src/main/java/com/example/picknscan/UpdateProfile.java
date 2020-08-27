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

public class UpdateProfile extends Fragment {
    EditText firstNameAlter,lastNameAlter,emailAlter,cellNumberAlter;
    Button updateProfileBtn;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    private ProgressDialog progressDialog;

    public UpdateProfile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_profile, container, false);
        pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0);
        editor = pref.edit();
        firstNameAlter = view.findViewById(R.id.firstNameAlter);
        lastNameAlter = view.findViewById(R.id.lastNameAlter);
        cellNumberAlter = view.findViewById(R.id.cellNumberAlter);
        updateProfileBtn = view.findViewById(R.id.updateProfileBtn);

        firstNameAlter.setText(pref.getString("FirstName",null));
        lastNameAlter.setText(pref.getString("LastName",null));
        cellNumberAlter.setText(pref.getString("Cellphone",null));
        progressDialog = new ProgressDialog(getContext());

        updateProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Validations validation = new Validations();
                if(!(TextUtils.isEmpty(firstNameAlter.getText().toString()))) {
                    if (validation.validateName(firstNameAlter.getText().toString())) {
                        if (!(TextUtils.isEmpty(lastNameAlter.getText().toString()))) {
                            if(validation.validateName(lastNameAlter.getText().toString())) {
                                if (!(TextUtils.isEmpty(cellNumberAlter.getText().toString()))) {
                                    if (validation.isValidMobile(cellNumberAlter.getText().toString())) {
                                        try {
                                            if(HttpMethods.isOnline()){
                                                if(updateUserProfile()){
                                                    progressDialog.setMessage("Updating your profile.....");
                                                    progressDialog.show();
                                                    Runnable progressRunnable = new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            progressDialog.cancel();
                                                            Fragment fragment = new AcountFragment();
                                                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                            fragmentTransaction.replace(R.id.fragment_container, fragment);
                                                            fragmentTransaction.addToBackStack(null);
                                                            fragmentTransaction.commit();
                                                            Toast.makeText(getActivity(), "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
                                                        }
                                                    };
                                                    Handler pdCanceller = new Handler();
                                                    pdCanceller.postDelayed(progressRunnable,3000);
                                                }
                                                else {

                                                }
                                            }
                                            else {
                                                Toast.makeText(getContext(), "Check your internet connection", Toast.LENGTH_SHORT).show();
                                            }

                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        cellNumberAlter.setError("Cellphone is invalid");
                                    }
                                } else {
                                    cellNumberAlter.setError("Cellphone is required");
                                }
                            }else {
                                lastNameAlter.setError("Lastname is too short");
                            }
                        } else {
                            lastNameAlter.setError("Lastname is required");
                        }
                    }else {
                        firstNameAlter.setError("Firstname is too short");
                    }
                }
                else{
                    firstNameAlter.setError("Firstname is required");
                }
            }
        });

        return view;
    }

    boolean updateUserProfile() throws IOException, JSONException {

        if(firstNameAlter.getText().toString().equals(pref.getString("FirstName",null)) && lastNameAlter.getText().toString().equals(pref.getString("LastName",null)) && cellNumberAlter.getText().toString().equals(pref.getString("Cellphone",null))){
            Toast.makeText(getActivity(), "No changes made", Toast.LENGTH_SHORT).show();
            return false;
        }
        String body = "{\"Firstname\": \""+firstNameAlter.getText().toString()+"\",\"Lastname\": \""+lastNameAlter.getText().toString()+"\",\"Email\": \""+pref.getString("Email",null)+"\",\"Cellphone\": \""+cellNumberAlter.getText().toString()+"\",\"Password\":\""+pref.getString("password", null)+"\"}";
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String response = HttpMethods.Put("http://mkhululi.net/picknscan/api/user/edit/",pref.getString("username", null),pref.getString("password", null),""+body);

        JSONObject jsonResponse = new JSONObject(response);
        if(jsonResponse.getString("status_code").equals("200")){
            editor.putString("username", pref.getString("username", null));
            editor.putString("password", pref.getString("password", null));
            editor.putString("FirstName",firstNameAlter.getText().toString());
            editor.putString("LastName", lastNameAlter.getText().toString());
            editor.putString("Cellphone", cellNumberAlter.getText().toString());
            editor.commit();
            return true;
        }
        else{
            Toast.makeText(getActivity(), "Cannot update try to logout try update again", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
