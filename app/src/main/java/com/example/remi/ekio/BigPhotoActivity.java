package com.example.remi.ekio;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;


import java.io.FileNotFoundException;

/**
 * Created by Hoang Nam on 02/06/2016.
 */
public class BigPhotoActivity extends Activity {
    public final static String MESSAGE_KEY = "com.example.remi.ekio.messagekey";
    ImageButton delete;
    ImageView photo;
    ImageButton back;
    TextView title,date;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.big_photo_layout);
        delete = (ImageButton) findViewById(R.id.delete);
        photo = (ImageView) findViewById(R.id.photo_view);
        back = (ImageButton) findViewById(R.id.back);
        title = (TextView) findViewById(R.id.object_title);
        date = (TextView) findViewById(R.id.object_date);

        Intent intent = getIntent();
        id = intent.getIntExtra(MESSAGE_KEY,1);

        CollectionableDAO objectDao = new CollectionableDAO(this);
        objectDao.open();
        Collectionable object = objectDao.select(id);
        objectDao.close();

        Bitmap myBitmap = BitmapFactory.decodeFile(object.getPhotoPath());
        photo.setImageBitmap(myBitmap);
        title.setText(object.getTitle());
        date.setText(object.getDate());

    }


    public void goBack(View view){
        Intent goBack = new Intent (this, ChooseFromCollectionActivity.class);
        goBack.putExtra(MESSAGE_KEY, id);
        startActivity(goBack);
    }

    public void photoDelete(View view){
        Toast.makeText(getApplicationContext(), " working process", Toast.LENGTH_SHORT).show();
    }


}
