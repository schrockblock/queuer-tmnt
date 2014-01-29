package com.tmnt.queuer.managers;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.tmnt.queuer.Constants;
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
    private String url;
    private static LoginManager loginManager;

    private LoginManager(){}

    public static LoginManager getLoginManager(){
        if (loginManager == null){
            loginManager = new LoginManager(); //This only executes if singleton does not exist
        }
        return loginManager;
    }


    public void setCallback(Context context, LoginManagerCallback callback) {
        this.callback = callback;
        this.context = context;
    }

    public void login(String username, String password) throws Exception{
        this.url = Constants.QUEUER_SESSION_URL;
        if (callback == null) throw new Exception("Must supply a LoginManagerCallback");
        callback.startedRequest();
        authenticate(username, password);
    }

    public void createAccount(String username, String password) throws Exception{
        this.url = Constants.QUEUER_CREATE_ACCOUNT_URL;
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
                    url,
                    signInJson, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try{
                        Log.d("SuccessfulResponse1", response.toString().toUpperCase());
                        if (response.has("errors")) {
                            authenticatedUnsuccessfully(callback);
                        }else{
                            authenticatedSuccessfully(callback);
                        }


                    }catch(Exception e){
                        Log.d("SuccessfulResponse2", response.toString().toUpperCase());
                        e.printStackTrace();
                    }


                    // handle response (are there errors?)

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // deal with it
                    try{
                        Log.d("ErrorResponse1", error.toString().toUpperCase());
                        authenticatedUnsuccessfully(callback);
                    }catch(Exception e){
                        Log.d("ErrorResponse2", error.toString().toUpperCase());
                        e.printStackTrace();
                    }
                }
            });
            //((QueuerApplication)context.getApplicationContext()).getRequestQueue().add(request);
            Volley.newRequestQueue(context.getApplicationContext()).add(request);

        }

    private void authenticatedSuccessfully(LoginManagerCallback callback)throws Exception{
        if (callback == null) throw new Exception("Must supply a LoginManagerCallback");
        callback.finishedRequest(true);
     //   return true;
    }

    private void authenticatedUnsuccessfully(LoginManagerCallback callback) throws Exception{
        if (callback == null) throw new Exception("Must supply a LoginManagerCallback");
        callback.finishedRequest(false);
    }
}