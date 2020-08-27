package com.example.picknscan;
import java.util.regex.*;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Signup extends AppCompatActivity{

    private EditText firstName,lastName,email,phone;
    private Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        phone = findViewById(R.id.CellPhone);
    }


    public void nextRegisterPage(View view){
        Validations validation = new Validations();
        if(!TextUtils.isEmpty(firstName.getText().toString())) {
            if (validation.validateName(firstName.getText().toString())) {
                if (!TextUtils.isEmpty(lastName.getText().toString())) {
                    if(validation.validateName(lastName.getText().toString())) {
                        if (!TextUtils.isEmpty(phone.getText().toString())) {
                            if(validation.isValidMobile(phone.getText().toString())) {
                                Intent intent = new Intent(this, SignUpContinue.class);
                                intent.putExtra("FirstName", firstName.getText().toString());
                                intent.putExtra("LastName", lastName.getText().toString());
                                intent.putExtra("Phone", phone.getText().toString());
                                startActivity(intent);
                            }else {
                                phone.setError( "Cellphone number is invalid" );
                            }
                        }else{
                            phone.setError( "Cellphone number is required" );
                        }
                    }else{
                        lastName.setError( "Lastname  is to short" );
                    }
                }else{
                    lastName.setError( "Lastname  is required" );
                }
            }else {
                firstName.setError( "Firstname  is to short" );
            }
        }else{
            firstName.setError( "Firstname  is required" );
        }
    }

    @Override
    public void onBackPressed(){
        Intent intent  = new Intent(Signup.this,WELCOME.class);
        startActivity(intent);
        finish();
    }

    boolean isFirstNameValidated(String naam){
        if(naam.equals("")){
            Toast.makeText(this, "First name field is required", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(naam.matches("[A-Z]+([ '-][a-zA-Z]+)*")) {
            Toast.makeText(this, "Special characters are not allowed", Toast.LENGTH_LONG).show();
            return true;
        }
        else{
            return true;
        }
    }

    boolean isLastNameValidated(String snaam){
        if(snaam.equals("")){
            Toast.makeText(this, "Last name field is required", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(snaam.matches("[A-Z]+([ '-][a-zA-Z]+)*")) {
           // Toast.makeText(this, "Special characters are not allowed", Toast.LENGTH_LONG).show();
            return true;
        }
        return true;
    }

    boolean isPhoneNumber(String phone){
        Character character = phone.charAt(0);
        Character character2 = phone.charAt(1);
        int firstDigit = Character.getNumericValue(character);
        int secondInput = Character.getNumericValue(character2);

        if(phone.equals("")){
            Toast.makeText(this, "Phone number field is required", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(phone.length() != 10){
            Toast.makeText(this, "Phone Number should be ten digits", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(firstDigit != 0 && secondInput == 0){
            return false;
        }
        else {
            return true;
        }
    }


}
