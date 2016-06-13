package com.example.remi.ekio;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Hoang Nam on 12/06/2016.
 */
public class GoodMatchActivity extends Activity {

    public final static String MESSAGE_RES = "com.example.remi.ekio.messageres";
    public final static String MESSAGE_KEY = "com.example.remi.ekio.messagekey";
    ImageView good, better,best, showing, searched;
    ImageButton collection, save, details;
    Bitmap goodImage, betterImage,bestImage,tempImage;
    int detailId;
    String[]idList;
    String file, tempfile, name;


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
        name = intent.getStringExtra(MESSAGE_KEY);
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


        file = "sdcard/EkioPhotos/" + name;
        tempfile = "sdcard/EkioPhotos/tmp/" + name;
        tempImage = BitmapFactory.decodeFile(tempfile);
        searched.setImageBitmap(tempImage);

        objectDao.close();


    }

    public void goSave(View view){
        saveFile(tempImage, file );
        File del = new File(tempfile);
        del.delete();
        Intent save = new Intent(this,SaveInfoActivity.class);
        save.putExtra(MESSAGE_KEY,name);
        startActivity(save);
    }

    public void goDetail(View view){
        Intent detail = new Intent(this,ChooseFromCollectionActivity.class);
        detail.putExtra(MESSAGE_KEY,detailId);
        startActivity(detail);
    }
    public void showGood(View view){
        showing.setImageBitmap(goodImage);
        detailId = Integer.parseInt(idList[0]);
    }

    public void showBetter(View view){
        showing.setImageBitmap(betterImage);
        detailId = Integer.parseInt(idList[1]);
    }

    public void showBest(View view){
        showing.setImageBitmap(bestImage);
        detailId = Integer.parseInt(idList[2]);
    }

    public void goCollectionShowcase(View view){

        Intent intent = new Intent(this, CollectionShowcaseActivity.class);
        startActivity(intent);
    }

    private void saveFile(Bitmap bmp, String filename){
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(filename);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
            // PNG is a lossless format, the compression factor (100) is ignored
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
