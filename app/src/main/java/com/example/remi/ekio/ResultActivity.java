package com.example.remi.ekio;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.File;

/**
 * Created by Hoang Nam on 31/05/2016.
 */
public class ResultActivity extends Activity {
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_layout);
        Intent intent = getIntent();
        //File file = intent.getExtras().getParcelable(("img"));
        String file = intent.getStringExtra("img");

        iv = (ImageView) findViewById(R.id.result);
        iv.setImageURI(Uri.parse(file));



    }
}
