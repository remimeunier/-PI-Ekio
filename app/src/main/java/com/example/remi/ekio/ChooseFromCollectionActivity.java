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


import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by Hoang Nam on 31/05/2016.
 */
public class ChooseFromCollectionActivity extends Activity {

    public final static String MESSAGE_KEY = "com.example.remi.ekio.messagekey";
    public final static String MESSAGE_DEL = "com.example.remi.ekio.messagedel";
    public final static String MESSAGE_FROMBIG = "com.example.remi.ekio.messagefrombig";
    ImageView iv;
    ImageButton fav;
    TextView object_name, object_date, object_key_words, object_comment, object_location;

    // gestion du menu (voir main activity for details)
    private String[] mMenuItem;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private LinearLayout point;
    private int id;
    private String path, tempfile;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // gestion du menu (voir main activity for details)
        mMenuItem = getResources().getStringArray(R.array.menu_item);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        CustomListAdapter adapter=new CustomListAdapter(this, mMenuItem);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // gestion du main content
        LayoutInflater factory = LayoutInflater.from(this);
        View myView = factory.inflate(R.layout.choose_from_collection_layout, null);
        point = (LinearLayout) findViewById(R.id.point);
        point.addView(myView);


        Intent intent = getIntent();

        //get the ID send throught intent
        id = intent.getIntExtra(MESSAGE_KEY,1);
        tempfile = intent.getStringExtra(MESSAGE_FROMBIG);
        //get the object matching the id
        CollectionableDAO objectDao = new CollectionableDAO(this);
        objectDao.open();
        Collectionable object = objectDao.select(id);
        path = object.getPhotoPath();
        objectDao.close();

        //show the image with size downgrading
        iv = (ImageView) findViewById(R.id.result_photo);

        Uri selectedImage = Uri.parse("file:///"+object.getPhotoPath());
        try {
            iv.setImageBitmap(decodeUri(selectedImage));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        // show title, date, comment
        object_name = (TextView) findViewById(R.id.result_photo_title);
        object_name.setText(object.getTitle());
        object_date = (TextView) findViewById(R.id.result_photo_date);
        object_date.setText(object.getDate());
        object_location = (TextView) findViewById(R.id.result_photo_location);
        object_location.setText(object.getLocation());
        object_comment = (TextView) findViewById(R.id.result_photo_comment);
        object_comment.setText(object.getcomment());
        object_key_words = (TextView) findViewById(R.id.result_keyword);
        object_key_words.setText(object.getKeyWords());

    }

    public void showDetails(View view) {

    }


    private Bitmap decodeUri(Uri selectedImage) throws FileNotFoundException {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(
                getContentResolver().openInputStream(selectedImage), null, o);

        final int REQUIRED_SIZE = 100;

        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(
                getContentResolver().openInputStream(selectedImage), null, o2);
    }


    public void goCollectionShowcase(View view){
        if (tempfile!=null) {
            File del = new File(tempfile);
            del.delete();
        }
        Intent intent = new Intent(this, CollectionShowcaseActivity.class);
        startActivity(intent);
    }

    public void goEdit(View view){
        if (tempfile!=null) {
            File del = new File(tempfile);
            del.delete();
        }
       // Toast.makeText(getApplicationContext(),
         //       "working process!!!", Toast.LENGTH_LONG).show();
        Intent goEdit = new Intent(this, SaveInfoActivity.class);
        goEdit.putExtra(MESSAGE_KEY,path);
        goEdit.putExtra(MESSAGE_DEL,true);
        startActivity(goEdit);
    }

    public void goBig(View view){
        Intent goBig = new Intent(this, BigPhotoActivity.class);
        goBig.putExtra(MESSAGE_KEY, path);
        startActivity(goBig);
    }
}
