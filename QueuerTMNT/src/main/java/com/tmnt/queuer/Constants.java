package com.tmnt.queuer;

/**
 * Created by nico on 1/21/14.
 */
public class Constants {
    public final static String QUEUER_BASE_URL = "http://queuer-rndapp.rhcloud.com/";
    public final static String QUEUER_API_URL = QUEUER_BASE_URL + "api/v1/";
    public final static String QUEUER_SESSION_URL = QUEUER_API_URL + "session";
    public final static String QUEUER_CREATE_ACCOUNT_URL = QUEUER_API_URL + "users";
    public final static String QUEUER_SUCCESS_CREATEACCOUNT = "Successfully created the account";
    public final static String QUEUER_FAIL_CREATEACCOUNT = "Failed creating account, please try again.";
}
