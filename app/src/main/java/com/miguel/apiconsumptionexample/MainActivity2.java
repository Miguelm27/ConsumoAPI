package com.miguel.apiconsumptionexample;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity {

    private EditText editTextTitle, editTextBody;
    private Button buttonSubmit;
    private TextView textViewApiResult;
    private RequestQueue requestQueue;
    private static final String API_URL = "https://jsonplaceholder.typicode.com/posts";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextBody = findViewById(R.id.editTextBody);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        textViewApiResult = findViewById(R.id.textViewApiResult);

        requestQueue = Volley.newRequestQueue(this);

        buttonSubmit.setOnClickListener(v -> {
            String title = editTextTitle.getText().toString().trim();
            String body = editTextBody.getText().toString().trim();

            if (title.isEmpty() || body.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            } else {
                createPost(title, body);
            }
        });
    }

    private void createPost(String title, String body) {
        JSONObject postData = new JSONObject();
        try {
            postData.put("title", title);
            postData.put("body", body);
            postData.put("userId", 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                API_URL,
                postData,
                response -> {
                    textViewApiResult.setText("Post creado:\n" + response.toString());
                    editTextTitle.setText("");
                    editTextBody.setText("");
                },
                error -> Toast.makeText(this, "Error al crear post", Toast.LENGTH_SHORT).show()
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        requestQueue.add(request);
    }
}
