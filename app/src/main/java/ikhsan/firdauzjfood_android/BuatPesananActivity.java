package ikhsan.firdauzjfood_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class BuatPesananActivity extends AppCompatActivity
{
    private int currentUserId;
    private String currentUserName;
    private int foodId;
    private String foodName;
    private String foodCategory;
    private double foodPrice;
    private String promoCode;
    private String selectedPayment;
    private int DELIVERY_FEE = 5000;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_pesanan);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            currentUserId = extras.getInt("currentUserId");
            currentUserName = extras.getString("currentUserName");
            foodId = extras.getInt("currentFoodId");
            foodName = extras.getString("currentFoodName");
            foodCategory = extras.getString("currentFoodCategory");
            foodPrice = extras.getDouble("currentFoodPrice");
        }


        final Button buttonHitung = findViewById(R.id.hitung);
        final Button buttonOrder = findViewById(R.id.pesan);
        final TextView promo_code = findViewById(R.id.static_order_promo_code);
        final TextView static_delivery_fee = findViewById(R.id.static_order_delivery);
        final TextView food_name = findViewById(R.id.order_food_name);
        final TextView food_category = findViewById(R.id.order_food_category);
        final TextView food_price = findViewById(R.id.order_food_price);
        final TextView total_price = findViewById(R.id.order_total_price);
        final EditText inputPromo = findViewById(R.id.order_promo_code);
        final RadioGroup radioGroup = findViewById(R.id.radioGroup);
        final TextView delivery_fee = findViewById(R.id.order_delivery_fee);

        buttonOrder.setVisibility(View.INVISIBLE);
        promo_code.setVisibility(View.INVISIBLE);
        inputPromo.setVisibility(View.INVISIBLE);
        static_delivery_fee.setVisibility(View.INVISIBLE);
        delivery_fee.setVisibility(View.INVISIBLE);

        food_name.setText(foodName);
        food_category.setText(foodCategory);

        String tempStringDeliveryFee = String.valueOf(DELIVERY_FEE);
        delivery_fee.setText("Rp. "+tempStringDeliveryFee +  ",-");
        food_price.setText("Rp. "+ foodPrice + ",-");
        total_price.setText("Rp. 0,-");

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                RadioButton radioButton = findViewById(checkedId);
                selectedPayment = radioButton.getText().toString().trim();

                if(selectedPayment.equals("Cash"))
                {
                    static_delivery_fee.setVisibility(View.VISIBLE);
                    delivery_fee.setVisibility(View.VISIBLE);
                    promo_code.setVisibility(View.INVISIBLE);
                    inputPromo.setVisibility(View.INVISIBLE);
                }
                if(selectedPayment.equals("Cashless"))
                {
                    static_delivery_fee.setVisibility(View.INVISIBLE);
                    delivery_fee.setVisibility(View.INVISIBLE);
                    promo_code.setVisibility(View.VISIBLE);
                    inputPromo.setVisibility(View.VISIBLE);
                }
            }
        });

        buttonHitung.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int radioSelectedId = radioGroup.getCheckedRadioButtonId();
                RadioButton radioSelected = findViewById(radioSelectedId);
                String selected = radioSelected.getText().toString().trim();


                if(selected.equals("Cash"))
                {
                    double tempTotal = foodPrice + DELIVERY_FEE;
                    total_price.setText("Rp. " + tempTotal + ",-");
                    buttonHitung.setVisibility(View.INVISIBLE);
                    buttonOrder.setVisibility(View.VISIBLE);
                }
                else if(selected.equals("Cashless"))
                {
                    promoCode = inputPromo.getText().toString();
                    final Response.Listener<String> promoResponse = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (promoCode.isEmpty())
                            {
                                //no promo applied
                                Toast.makeText(BuatPesananActivity.this, "No Promo Code Applied", Toast.LENGTH_SHORT).show();
                                total_price.setText("Rp. " + foodPrice + ",-");

                                buttonHitung.setVisibility(View.INVISIBLE);
                                buttonOrder.setVisibility(View.VISIBLE);
                            }
                            else
                            {
                                try {
                                    JSONObject responseJSON = null;
                                    responseJSON = new JSONObject(response);

                                    int promoMinPrice = responseJSON.getInt("minPrice");
                                    int promoDiscountValue = responseJSON.getInt("discount");
                                    boolean promoStatus = responseJSON.getBoolean("active");


                                    if (responseJSON == null) {
                                        Toast.makeText(BuatPesananActivity.this, "Promo not found", Toast.LENGTH_SHORT).show();
                                    } else if (promoStatus == false) {
                                        Toast.makeText(BuatPesananActivity.this, "Promo already expired", Toast.LENGTH_SHORT).show();
                                    } else if (promoStatus == true) {
                                        if (foodPrice < promoMinPrice)
                                        {
                                            Toast.makeText(BuatPesananActivity.this, "Minimum Price is not fulfilled", Toast.LENGTH_SHORT).show();
                                        }
                                        else
                                        {
                                            Toast.makeText(BuatPesananActivity.this, "Promo Code Successfully Applied !", Toast.LENGTH_SHORT).show();
                                            total_price.setText("Rp. " + (foodPrice - promoDiscountValue) + ",-");
                                            buttonHitung.setVisibility(View.INVISIBLE);
                                            buttonOrder.setVisibility(View.VISIBLE);

                                            //change delivery fee to discount value
                                            static_delivery_fee.setText("Discount");
                                            String tmpDiscount = "- Rp." + promoDiscountValue + ",-";
                                            delivery_fee.setText(tmpDiscount);
                                            static_delivery_fee.setVisibility(View.VISIBLE);
                                            delivery_fee.setVisibility(View.VISIBLE);

                                        }
                                    }
                                } catch (JSONException e) {
                                    Toast.makeText(BuatPesananActivity.this, "Promo not found", Toast.LENGTH_LONG).show();
                                }

                            }
                        }
                    };

                    CheckPromoRequest promoRequest = new CheckPromoRequest(promoCode, promoResponse);
                    RequestQueue queue = Volley.newRequestQueue(BuatPesananActivity.this);
                    queue.add(promoRequest);

           /*         Response.ErrorListener errorPromo = new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            Log.d("Error", "Error Occured", error);
                        }
                    }; */
                }
                else if(selected.isEmpty())
                {
                    Toast.makeText(BuatPesananActivity.this, "Please select payment type", Toast.LENGTH_SHORT).show();
                    buttonHitung.setVisibility(View.VISIBLE);
                    buttonOrder.setVisibility(View.INVISIBLE);
                }
                else
                {
                    Toast.makeText(BuatPesananActivity.this, "Please select payment type", Toast.LENGTH_SHORT).show();
                    buttonHitung.setVisibility(View.VISIBLE);
                    buttonOrder.setVisibility(View.INVISIBLE);
                }
            }
        });

        buttonOrder.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int radioSelectedId = radioGroup.getCheckedRadioButtonId();
                RadioButton radioSelected = findViewById(radioSelectedId);
                String selected = radioSelected.getText().toString().trim();

                BuatPesananRequest request = null;

                final Response.Listener<String> responseListener = new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        try
                        {
                            JSONObject objJSON = new JSONObject(response);
                            Toast.makeText(BuatPesananActivity.this, "Your order has been placed", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("currentUserId", currentUserId);
                            startActivity(intent);
                            finish();
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                            Toast.makeText(BuatPesananActivity.this, "Please Finish Your Order First", Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                if(selected.equals("Cash"))
                {
                    request = new BuatPesananRequest(String.valueOf(foodId), String.valueOf(currentUserId) ,responseListener);
                }
                else if(selected.equals("Cashless"))
                {
                    request = new BuatPesananRequest(String.valueOf(foodId), String.valueOf(currentUserId), promoCode,  responseListener);
                }

                RequestQueue queue = Volley.newRequestQueue(BuatPesananActivity.this);
                queue.add(request);
            }
        });
    }
}
