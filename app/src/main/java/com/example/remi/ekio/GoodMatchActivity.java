package com.example.remi.ekio;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by Hoang Nam on 12/06/2016.
 */
public class GoodMatchActivity extends Activity {

    public final static String MESSAGE_RES = "com.example.remi.ekio.messageres";
    public final static String MESSAGE_KEY = "com.example.remi.ekio.messagekey";
    public final static String temp = "sdcard/EkioPhotos/tmp/tempfile.jpg";
    ImageView good, better,best, showing, searched;
    ImageButton collection, save, details;
    Bitmap goodImage, betterImage,bestImage,tempImage;
    int detailId;
    String[]idList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.good_match_layout);
        good = (ImageView) findViewById(R.id.good);
        better = (ImageView) findViewById(R.id.better);
        best = (ImageView) findViewById(R.id.best);
        showing = (ImageView) findViewById(R.id.showing);
        searched = (ImageView) findViewById(R.id.searchedImage);


        collection = (ImageButton) findViewById(R.id.collection);
        save = (ImageButton) findViewById(R.id.save);
        details = (ImageButton) findViewById(R.id.details);


        Intent intent = getIntent();
        String res = intent.getStringExtra(MESSAGE_RES);
        idList = res.split(",");

        CollectionableDAO objectDao = new CollectionableDAO(this);
        objectDao.open();


        detailId = Integer.parseInt(idList[2]);

        goodImage = BitmapFactory.decodeFile(objectDao.select(Integer.parseInt(idList[0])).getPhotoPath());
        good.setImageBitmap(goodImage);

        betterImage = BitmapFactory.decodeFile(objectDao.select(Integer.parseInt(idList[1])).getPhotoPath());
        better.setImageBitmap(betterImage);

        bestImage = BitmapFactory.decodeFile(objectDao.select(Integer.parseInt(idList[2])).getPhotoPath());
        best.setImageBitmap(bestImage);
        showing.setImageBitmap(bestImage);

        tempImage = BitmapFactory.decodeFile(temp);
        searched.setImageBitmap(tempImage);

        objectDao.close();


    }

    public void goSave(View view){
        Intent save = new Intent(this,SaveInfoActivity.class);
        save.putExtra(MESSAGE_KEY,detailId);
        startActivity(save);
    }

    public void setGood(){
        showing.setImageBitmap(goodImage);
        detailId = Integer.parseInt(idList[0]);
    }

    public void setBetter(){
        showing.setImageBitmap(betterImage);
        detailId = Integer.parseInt(idList[1]);
    }

    public void setBest(){
        showing.setImageBitmap(bestImage);
        detailId = Integer.parseInt(idList[2]);
    }

}
