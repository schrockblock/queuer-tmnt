package com.tmnt.queuer.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Button;

import com.tmnt.queuer.R;

/**
 * Created by rahul on 1/12/14.
 */
public class CreateAccountActivity extends ActionBarActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);
        Button loginButton = (Button)findViewById(R.id.btn_login);
        Button createAccountButton = (Button)findViewById(R.id.btn_createAccount);
    }
}
