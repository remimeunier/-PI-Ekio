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

    // gestion du menu (voir main activity for details)
    private String[] mMenuItem;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private LinearLayout point;

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
        View myView = factory.inflate(R.layout.big_photo_layout, null);
        point = (LinearLayout) findViewById(R.id.point);
        point.addView(myView);


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
        CollectionableDAO objectDao2 = new CollectionableDAO(this);
        objectDao2.open();
        objectDao2.delete(id);
        objectDao2.close();

        Toast.makeText(getApplicationContext(), "Object deleted.", Toast.LENGTH_SHORT).show();

        Intent goBeforePicture = new Intent (this, BeforePictureActivity.class);
        startActivity(goBeforePicture);
    }


}
