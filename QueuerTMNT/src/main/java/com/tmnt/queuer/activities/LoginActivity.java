package com.tmnt.queuer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tmnt.queuer.Constants;
import com.tmnt.queuer.interfaces.LoginManagerCallback;
import com.tmnt.queuer.R;
import com.tmnt.queuer.managers.LoginManager;

public class LoginActivity extends ActionBarActivity implements LoginManagerCallback{

    private ProgressBar loading_bar;
    private TextView loading_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button login = (Button)findViewById(R.id.btn_login);
        Button createAccount = (Button)findViewById(R.id.btn_createAccount);
        final EditText user = (EditText)findViewById(R.id.et_username);
        final EditText pass = (EditText)findViewById(R.id.et_password);

        loading_bar = (ProgressBar)findViewById(R.id.li_progressbar);
        loading_text = (TextView)findViewById(R.id.li_loading);
        loading_bar.setVisibility(View.INVISIBLE);
        loading_text.setVisibility(View.INVISIBLE);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Started onclick manager");
                LoginActivity.this.startedRequest();
                LoginManager manager = new LoginManager();
                manager.setCallback(LoginActivity.this, LoginActivity.this);
                try {
                    manager.login(user.getText().toString(), pass.getText().toString(),  Constants.QUEUER_SESSION_URL);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            //Intent intent = new Intent(LoginActivity.this, LoginLoad.class);
            //startActivity(intent);
            }

        });

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);

                startActivity(intent);
            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void startedRequest() {
        loading_bar.setVisibility(View.VISIBLE);
        loading_text.setVisibility(View.VISIBLE);
    }

    @Override
    public void finishedRequest(boolean successful) {
        loading_bar.setVisibility(View.INVISIBLE);
        loading_text.setVisibility(View.INVISIBLE);
        if (successful){
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(LoginActivity.this, Constants.QUEUER_SUCCESS_LOGIN, duration);
            toast.show();

            Intent go_to_feed = new Intent(LoginActivity.this, FeedActivity.class);
            startActivity(go_to_feed);
        }else{
            //TODO: Add error saying can't log in
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(LoginActivity.this, Constants.QUEUER_FAIL_LOGIN, duration);
            toast.show();

        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_login, container, false);
            return rootView;
        }
    }


}