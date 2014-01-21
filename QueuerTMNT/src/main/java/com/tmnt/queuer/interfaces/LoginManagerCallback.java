package com.tmnt.queuer.interfaces;

/**
 * Created by rahul on 1/8/14.
 */
public interface LoginManagerCallback {

        public void startedRequest();
        public void finishedRequest(boolean successful);

}
