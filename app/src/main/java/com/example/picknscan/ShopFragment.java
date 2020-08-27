package com.example.picknscan;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;


public class ShopFragment extends Fragment {

    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;
    public static ListView listOfItems;
    ImageButton manual_barcode_imageView1;
    public static Button buttonCheckout;
    TextView currentQuantity;
    TextView enter_manual_barcode2;
    public static TextView subTotalItems;
    public static TextView totalAmount;

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;
    public static SharedPreferences sharedPreferences2;

    public ShopFragment() {
        // Required empty public constructor

    }
    TextView barcodeShow;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if(getArguments() != null){
            outState.putString("quantityCountKey", getArguments().getString("quantityCount"));
            super.onSaveInstanceState(outState);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater,final ViewGroup container,
                             Bundle savedInstanceState) {
        final View veiw = inflater.inflate(R.layout.fragment_shop, container, false);
        Button switchButton = veiw.findViewById(R.id.toScan);
        listOfItems = veiw.findViewById(R.id.scanneditemlist);
        barcodeShow = veiw.findViewById(R.id.theText);
        buttonCheckout =  veiw.findViewById(R.id.toCheck);
        currentQuantity = veiw.findViewById(R.id.quantity);
        manual_barcode_imageView1 = veiw.findViewById(R.id.manual_barcode_imageView1);

        sharedPreferences = getActivity().getApplicationContext().getSharedPreferences("save_my_products", 0);
        sharedPreferences2 = getActivity().getApplicationContext().getSharedPreferences("save_my_products", 0);


        pref = getActivity().getApplicationContext().getSharedPreferences("MyPref",0);
        editor = pref.edit();


        subTotalItems = veiw.findViewById(R.id.subTotalItems);
        totalAmount = veiw.findViewById(R.id.totalAmount);

        returnedValues();

        listOfItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "List item was clicked at " + position, Toast.LENGTH_SHORT).show();
            }
        });

        switchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ScanFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        buttonCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new choosePaymentFragment();
                Bundle args = new Bundle();
                args.putString("amount",totalAmount.getText().toString());
                System.out.println("This is the amount am passing "+totalAmount.getText().toString());
                fragment.setArguments(args);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        manual_barcode_imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = requireActivity().getLayoutInflater();
                final View mView = inflater.inflate(R.layout.manual_barcode_alert_dialog,container, false);

                builder.setView(mView)
                        .setPositiveButton("Add items", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                final EditText manualBarcode = mView.findViewById(R.id.manual_barcode_value);
                                try {
                                    getProducts(manualBarcode.getText().toString());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Fragment fragment = new ShopFragment();
                                Bundle args = new Bundle();
                                args.putString("productName", prodcutName);
                                args.putString("productID", ProductID);
                                args.putString("quantityPerUnit", quantityPerUnit);
                                args.putString("Unitprice", String.valueOf(Unitprice));
                                fragment.setArguments(args);
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.fragment_container, fragment);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

                Button button = alert.getButton(DialogInterface.BUTTON_POSITIVE);
                Button button2 = alert.getButton(DialogInterface.BUTTON_NEGATIVE);

                if(button != null ) {
                    button.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_rounded));
                    button.setTextSize(15);
                    button.setWidth(600);
                    button.setPadding(110,0,110,0);
                }
            }
        });

        return veiw;
    }
    public static void AddTotal(double total){
        totalAmount.setText(Double.toString(Math.round((total+Double.parseDouble(totalAmount.getText().toString()))*100.0)/100.0));
    }
    public static void subTotal(double total){
        totalAmount.setText(Double.toString(Math.round((Double.parseDouble(totalAmount.getText().toString())-total)*100.0)/100.0));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void returnedValues(){
        if(products.size() > 1)
        {
            loadData();
        }
        if(getArguments() != null){
            if(!getArguments().getString("productName").equals("Cancel"))
            {
                if(!getArguments().getString("productName").equals("Product not found"))
                {
                    String productName = getArguments().getString("productName");
                    int productID = Integer.parseInt(getArguments().getString("productID"));
                    String quantityPerUnit = getArguments().getString("quantityPerUnit");
                    String UnitsPrice = getArguments().getString("Unitprice");
                    int i = 0;
                    for(i = 0; i < products.size();i++)
                    {
                        if(productName.equals(products.get(i).ProductName()))
                        {
                            products.set(i,new Products(productID,""+products.get(i).ProductName(),0+products.get(i).UnitPrice(),""+products.get(i).QuantityPerUnit(),products.get(i).Quantity()+1,(products.get(i).Quantity()+1*Double.parseDouble(UnitsPrice))));
                            i = products.size() + 10;
                            adapterItem = new scanneditemlistclass(getContext(),products);
                            listOfItems.setAdapter(adapterItem);

                        }
                    }
                    if(i == products.size())
                    {
                        products.add(new Products(productID,productName,Double.parseDouble(UnitsPrice),""+quantityPerUnit,1,(1*Double.parseDouble(UnitsPrice))));
                        adapterItem = new scanneditemlistclass(getContext(),products);
                        listOfItems.setAdapter(adapterItem);
                    }
                }
                else {
                    adapterItem = new scanneditemlistclass(getContext(),products);
                    listOfItems.setAdapter(adapterItem);
                    Toast.makeText(getContext(), "You cannot add unavailable product scan again", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                adapterItem = new scanneditemlistclass(getContext(),products);
                listOfItems.setAdapter(adapterItem);
            }

        }
        calculateSubtotal();
        calculateTotalAmount();

    }
    void calculateSubtotal()
    {
        subTotalItems.setText("Subtotal("+Integer.toString(products.size())+") items");
    }
    static void recalculateSubtotal()
    {
        subTotalItems.setText("Subtotal("+Integer.toString(products.size())+") items");
        if(products.size() == 0)
        {
            //go back to first scanned coz you empty now we dont want you to click checkout
            buttonCheckout.setClickable(false);
        }
    }
    void calculateTotalAmount() {
        double total = 0;
        for(int i = 0;i < products.size();i++)
        {
            total = total + products.get(i).Total(products.get(i).Quantity(),products.get(i).UnitPrice());
        }
        totalAmount.setText(Double.toString(Math.round(total*100.0)/100.0));
    }
    static void reCalculateTotalAmount() {
        double total = 0;
        for(int i = 0;i < products.size();i++)
        {
            total = total + products.get(i).Total(products.get(i).Quantity(),products.get(i).UnitPrice());
        }
        totalAmount.setText("R"+(total));
    }

    private void loadData() {
        Toast.makeText(getContext(), "Executed", Toast.LENGTH_LONG).show();
        Gson gson = new Gson();
        String json = sharedPreferences.getString("myProductList", null);
        Type type = new TypeToken<ArrayList<Products>>() {}.getType();
        products = gson.fromJson(json, type);

        if (products == null) {
            products = new ArrayList<>();
        }
        adapterItem = new scanneditemlistclass(getContext().getApplicationContext(),products);
        listOfItems.setAdapter(adapterItem);
        return;
    }
    public static void reloadList(Context context){
        Gson gson = new Gson();
        String json = sharedPreferences2.getString("myProductList", null);
        Type type = new TypeToken<ArrayList<Products>>() {}.getType();
        products = gson.fromJson(json, type);
        if (products == null) {
            products = new ArrayList<>();
        }
        adapterItem = new scanneditemlistclass(context,products);
        listOfItems.setAdapter(adapterItem);
        return;

    }
    static scanneditemlistclass adapterItem;
    public static ArrayList<Products> products = new ArrayList<>();


    String prodcutName = "Product not found",quantityPerUnit = "Please try again or enter barcode manual";
    String Unitprice = "0.00";
    String ProductID ="";

    void getProducts(String barcode) throws IOException, JSONException {
        String body = "{\"id\":1}";
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String response = HttpMethods.Get("http://mkhululi.net/picknscan/api/products/barcode/"+barcode,pref.getString("username",null),pref.getString("password",null),body);
        JSONObject jsonResponse = new JSONObject(response);
        if(jsonResponse.getString("status_code").equals(""+200) ){
            prodcutName = jsonResponse.getJSONObject("data").getString("ProductName");
            ProductID = jsonResponse.getJSONObject("data").getString("ProductID");
            quantityPerUnit = jsonResponse.getJSONObject("data").getString("QuantityPerUnit");
            Unitprice = (jsonResponse.getJSONObject("data").getString("UnitPrice"));
        }
        else
        {
            prodcutName = "Product not found";
            quantityPerUnit = "Please try again or enter barcode manual";
            Unitprice = "0.00";
        }
    }public static class scanneditemlistclass  extends ArrayAdapter<Products>{

        Context context;
        Products prod;
        TextView total;
        TextView productName;
        TextView unitPrice,qtyPerUnits;
        Button minus,plus;
        LayoutInflater inflater;
        SharedPreferences sharedPreferences;

        public scanneditemlistclass(Context applicationContext, ArrayList<Products> products){
            super(applicationContext,0,products);
            context =applicationContext;
            printList(products);
        }

        //@Override
        public View getView(final int position, View view, ViewGroup parent) {
            if(view == null){
                view = LayoutInflater.from(getContext()).inflate(R.layout.scanneditemlist,parent,false);

            }

            prod  = getItem(position);
            productName = (TextView)view.findViewById(R.id.item_name);
            unitPrice = (TextView)view.findViewById(R.id.item_price);
            final TextView total = (TextView)view.findViewById(R.id.overall_price);
            final TextView quantity = (TextView)view.findViewById(R.id.quantity);
            qtyPerUnits = (TextView)view.findViewById(R.id.item_qpu);
            minus = view.findViewById(R.id.minus);
            plus = view.findViewById(R.id.plus);

            productName.setText(prod.ProductName());
            quantity.setText(Integer.toString(prod.Quantity()));
            qtyPerUnits.setText(prod.QuantityPerUnit());
            unitPrice.setText("R"+prod.UnitPrice());
            total.setText("R"+Math.round((prod.Total(prod.Quantity(),prod.UnitPrice()))*100.0)/100.0);

            plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int count = Integer.parseInt(String.valueOf(quantity.getText()));
                    count += 1;
                    prod.Quantity(count);
                    quantity.setText(Integer.toString(count));
                    total.setText("R"+Math.round((count*productsArrayList.get(position).UnitPrice())*100.0)/100.0);
                    increaseQuantity(position,count);
                    ShopFragment.AddTotal(productsArrayList.get(position).UnitPrice());

                }
            });
            minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int count = Integer.parseInt(String.valueOf(quantity.getText()));
                    count -= 1;
                    System.out.println("Count is "+count+" now ");
                    if(count != 0)
                    {
                        prod.Quantity(count);
                        quantity.setText(Integer.toString(count));
                        total.setText("R"+Math.round((count*productsArrayList.get(position).UnitPrice())*100.0)/100.0);
                        increaseQuantity(position ,count);
                        ShopFragment.subTotal(productsArrayList.get(position).UnitPrice());
                    }
                    else{
                        System.out.println("It my turn to remove");
                        productsArrayList.remove(position);
                        saveData();
                        ShopFragment.reloadList(context);
                        reCalculateTotalAmount();
                        recalculateSubtotal();
                    }

                }
            });

            return view;
        }


        ArrayList<Products> productsArrayList = new ArrayList<>();


        public void printList(ArrayList<Products> products)
        {
            sharedPreferences = getContext().getApplicationContext().getSharedPreferences("save_my_products", 0);

            if(products.size() == 1)
            {
                productsArrayList = products;
                saveData();
                return;
            }
            loadData();
            if(products.size() > productsArrayList.size())
            {
                productsArrayList.add(products.get(products.size()-1));
                saveData();
            }

        }


        void increaseQuantity(int i,int count)
        {
            loadData();
            productsArrayList.set(i,new Products(productsArrayList.get(i).ProductID(),productsArrayList.get(i).ProductName(),productsArrayList.get(i).UnitPrice(),productsArrayList.get(i).QuantityPerUnit(),count,(count*productsArrayList.get(i).UnitPrice())));
            saveData();
        }

        private void saveData() {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("myProductList");
            editor.commit();


            Gson gson = new Gson();
            String json = gson.toJson(productsArrayList);
            editor.putString("myProductList", json);
            editor.commit();

        }

        private void loadData() {
            Gson gson = new Gson();
            String json = sharedPreferences.getString("myProductList", null);
            Type type = new TypeToken<ArrayList<Products>>() {}.getType();
            productsArrayList = gson.fromJson(json, type);
            if (productsArrayList == null) {
                productsArrayList = new ArrayList<>();
            }
            return;
        }
    }
}

