package com.example.picknscan;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class confirmOnlinePayment extends Fragment {

    Button confirmPaymentTransaction;
    Button addNewCard;
    ViewFlipper v_flipper;
    ListView card_listView;
    TextView ctitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.confirmonlinepayment, container, false);
/*        ViewPager viewPager = view.findViewById(R.id.card_slides);
        card_slide_adapter csa = new card_slide_adapter(getActivity());
        viewPager.setAdapter(csa);*/

        confirmPaymentTransaction = view.findViewById(R.id.confirm_transaction);
        addNewCard = view.findViewById(R.id.add_card);
        v_flipper = view.findViewById(R.id.v_flipper);
        card_listView = view.findViewById(R.id.card_list_view);
        card_listView.setClickable(true);
        ctitle =  view.findViewById(R.id.card_title);

        // card List
        String availableCards [] = {"Capitec Card","Absa Card","Standard Card","Nedbank Card","FNB Card"};
        String exp_date [] = {"12/02","07/16","05/12","01/01","09/21"};

        CustomAdapter customAdapter = new CustomAdapter(getContext(), availableCards,exp_date);
        card_listView.setAdapter(customAdapter);
        card_listView.setOnItemClickListener(listClick);
/*        card_listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                Toast.makeText(getActivity().getApplicationContext(),"You selected"+selectedItem+" ",Toast.LENGTH_LONG).show();
            }
        });*/
        //end card list



        // Slide images
        int images [] = {R.drawable.c1,R.drawable.c2,R.drawable.c3};
        for(int img: images){
            flipperImage(img);
        }
        // end of slide images

        confirmPaymentTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //reciept alert dialog/invoice

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
/*                ImageView image = new ImageView(getActivity());
                image.setImageResource(R.drawable.overallbarcode);*/
                builder.setTitle("RECIEPT");
                //builder.setIcon(R.drawable.barcodescan);
                builder.setMessage("")
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
        });


        addNewCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new addCreditCard();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return view;
    }


    public void flipperImage(int image){
        ImageView imageView = new ImageView(getActivity());
        imageView.setBackgroundResource(image);

        v_flipper.addView(imageView);
        v_flipper.setFlipInterval(4000);
        v_flipper.setAutoStart(true);

        v_flipper.setInAnimation(getActivity(), android.R.anim.slide_in_left);
        v_flipper.setOutAnimation(getActivity(), android.R.anim.slide_out_right);

    }

    private AdapterView.OnItemClickListener listClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String selectedItem = (String) parent.getItemAtPosition(position);
            Toast.makeText(getActivity().getApplicationContext(),"You selected"+selectedItem+" ",Toast.LENGTH_SHORT).show();
        }
    };
}
