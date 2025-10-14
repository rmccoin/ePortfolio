package com.example.a5_3_project2_mccoin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText usernameField, passwordField;
    Button buttonLogin;

    // Update this URL to point to your working login.php file
    private static final String LOGIN_URL = "https://inventoryappproject.infinityfreeapp.com/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); // layout file we'll create next

        usernameField = findViewById(R.id.editTextUsername);
        passwordField = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(v -> attemptLogin());
    }

    private void attemptLogin() {
        String username = usernameField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show();
            return;
        }

        StringRequest request = new StringRequest(Request.Method.POST, LOGIN_URL,
                response -> {
                    try {
                        JSONObject obj = new JSONObject(response);
                        boolean success = obj.optBoolean("success", false);

                        if (success) {
                            Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();

                            // Go to HomeActivity
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            String message = obj.optString("message", "Invalid credentials");
                            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Server error", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(this, "Network error: " + error.getMessage(), Toast.LENGTH_SHORT).show()) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };

        Volley.newRequestQueue(this).add(request);
    }
}