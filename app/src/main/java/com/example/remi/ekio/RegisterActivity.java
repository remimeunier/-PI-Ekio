package com.example.remi.ekio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText userName = (EditText) findViewById(R.id.user_name);
        final EditText password = (EditText) findViewById(R.id.password);
        final EditText password2 = (EditText) findViewById(R.id.password2);
        final ImageView register = (ImageView) findViewById(R.id.register_confirm);
    }
}
