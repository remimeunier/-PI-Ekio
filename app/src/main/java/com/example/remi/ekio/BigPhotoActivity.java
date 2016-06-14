package com.example.remi.ekio;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
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
 * Created by Hoang Nam on 02/06/2016.
 */
public class BigPhotoActivity extends Activity {
    public final static String MESSAGE_KEY = "com.example.remi.ekio.messagekey";
    ImageButton delete;
    ImageView photo;
    ImageButton back;
    TextView title,date;
    String path;
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
        path = intent.getStringExtra(MESSAGE_KEY);

        CollectionableDAO objectDao = new CollectionableDAO(this);
        objectDao.open();

        id = objectDao.getIdFromPath(path);
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

        AlertDialog diaBox = AskOption();
        diaBox.show();

    }



    private AlertDialog AskOption()
    {
        AlertDialog myQuittingDialogBox =new AlertDialog.Builder(this)
                //set message, title, and icon
                .setTitle("Delete")
                .setMessage("Do you want to delete ?")
                .setIcon(R.drawable.trash)

                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        //your deleting code
                        CollectionableDAO objectDao2 = new CollectionableDAO(getApplicationContext());
                        objectDao2.open();
                        String path = objectDao2.select(id).getPhotoPath();
                        String objectName = objectDao2.select(id).getTitle();
                        objectDao2.delete(id);
                        File del = new File(path);
                        del.delete();
                        objectDao2.close();
                        if (objectName != ""){
                            Toast.makeText(getApplicationContext(), objectName+"deleted.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Object deleted.", Toast.LENGTH_SHORT).show();
                        }

                        Intent goBeforePicture = new Intent (getApplicationContext(), CollectionShowcaseActivity.class);
                        startActivity(goBeforePicture);

                        dialog.dismiss();
                    }

                })



                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();
        return myQuittingDialogBox;

    }

    public void favorise(){
        Toast.makeText(getApplicationContext(), "Premium feature !", Toast.LENGTH_SHORT).show();
    }


}
