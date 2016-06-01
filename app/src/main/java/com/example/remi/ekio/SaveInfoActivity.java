package com.example.remi.ekio;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Hoang Nam on 31/05/2016.
 */
public class SaveInfoActivity extends Activity {

    public final static String MESSAGE_KEY = "com.example.remi.ekio.messagekey";
    ImageView preview;
    EditText etDate, etTitle, etComment, etKeyWord, etLocation;
    ImageButton geo;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_info_layout);

        // get intent fot photo path
        Intent intent = getIntent();
        String name = intent.getStringExtra(MESSAGE_KEY);
        path = "sdcard/EkioPhotos/"+name;

        //get edit Text
        etTitle = (EditText) findViewById(R.id.photo_title);
        etComment = (EditText) findViewById(R.id.photo_comment);
        etKeyWord = (EditText) findViewById(R.id.keyword);
        etLocation = (EditText) findViewById(R.id.photo_location);

        // get other component
        geo = (ImageButton) findViewById(R.id.geolocalisation);
        preview = (ImageView) findViewById(R.id.photo);
        preview.setImageDrawable(Drawable.createFromPath(path));

        // set date
        etDate = (EditText) findViewById(R.id.photo_date);
        final Calendar cal = Calendar.getInstance();
        int dd = cal.get(Calendar.DAY_OF_MONTH);
        int mm = cal.get(Calendar.MONTH);
        int yy = cal.get(Calendar.YEAR);
        // set current date into textview
        etDate.setText(new StringBuilder()
        // Month is 0 based, just add 1
                .append(dd).append("/").append(mm + 1).append("/")
                .append(yy));

    }

    public void goCollectionShowcase(View view){

        final String title = etTitle.getText().toString();
        final String comment = etComment.getText().toString();
        final String keyWords = etKeyWord.getText().toString();
        final String date = etDate.getText().toString();
        final String location = etTitle.getText().toString();

        Collectionable object = new Collectionable(title, date, location, comment, keyWords, path);
        CollectionableDAO objectDao = new CollectionableDAO(this);
        objectDao.open();
        objectDao.ajouter(object);
        objectDao.close();

        Toast.makeText(getApplicationContext(),
                title + "has been saved", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, BeforePictureActivity.class);
        startActivity(intent);
    }


}
