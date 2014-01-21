package com.tmnt.queuer.managers;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.tmnt.queuer.QueuerApplication;
import com.tmnt.queuer.interfaces.LoginManagerCallback;
import com.tmnt.queuer.models.SignInModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by TMNT on 1/7/14.
 */
public class LoginManager {
    private LoginManagerCallback callback;
    private Context context;

    public void setCallback(Context context, LoginManagerCallback callback) {
        this.callback = callback;
        this.context = context;
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
                    try{
                        authenticatedSuccessfully(callback, true);
                    }catch(Exception e){
                        e.printStackTrace();
                    }


                    // handle response (are there errors?)
                    System.out.println("Testingresponse" + response.toString().toUpperCase());
                    Log.d("TESTINGRESPONSE", response.toString().toUpperCase());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // deal with it
                    System.out.println("Testingresponse" + error.toString().toUpperCase());
                    Log.d("TESTINGRESPONSE", error.toString().toUpperCase());

                }
            });
            ((QueuerApplication)context.getApplicationContext()).getRequestQueue().add(request);
        }

    private void authenticatedSuccessfully(LoginManagerCallback callback, boolean successful)throws Exception{
        if (callback == null) throw new Exception("Must supply a LoginManagerCallback");
        callback.finishedRequest(true);
     //   return true;
    }

    private void authenticatedUnsuccessfully(LoginManagerCallback callback, boolean unsuccessful) throws Exception{
        if (callback == null) throw new Exception("Must supply a LoginManagerCallback");
        callback.finishedRequest(false);
    }
}