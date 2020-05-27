package ikhsan.firdauzjfood_android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ExpandableListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ExpandableListView expListView;
    ExpandableListAdapter listAdapter;
    private ArrayList<Seller> listSeller = new ArrayList<>();
    private ArrayList<Food> foodIdList = new ArrayList<>();
    private HashMap<Seller, ArrayList<Food>> childMapping = new HashMap<>();
    private static int currentUserId;
    private static String currentUserName;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        expListView = findViewById(R.id.listFoods);
        final Button button_pesanan = findViewById(R.id.button_pesanan);


        Bundle extras = getIntent().getExtras();
        if(extras!=null)
        {
            currentUserId = extras.getInt("currentUserId");
            currentUserName = extras.getString("currentUserName");
        }


        refreshList();

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener()
        {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id)
            {
                Intent intentBuatPesanan = new Intent(MainActivity.this, BuatPesananActivity.class);

                int foodId = childMapping.get(listSeller.get(groupPosition)).get(childPosition).getId();
                String foodName = childMapping.get(listSeller.get(groupPosition)).get(childPosition).getName();
                String foodCategory = childMapping.get(listSeller.get(groupPosition)).get(childPosition).getCategory();
                double foodPrice = childMapping.get(listSeller.get(groupPosition)).get(childPosition).getPrice();

                intentBuatPesanan.putExtra("currentFoodId", foodId);
                intentBuatPesanan.putExtra("currentFoodName", foodName);
                intentBuatPesanan.putExtra("currentFoodCategory", foodCategory);
                intentBuatPesanan.putExtra("currentFoodPrice", foodPrice);

                intentBuatPesanan.putExtra("currentUserId", currentUserId);
                intentBuatPesanan.putExtra("currentUserName", currentUserName);

                startActivity(intentBuatPesanan);
                return true;

            }
        });

        button_pesanan.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intentPesanan = new Intent(MainActivity.this, SelesaiPesananActivity.class);
                intentPesanan.putExtra("currentUserId", currentUserId);
                startActivity(intentPesanan);
            }
        });

    }

    protected void refreshList() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonResponse = new JSONArray(response);
                    for (int i = 0; i < jsonResponse.length(); i++) {
                      //  AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                        JSONObject food = jsonResponse.getJSONObject(i);
                        JSONObject seller = food.getJSONObject("seller");
                        JSONObject location = seller.getJSONObject("location");

                        Location newLocation = new Location(
                                location.getString("province"),
                                location.getString("description"),
                                location.getString("city")
                        );

                        Seller newSeller = new Seller(
                                seller.getInt("id"),
                                seller.getString("name"),
                                seller.getString("email"),
                                seller.getString("phoneNumber"),
                                newLocation
                        );


                        Food newFood = new Food(
                                food.getInt("id"),
                                food.getString("name"),
                                newSeller,
                                food.getInt("price"),
                                food.getString("category")
                        );

                        foodIdList.add(newFood);

                        boolean statusFlag = true;
                        for (Seller sellerPtr : listSeller) {
                            if (sellerPtr.getId() == newSeller.getId()) {
                                statusFlag = false;
                            }
                        }
                        if (statusFlag == true) {
                            listSeller.add(newSeller);
                        }

                    }


                    for (Seller sel : listSeller) {
                        ArrayList<Food> temp = new ArrayList<>();
                        for (Food food : foodIdList) {
                            if (food.getSeller().getId() == sel.getId())
                            {
                                temp.add(food);
                            }
                        }
                        childMapping.put(sel, temp);
                    }

                    listAdapter = new MainListAdapter(MainActivity.this, listSeller, childMapping);
                    expListView.setAdapter(listAdapter);
                } catch (JSONException e) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Data error").create().show();
                }
            }
        };
        MenuRequest menuRequest = new MenuRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(menuRequest);
    }
}
