package ikhsan.firdauzjfood_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.FontResourcesParserCompat;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.CellIdentity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class SelesaiPesananActivity extends AppCompatActivity {

    private int currentUserId;
    private int currentInvoiceId;
    private String foodName;
    private String foodCategory;
    private String customerName;
    private String date;
    private String promoCode;
    private String paymentType;
    private int totalPrice;
    private String invoiceStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            currentUserId = extras.getInt("currentUserId");
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selesai_pesanan);

        final TextView static_title_invoice = findViewById(R.id.static_invoice);
        final TextView static_food_name = findViewById(R.id.static_invoice_food_name);
        final TextView static_food_category = findViewById(R.id.static_invoice_food_category);
        final TextView static_date = findViewById(R.id.static_invoice_date);
        final TextView static_delivery_fee = findViewById(R.id.static_invoice_delivery_fee);
        final TextView static_payment_type = findViewById(R.id.static_invoice_payment_type);
        final TextView static_total_price = findViewById(R.id.static_invoice_total_price);
        final TextView static_invoice_id = findViewById(R.id.static_invoice_id);
        final TextView customer_name = findViewById(R.id.static_invoice_cutomer_name);
        final TextView static_no_order = findViewById(R.id.static_invoice_no_order);


        final TextView invoice_id = findViewById(R.id.invoice_id);
        final TextView food_name = findViewById(R.id.invoice_food_name);
        final TextView food_category = findViewById(R.id.invoice_food_category);
        final TextView invoice_date = findViewById(R.id.invoice_date);
        final TextView delivery_fee = findViewById(R.id.invoice_delivery_fee);
        final TextView payment_type = findViewById(R.id.invoice_payment_type);
        final TextView total_price = findViewById(R.id.invoice_total_price);

        final Button button_cancel = findViewById(R.id.invoice_button_cancel);
        final Button button_finish = findViewById(R.id.invoice_button_finish);

        //all view invisible
        allViewInvisible(static_invoice_id, invoice_id,static_title_invoice, customer_name, static_food_name,
                static_food_category, static_date,
                 static_delivery_fee, static_payment_type, static_total_price,
                food_name, food_category, invoice_date, delivery_fee,
                payment_type, total_price, button_cancel, button_finish, static_no_order);

        final Response.Listener<String> responseListener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    JSONArray responseJSON = new JSONArray(response);
                    if(responseJSON.isNull(0))
                    {
                        static_no_order.setVisibility(View.VISIBLE);
                    }
                    for(int i = 0; i < responseJSON.length(); i++)
                    {
                        JSONObject objInvoice =  responseJSON.getJSONObject(i);
                        currentInvoiceId = objInvoice.getInt("id");
                        date = objInvoice.getString("date");
                        paymentType = objInvoice.getString("paymentType");
                        totalPrice = objInvoice.getInt("totalPrice");
                        invoiceStatus = objInvoice.getString("invoiceStatus");

                        //causes error, everything not displayed
                        //deliveryFee = objInvoice.getInt("deliveryFee");

                        invoice_id.setText(String.valueOf(currentInvoiceId));
                        invoice_date.setText(date.substring(0,9));
                        payment_type.setText(paymentType);
                        total_price.setText(String.valueOf(totalPrice));
                        delivery_fee.setText("5000");

                        JSONObject objCustomer = objInvoice.getJSONObject("customer");
                        customerName = objCustomer.getString("name");
                        customer_name.setText(customerName);

                        JSONArray foods = objInvoice.getJSONArray("foods");
                        for(int j = 0; j < foods.length(); j++)
                        {
                            JSONObject objFood = foods.getJSONObject(j);
                            foodName = objFood.getString("name");
                            foodCategory = objFood.getString("category");
                            food_name.setText(foodName);
                            food_category.setText(foodCategory);
                        }

                        if(invoiceStatus.equals("Ongoing"))
                        {
                            static_no_order.setVisibility(View.INVISIBLE);
                            allViewVisible(static_invoice_id, invoice_id,static_title_invoice, customer_name, static_food_name,
                                    static_food_category, static_date,
                                    static_delivery_fee, static_payment_type, static_total_price,
                                    food_name, food_category, invoice_date, delivery_fee,
                                    payment_type, total_price, button_cancel, button_finish);

                            //overwrite after changed to promo code
                            static_delivery_fee.setText("Delivery fee");
                        }
                        else
                        {
                            static_no_order.setVisibility(View.VISIBLE);
                        }


                        //change delivery fee to promo code
                        if(paymentType.equals("Cashless"))
                        {
                            static_delivery_fee.setText("Promo Code");
                            promoCode = objInvoice.getString("promo");
                            if(promoCode.equals("null"))
                            {
                                delivery_fee.setText("no promo applied");
                            }
                            else
                            {
                                JSONObject objPromo = objInvoice.getJSONObject("promo");
                                promoCode = objPromo.getString("code");
                                delivery_fee.setText(promoCode);
                            }
                        }


                    }


                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        };
        PesananFetchRequest fetchRequest = new PesananFetchRequest(String.valueOf(currentUserId), responseListener);
        RequestQueue queue = Volley.newRequestQueue(SelesaiPesananActivity.this);
        queue.add(fetchRequest);


        button_cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final Response.Listener<String> cancelListener = new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        try
                        {
                            JSONObject objJSON = new JSONObject(response);
                            Toast.makeText(SelesaiPesananActivity.this, "Your Order has been Canceled", Toast.LENGTH_SHORT).show();
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                };
                PesananBatalRequest batalRequest = new PesananBatalRequest(String.valueOf(currentInvoiceId), cancelListener);
                RequestQueue queue = Volley.newRequestQueue(SelesaiPesananActivity.this);
                queue.add(batalRequest);

                Intent intentInvoice = new Intent(SelesaiPesananActivity.this, MainActivity.class);
                intentInvoice.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentInvoice);
                finish();

            }
        });

        button_finish.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final Response.Listener<String> finishListener = new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        try
                        {
                            JSONObject objJSON = new JSONObject(response);
                            Toast.makeText(SelesaiPesananActivity.this, "Your Order has been Submitted", Toast.LENGTH_SHORT).show();
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                };
                PesananSelesaiRequest selesaiRequest = new PesananSelesaiRequest(String.valueOf(currentInvoiceId), finishListener);
                RequestQueue queue = Volley.newRequestQueue(SelesaiPesananActivity.this);
                queue.add(selesaiRequest);

                Intent intentInvoice = new Intent(SelesaiPesananActivity.this, MainActivity.class);
                intentInvoice.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentInvoice);
                finish();
            }
        });

    }

    //method to make every view invisible
    protected void allViewInvisible(View v1, View v2, View v3,
                                    View v4, View v5, View v6,
                                    View v7,View v8,View v9,
                                    View v10, View v11,View v12,
                                    View v13, View v14, View v15,
                                    View v16, View v17, View v18, View v19)
    {
        viewInvisible(v1);viewInvisible(v2);viewInvisible(v3);
        viewInvisible(v4);viewInvisible(v5);viewInvisible(v6);
        viewInvisible(v7);viewInvisible(v8);viewInvisible(v9);
        viewInvisible(v10);viewInvisible(v11);viewInvisible(v12);
        viewInvisible(v13);viewInvisible(v14);viewInvisible(v15);
        viewInvisible(v16);viewInvisible(v17);viewInvisible(v18);viewInvisible(v19);
    }

    //method to make every view visible
    protected void allViewVisible(View v1, View v2, View v3,
                                  View v4, View v5, View v6,
                                  View v7,View v8,View v9,
                                  View v10, View v11,View v12,
                                  View v13, View v14, View v15,
                                  View v16, View v17, View v18)
    {
        viewVisible(v1);viewVisible(v2);viewVisible(v3);
        viewVisible(v4);viewVisible(v5);viewVisible(v6);
        viewVisible(v7);viewVisible(v8);viewVisible(v9);
        viewVisible(v10);viewVisible(v11);viewVisible(v12);
        viewVisible(v13);viewVisible(v14);viewVisible(v15);
        viewVisible(v16);viewVisible(v17);viewVisible(v18);
    }

    //method to shorten invisible method
    protected void viewInvisible(View v)
    {
        v.setVisibility(View.INVISIBLE);
    }

    //method to shorten visible method
    protected void viewVisible(View v)
    {
        v.setVisibility(View.VISIBLE);
    }

}
