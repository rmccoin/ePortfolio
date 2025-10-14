package com.example.a5_3_project2_mccoin;

import android.os.Bundle;
import android.widget.Toast;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    // URL for your InfinityFree PHP API
    private static final String BASE_URL = "https://inventoryappproject.infinityfreeapp.com/";
    private static final String GET_ITEMS_URL = BASE_URL + "get_items.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Call function to load inventory data from your database
        loadInventoryData();
    }

    private void loadInventoryData() {
        // Create a new request queue
        RequestQueue queue = Volley.newRequestQueue(this);

        // Create a GET request to fetch data from your PHP file
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                GET_ITEMS_URL,
                null,
                response -> handleResponse(response),
                error -> {
                    Toast.makeText(this, "Error loading items: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("VolleyError", "Error: " + error.toString());
                }
        );

        // Add the request to the queue
        queue.add(jsonArrayRequest);
    }

    private void handleResponse(JSONArray response) {
        try {
            // Loop through the JSON array returned by PHP
            for (int i = 0; i < response.length(); i++) {
                JSONObject item = response.getJSONObject(i);

                int id = item.getInt("id");
                String name = item.getString("name");
                int quantity = item.getInt("quantity");
                double price = item.getDouble("price");

                // Log data to Logcat for now
                Log.d("InventoryItem", "ID: " + id + " | Name: " + name + " | Qty: " + quantity + " | Price: $" + price);
            }

            Toast.makeText(this, "Inventory loaded successfully!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error parsing data", Toast.LENGTH_SHORT).show();
        }
    }
}