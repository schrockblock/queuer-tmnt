package com.tmnt.queuer.managers;

import com.tmnt.queuer.Interfaces.LoginManagerCallback;

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