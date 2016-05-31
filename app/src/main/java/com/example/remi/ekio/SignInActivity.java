package com.example.remi.ekio;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        final EditText etEmail = (EditText) findViewById(R.id.email);
        final EditText etPassword = (EditText) findViewById(R.id.password);
        final ImageView logIn = (ImageView) findViewById(R.id.signin_confirm);
        final CheckBox etAuto = (CheckBox) findViewById(R.id.auto_connexion);
        final boolean auto = etAuto.isChecked();
        final Context cContext = this;

        assert logIn != null;
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserDAO userDao = new UserDAO(cContext);
                userDao.open();
                Boolean valid = userDao.authentificate(etEmail.getText().toString(), etPassword.getText().toString());
                userDao.close();
                if (valid) {
                    UserDAO userDao2 = new UserDAO(cContext);
                    userDao2.open();
                    userDao2.setAutoConnect(1, auto);
                    userDao2.close();

                    Intent intent = new Intent(cContext, BeforePictureActivity.class);
                    cContext.startActivity(intent);
                } else {
                    etPassword.getText().clear();
                    Toast.makeText(SignInActivity.this, "Email or Password not valid, please try again.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
