package com.tmnt.queuer.managers;

import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tmnt.queuer.Interfaces.LoginManagerCallback;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by TMNT on 1/7/14.
 */
public class LoginManager extends ActionBarActivity {
    private LoginManagerCallback callback;

    public void setCallback(LoginManagerCallback callback) {
        this.callback = callback;
    }

    public void login(String username, String password) throws Exception{
        if (callback == null) throw new Exception("Must supply a LoginManagerCallback");

        callback.startedRequest();
        authenticate(username, password);
    }

    private void authenticate(String username, String password){
        final String USER = username;
        final String PASS = password;
        RequestQueue requestQueue = Volley.newRequestQueue(LoginManager.this.getApplicationContext());
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
        Map<String, String> map = new HashMap<String, String>();
        map.put("username", USER);
        map.put("password", PASS);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                "http://queuer-rndapp.rhcloud.com/api/v1/session",
                new Gson().toJson(map),
                listener,
                errorListener);
        requestQueue.add(request);

    }

    private void authenticatedSuccessfully() throws Exception{
        if (callback == null) throw new Exception("Must supply a LoginManagerCallback");
        callback.finishedRequest(true);
    }

    private void authenticatedUnsuccessfully() throws Exception{
        if (callback == null) throw new Exception("Must supply a LoginManagerCallback");
        callback.finishedRequest(false);
    }
}