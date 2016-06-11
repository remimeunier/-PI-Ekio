package com.example.remi.ekio;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * Created by Hoang Nam on 02/06/2016.
 */
public class ResultatActivity extends Activity {
    public final static String MESSAGE_KEY = "com.example.remi.ekio.messagekey";
    ImageButton delete;
    ImageView photo;
    Button edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultat_layout);
     //   delete = (ImageButton) findViewById(R.id.delete_from_view);
     //   photo = (ImageView) findViewById(R.id.photo_view);
     //   edit = (Button) findViewById(R.id.edit);
    }

    public void goBig(View view){
        Intent big = new Intent(this,PhotoGrandEcranACtivity.class);
        big.putExtra(MESSAGE_KEY,1);
        startActivity(big);

    }
}
