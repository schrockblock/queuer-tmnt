package com.tmnt.queuer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tmnt.queuer.R;

/**
 * Created by rahul on 1/12/14.
 */
public class CreateAccountActivity extends ActionBarActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);
        Button createAccountButton = (Button)findViewById(R.id.btn_createAccount);

        EditText username = (EditText)findViewById(R.id.et_username);
        EditText password = (EditText)findViewById(R.id.et_password);

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do Volley request to create new account

                Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
                startActivity(intent);

            }

        });
    }
}
