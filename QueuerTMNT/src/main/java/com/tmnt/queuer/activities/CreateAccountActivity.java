package com.tmnt.queuer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tmnt.queuer.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rahul on 1/12/14.
 */
public class CreateAccountActivity extends ActionBarActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);
        Button createAccountButton = (Button)findViewById(R.id.btn_createAccount);

        EditText username = (EditText)findViewById(R.id.et_username);
        EditText password = (EditText)findViewById(R.id.et_password);
        final String USERNAME = username.toString();
        final String PASSWORD = password.toString();

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do Volley request to create new account

                RequestQueue requestQueue = Volley.newRequestQueue(CreateAccountActivity.this.getApplicationContext());
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Success Response:", response.toString());
                    }
                };

                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.networkResponse != null) {
                            Log.d("Error Response code: ", " " + error.networkResponse.statusCode);
                        }
                    }
                };

                Map<String, Map> user_map = new HashMap<String, Map>();
                Map<String, String> map = new HashMap<String, String>();
                map.put("username", USERNAME);
                map.put("password", PASSWORD);
                user_map.put("user", map);
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                        "http://queuer-rndapp.rhcloud.com/api/v1/users",
                        new Gson().toJson(user_map),
                        listener,
                        errorListener);
                requestQueue.add(request);


                Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
                startActivity(intent);

            }

        });
    }
}
