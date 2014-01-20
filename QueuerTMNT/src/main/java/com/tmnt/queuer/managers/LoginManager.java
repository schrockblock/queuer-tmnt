package com.tmnt.queuer.managers;

import android.support.v7.appcompat.R;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.tmnt.queuer.interfaces.LoginManagerCallback;
import com.tmnt.queuer.models.SignInModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by TMNT on 1/7/14.
 */
public class LoginManager {
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
            SignInModel model = new SignInModel(username, password);
            JSONObject signInJson = null;
            try {
                signInJson = new JSONObject(new Gson().toJson(model));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                    "http://queuer-mdapp.rhcloud.com/api/v1/session",//TODO: put url in strings folder
                    signInJson, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    // handle response (are there errors?)
                    Log.d("TESTINGRESPONSE", response.toString().toUpperCase());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // deal with it

                    Log.d("TESTINGRESPONSE", error.toString().toUpperCase());

                }
            });
        }

    private void authenticatedSuccessfully()throws Exception{
        if (callback == null) throw new Exception("Must supply a LoginManagerCallback");
        callback.finishedRequest(true);
    }

    private void authenticatedUnsuccessfully() throws Exception{
        if (callback == null) throw new Exception("Must supply a LoginManagerCallback");
        callback.finishedRequest(false);
    }
}