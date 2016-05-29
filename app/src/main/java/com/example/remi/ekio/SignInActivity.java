package com.example.remi.ekio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        final EditText userName = (EditText) findViewById(R.id.user_name);
        final EditText password = (EditText) findViewById(R.id.password);
        final ImageView logIn = (ImageView) findViewById(R.id.signin_confirm);
    }
}
