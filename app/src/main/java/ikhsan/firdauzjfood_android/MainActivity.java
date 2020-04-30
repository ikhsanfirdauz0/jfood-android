package ikhsan.firdauzjfood_android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ExpandableListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ExpandableListView expListView = null;
        MainListAdapter listAdapter = new MainListAdapter(MainActivity.this, listSeller, childMapping);
        expListView.setAdapter(listAdapter);
    }

    private ArrayList<Seller> listSeller = new ArrayList<>();
    private ArrayList<Food> foodIdList = new ArrayList<>();
    private HashMap<Seller, ArrayList<Food>> childMapping = new HashMap<>();

    protected void refreshList()
    {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    JSONArray jsonResponse = new JSONArray(response);
                    for (int i = 0; i < jsonResponse.length(); i++)
                    {
                        JSONObject food = jsonResponse.getJSONObject(i);
                        JSONObject seller = food.getJSONObject("seller");
                        JSONObject location = seller.getJSONObject("location");
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }


                for(Seller sel : listSeller)
                {
                    ArrayList<Food> temp = new ArrayList<>();
                    for(Food food : foodIdList)
                    {
                        if (food.getSeller().getName().equals(sel.getName()) ||
                                food.getSeller().getEmail().equals(sel.getEmail()) ||
                                food.getSeller().getPhoneNumber().equals(sel.getPhoneNumber()))
                        {
                            temp.add(food);
                        }
                    }
                    childMapping.put(sel,temp);
                }

            }
        };
    }

}
