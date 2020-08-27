package com.example.picknscan;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.craftman.cardform.Card;
import com.craftman.cardform.CardForm;
import com.craftman.cardform.OnPayBtnClickListner;


public class addCreditCard extends Fragment {

    public addCreditCard() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_credit_card, container, false);
        CardForm cardForm = view.findViewById(R.id.creditCardForm);
        TextView txtAmt = view.findViewById(R.id.payment_amount);
        Button btnAdd = view.findViewById(R.id.btn_pay);

        txtAmt.setText("R. 1222");
        btnAdd.setText(String.format("Add Card",txtAmt.getText()));

        cardForm.setPayBtnClickListner(new OnPayBtnClickListner() {
            @Override
            public void onClick(Card card) {
//                Toast.makeText(getActivity(),"Name : "+card.getName()+" | Last 4 Digits: "+card.getLast4(),Toast.LENGTH_SHORT).show();
//                Toast.makeText(getActivity(),"Card Number : "+card.getNumber()+" |  expire month "+card.getExpMonth(),Toast.LENGTH_SHORT).show();
//                Toast.makeText(getActivity(),"ccv : "+card.getCVC()+" |  expire year "+card.getExpYear(),Toast.LENGTH_SHORT).show();


            }
        });



        return view;
    }



}
