package com.example.remi.ekio;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.Calendar;

/**
 * Created by Hoang Nam on 31/05/2016.
 */
public class SaveInfoActivity extends Activity {
    public final static String MESSAGE_KEY = "com.example.remi.ekio.messagekey";
    ImageView preview;
    EditText date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_info_layout);
        Intent intent = getIntent();
        String name = intent.getStringExtra(MESSAGE_KEY);
        String path = "sdcard/EkioPhotos/"+name;
        preview = (ImageView) findViewById(R.id.photo);
        preview.setImageDrawable(Drawable.createFromPath(path));
        date = (EditText) findViewById(R.id.photo_date);
        final Calendar cal = Calendar.getInstance();
        int dd = cal.get(Calendar.DAY_OF_MONTH);
        int mm = cal.get(Calendar.MONTH);
        int yy = cal.get(Calendar.YEAR);
        // set current date into textview
        date.setText(new StringBuilder()
        // Month is 0 based, just add 1
                .append(dd).append("/").append(mm + 1).append("/")
                .append(yy));

    }


}
