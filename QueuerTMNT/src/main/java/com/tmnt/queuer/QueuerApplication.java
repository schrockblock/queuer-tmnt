package com.tmnt.queuer;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by nico on 1/21/14.
 */
public class QueuerApplication extends Application {
    private RequestQueue queue;

    public RequestQueue getRequestQueue(){
        if (queue == null) queue = Volley.newRequestQueue(this);
        return queue;
    }
}
