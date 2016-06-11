package com.example.remi.ekio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends AppCompatActivity {

    private String[] mMenuItem;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private LinearLayout point;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen);



        //sign In (if user created and non auto conexion) or Register (no account) or direct connect (auto connexion)
        UserDAO userDao = new UserDAO(this);
        userDao.open();
        try {
            User user = userDao.selectionner(1);

            if (user == null) {
                final ImageView imSignIn = (ImageView) findViewById(R.id.login);
                imSignIn.setVisibility(View.GONE);
            } else {
                if (user.getAuto() == true) {
                    Intent intent = new Intent(this, BeforePictureActivity.class);
                    startActivity(intent);
                } else {
                    final ImageView imRegister = (ImageView) findViewById(R.id.register);
                    imRegister.setVisibility(View.GONE);
                }
            }
        } catch (Exception e){
        }
        userDao.close();
    }

    public void goSignIn(View view) {
        Intent goSignIn = new Intent(this, SignInActivity.class);
        startActivity(goSignIn);
    }
    public void goRegister(View view) {
        Intent goToRegister = new Intent(this, RegisterActivity.class);
        startActivity(goToRegister);
    }
}
