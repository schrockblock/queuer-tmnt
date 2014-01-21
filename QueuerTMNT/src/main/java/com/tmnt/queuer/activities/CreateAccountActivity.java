package com.tmnt.queuer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tmnt.queuer.R;
import com.tmnt.queuer.interfaces.LoginManagerCallback;
import com.tmnt.queuer.managers.LoginManager;

/**
 * Created by rahul on 1/12/14.
 */
public class CreateAccountActivity extends ActionBarActivity {

    private ProgressBar loading_progressbar;
    private TextView loading;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);
        Button createAccountButton = (Button)findViewById(R.id.btn_createAccount);

        final EditText username = (EditText)findViewById(R.id.et_username);
        final EditText password = (EditText)findViewById(R.id.et_password);
        loading_progressbar = (ProgressBar)findViewById(R.id.ca_progressbar);
        loading_progressbar.setVisibility(View.INVISIBLE);

        loading = (TextView)findViewById(R.id.ca_loading);
        loading.setVisibility(View.INVISIBLE);



        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do Volley request to create new account
                startedRequest();
                /**LoginManager loginManager = new LoginManager();
                loginManager.setCallback(CreateAccountActivity.this, CreateAccountActivity.class);
                try{
                    loginManager.login(username.getText().toString(), password.getText().toString());
                }catch (Exception e){
                    e.printStackTrace();
                }*/
            }

        });

    }

    public void startedRequest() {
        loading.setVisibility(View.VISIBLE);
        loading_progressbar.setVisibility(View.VISIBLE);
    }

    public void finishedRequest(boolean successful){
        if (successful){
            Intent go_to_login = new Intent(this, LoginActivity.class);
            startActivity(go_to_login);
        }

        loading.setVisibility(View.INVISIBLE);
        loading_progressbar.setVisibility(View.INVISIBLE);

    }
}
