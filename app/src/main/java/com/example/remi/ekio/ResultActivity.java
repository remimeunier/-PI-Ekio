package com.example.remi.ekio;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

/**
 * Created by Hoang Nam on 31/05/2016.
 */
public class ResultActivity extends Activity {
    ImageView iv;
    ImageButton fav;
    TextView object_name, object_date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_layout);
        Intent intent = getIntent();
        //File file = intent.getExtras().getParcelable(("img"));
        String file = intent.getStringExtra("img");

        iv = (ImageView) findViewById(R.id.result);
        iv.setImageURI(Uri.parse(file));
        fav = (ImageButton) findViewById(R.id.fav);
        fav.setTag(R.drawable.fav_off);
        object_name = (TextView) findViewById(R.id.object_title);
        object_date = (TextView) findViewById(R.id.object_date);

    }

    public void showDetails(View view){

    }

    public void favorise(View view){
        if (fav.getTag().equals(R.drawable.fav_off)){
            fav.setImageResource(R.drawable.fav_on);
            fav.setTag(R.drawable.fav_on);
            Toast.makeText(getApplicationContext(),
                    object_name.getText().toString() + "has been saved to Favorites", Toast.LENGTH_SHORT).show();;
        }
        else{
            fav.setImageResource(R.drawable.fav_off);
            fav.setTag(R.drawable.fav_off);
            Toast.makeText(getApplicationContext(),
                    object_name.getText().toString() + "has been removed from Favorites", Toast.LENGTH_SHORT).show();;
        }
    }
}
