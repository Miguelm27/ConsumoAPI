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

public class MainActivity3 extends AppCompatActivity {

    private EditText editTextPostId, editTextNewTitle, editTextNewBody;
    private Button buttonUpdate;
    private TextView textViewApiResult;
    private RequestQueue requestQueue;
    private static final String BASE_API_URL = "https://jsonplaceholder.typicode.com/posts/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        editTextPostId = findViewById(R.id.editTextPostId);
        editTextNewTitle = findViewById(R.id.editTextNewTitle);
        editTextNewBody = findViewById(R.id.editTextNewBody);
        buttonUpdate = findViewById(R.id.buttonUpdate);
        textViewApiResult = findViewById(R.id.textViewApiResult);

        requestQueue = Volley.newRequestQueue(this);

        buttonUpdate.setOnClickListener(v -> {
            String postId = editTextPostId.getText().toString().trim();
            String newTitle = editTextNewTitle.getText().toString().trim();
            String newBody = editTextNewBody.getText().toString().trim();

            if (postId.isEmpty() || newTitle.isEmpty() || newBody.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            } else {
                updatePost(postId, newTitle, newBody);
            }
        });
    }

    private void updatePost(String postId, String newTitle, String newBody) {
        String url = BASE_API_URL + postId;

        JSONObject postData = new JSONObject();
        try {
            postData.put("id", Integer.parseInt(postId));
            postData.put("title", newTitle);
            postData.put("body", newBody);
            postData.put("userId", 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                postData,
                response -> textViewApiResult.setText("Post actualizado:\n" + response.toString()),
                error -> Toast.makeText(this, "Error al actualizar post", Toast.LENGTH_SHORT).show()
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
