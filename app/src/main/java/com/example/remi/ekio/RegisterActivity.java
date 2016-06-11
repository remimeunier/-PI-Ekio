package com.example.remi.ekio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.content.Context;
import android.widget.Toast;
import android.widget.CheckBox;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        final ImageView register = (ImageView) findViewById(R.id.register_confirm);
        final Context cContext = this;

        assert register != null;
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText etUserName = (EditText) findViewById(R.id.user_name);
                final EditText etPassword = (EditText) findViewById(R.id.password);
                final EditText etPassword2 = (EditText) findViewById(R.id.password2);
                final EditText etEmail = (EditText) findViewById(R.id.email);
                final CheckBox etAuto = (CheckBox) findViewById(R.id.auto_connexion);

                final String name = etUserName.getText().toString();
                final String password = etPassword.getText().toString();
                final String password2 = etPassword2.getText().toString();
                final String email = etEmail.getText().toString();
                final boolean auto = etAuto.isChecked();

                if (!password.isEmpty() && password.equals(password2)) {
                    if (email.contains("@")) {
                        User user = new User(email, name, password, auto);
                        UserDAO userDao = new UserDAO(cContext);
                        userDao.open();
                        userDao.ajouter(user);
                        userDao.close();

                        Intent intent = new Intent(cContext, BeforePictureActivity.class);
                        cContext.startActivity(intent);
                    } else {
                        etEmail.getText().clear();
                        Toast.makeText(RegisterActivity.this, "Email must be valid, try again",
                                Toast.LENGTH_LONG).show();
                    }

                } else {
                    etPassword.getText().clear();
                    etPassword2.getText().clear();
                    Toast.makeText(RegisterActivity.this, "Error in password, try again",
                            Toast.LENGTH_LONG).show();
                }


            }
        });
    }
}
